package cn.xdaoy.common.corp;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xdaoy.utils.http.HttpUtils;
import cn.xdaoy.utils.tcp.TcpUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CorpRequest {

	@Resource
	CorpConfigQuerier configQuerier;

	/**
	 * corp get
	 * @param corp
	 * @param tradeCode
	 * @return
	 * @throws Exception
	 */
	public String corpGet(CorpDo corp, String tradeCode) throws Exception {
		String url = configQuerier.getUrl(corp, tradeCode);
		log.info("==>corp request,url:{}",url);
		return HttpUtils.get(url, corp.getCorpcharset());
	}

	/**
	 * corp get with url params
	 * @param corp
	 * @param tradeCode
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String corpGet(CorpDo corp, String tradeCode, Map<String, String> params) throws Exception {
		String url = configQuerier.getUrl(corp, tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,param:{}",params);
		return HttpUtils.get(url, params, corp.getCorpcharset());
	}

	/**
	 * corp post json
	 * @param corp
	 * @param tradeCode
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public String corpPostJson(CorpDo corp, String tradeCode, String json) throws Exception {
		String url = configQuerier.getUrl(corp, tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,json:{}",json);
		return HttpUtils.postJson(url, json, corp.getCorpcharset());
	}

	/**
	 * corp post xml
	 * @param corp
	 * @param tradeCode
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public String corpPostXml(CorpDo corp, String tradeCode, String xml) throws Exception {
		String url = configQuerier.getUrl(corp, tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,xml:{}",xml);
		return HttpUtils.postXml(url, xml, corp.getCorpcharset());
	}

	/**
	 * corp post form
	 * @param config
	 * @param tradeCode
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String corpPostForm(CorpDo corp, String tradeCode, Map<String, String> params) throws Exception {
		String url = configQuerier.getUrl(corp, tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,params:{}",params);
		return HttpUtils.postForm(url, params, corp.getCorpcharset());
	}

	/**
	 * corp tcp
	 * @param config
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String corpTcp(CorpDo corp, String message) throws Exception {
		log.info("==>corp request,corp:{}",corp);
		log.info("==>corp request,message:{}",message);
		return TcpUtils.getString(corp.getCorphost(), corp.getCorpport(), corp.getCorpcharset(), message);
	}

	/**
	 * corp get
	 * @param config
	 * @param tradeCode
	 * @return
	 * @throws Exception
	 */
	public String corpGet(CorpConfig config, String tradeCode) throws Exception {
		String url = config.getUrl(tradeCode);
		log.info("==>corp request,url:{}",url);
		return HttpUtils.get(url, config.getConfig().getCorpcharset());
	}

	/**
	 * corp get with url params
	 * @param config
	 * @param tradeCode
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String corpGet(CorpConfig config, String tradeCode, Map<String, String> params) throws Exception {
		String url = config.getUrl(tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,params:{}",params);
		return HttpUtils.get(url, params, config.getConfig().getCorpcharset());
	}

	/**
	 * corp post json
	 * @param config
	 * @param tradeCode
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public String corpPostJson(CorpConfig config, String tradeCode, String json) throws Exception {
		String url = config.getUrl(tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,json:{}",json);
		return HttpUtils.postJson(url, json, config.getConfig().getCorpcharset());
	}

	/**
	 * corp post xml
	 * @param config
	 * @param tradeCode
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public String corpPostXml(CorpConfig config, String tradeCode, String xml) throws Exception {
		String url = config.getUrl(tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,xml:{}",xml);
		return HttpUtils.postXml(url, xml, config.getConfig().getCorpcharset());
	}

	/**
	 * corp post form
	 * @param config
	 * @param tradeCode
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String corpPostForm(CorpConfig config, String tradeCode, Map<String, String> params) throws Exception {
		String url = config.getUrl(tradeCode);
		log.info("==>corp request,url:{}",url);
		log.info("==>corp request,params:{}",params);
		return HttpUtils.postForm(url, params, config.getConfig().getCorpcharset());
	}

	/**
	 * corp tcp
	 * @param config
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String corpTcp(CorpConfig config, String message) throws Exception {
		log.info("==>corp request,config:{}",config.getConfig());
		log.info("==>corp request,message:{}",message);
		return TcpUtils.getString(config.getConfig().getCorphost(), config.getConfig().getCorpport(),
				config.getConfig().getCorpcharset(), message);
	}

}
