package cn.xdaoy.common.corp;

import org.apache.ibatis.annotations.Mapper;

import cn.xdaoy.common.mapper.IBaseMapper;


@Mapper
public interface ICorpMapper extends IBaseMapper<CorpDo>{
	
	CorpFtpDo getFtp(CorpFtpDo param);
	
}
