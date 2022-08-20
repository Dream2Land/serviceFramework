package cn.xdaoy.sample.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SampleMapper {
	
	@Select("select 1 from dual")
	int count();

}
