package cn.xdaoy.sample.corp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.xdaoy.common.corp.CorpConfigQuerier;
import cn.xdaoy.common.corp.CorpDo;
import cn.xdaoy.common.corp.CorpRequest;

@Component
public class CorpRequestService {

	@Resource
	CorpConfigQuerier query;
	
	@Resource
	CorpRequest corpRequest;
	
	public void testReqest() {
		CorpDo corp = query.getConfig(new CorpDo("appid","corpid"));
		try {
			corpRequest.corpPostJson(corp, "01","{}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
