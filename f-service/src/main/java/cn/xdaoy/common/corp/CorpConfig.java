package cn.xdaoy.common.corp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.xdaoy.common.exception.ErrorCode.PlatError;
import cn.xdaoy.common.exception.PlatException;
import cn.xdaoy.utils.StringUtils;

@Component
public abstract class CorpConfig {

	@Autowired
	private ICorpMapper corpMapper;

	private CorpDo instance;

	protected abstract CorpDo getCorpDo();

	/**
	 * get request target config
	 * 
	 * @return
	 */
	public CorpDo getConfig() {
		if (null != instance)
			return instance;
		CorpDo corp = getCorpDo();
		if (StringUtils.isEmpty(corp.getSysid()) || StringUtils.isEmpty(corp.getCorpcode())) {
			throw new PlatException(PlatError.methodParamInvalid, "sysid or corpcode is empty");
		}
		corp = corpMapper.getByKey(corp);
		if (null == corp) {
			throw new PlatException(PlatError.recordNotExists, "corp invalid");
		}
		instance = corp;
		return corp;
	}

	/**
	 * get corp trade code
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public CorpCodeDo getCorpCode(String code) throws Exception {
		Assert.notNull(code, "code can't be null");
		CorpDo corpDo = getConfig();
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
	public String getUrl(String code) throws Exception {
		Assert.notNull(code, "code can't be null");
		CorpDo corpDo = getConfig();
		CorpCodeDo corpCodeDo = getCorpCode(code);
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
	public CorpFtpDo getFtp(String type) {
		Assert.notNull(type, "type can't be null");
		CorpFtpDo model = new CorpFtpDo();
		model.setCorpid(getConfig().getId());
		model.setType(type);
		return corpMapper.getFtp(model);
	}
}
