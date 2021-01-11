package cn.xdaoy.common.corp;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.xdaoy.common.exception.PlatException;
import cn.xdaoy.common.exception.ErrorCode.PlatError;
import cn.xdaoy.utils.StringUtils;

@Service
public class CorpConfigQuerier {

	private static ConcurrentHashMap<String, CorpDo> merchMap = new ConcurrentHashMap<>();

	@Autowired
	private ICorpMapper corpMapper;

	/**
	 * get corp requst config
	 * 
	 * @return
	 */
	public CorpDo getConfig(final CorpDo corp) {
		if (StringUtils.isEmpty(corp.getSysid()) || StringUtils.isEmpty(corp.getCorpcode())) {
			throw new PlatException(PlatError.methodParamInvalid, "sysid or corpcode is empty");
		}
		CorpDo corpCache = merchMap.get(corp.getSysid() + "_" + corp.getCorpcode());
		if (null != corpCache) {
			return corpCache;
		}
		CorpDo c = corpMapper.getByKey(corp);
		if (null == c) {
			throw new PlatException(PlatError.recordNotExists, "corp invalid");
		}
		merchMap.put(c.getSysid() + "_" + c.getCorpcode(), c);
		return c;
	}

	/**
	 * get corp trade code
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public CorpCodeDo getCorpCode(final CorpDo corpDo, String code) throws Exception {
		Assert.notNull(code, "code can't be null");
		if (null == corpDo) {
			throw new Exception("query corp not exists！");
		}
		for (CorpCodeDo t : corpDo.getCorpCodeList()) {
			if (code.equals(t.getTradecode())) {
				return t;
			}
		}
		return new CorpCodeDo();
	}

	/**
	 * get corp requst url
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String getUrl(final CorpDo corpDo, String code) throws Exception {
		Assert.notNull(code, "code can't be null");
		CorpCodeDo corpCodeDo = getCorpCode(corpDo, code);
		if (null == corpCodeDo || StringUtils.isEmpty(corpCodeDo.getTradecode())) {
			throw new PlatException(PlatError.recordNotExists, "tradecode invalid");
		}
		return corpDo.getCorpproc() + "://" + corpDo.getCorphost() + ":" + corpDo.getCorpport() + corpCodeDo.getUrl();
	}

	/**
	 * get corp ftp
	 * 
	 * @param type
	 * @return
	 */
	public CorpFtpDo getFtp(final CorpDo corpDo, String type) {
		Assert.notNull(type, "type can't be null");
		CorpFtpDo model = new CorpFtpDo();
		model.setCorpid(getConfig(corpDo).getId());
		model.setType(type);
		return corpMapper.getFtp(model);
	}
}
