package cn.xdaoy.common.task;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CronMapper {

	@Select("select cron,classname as 'className' from sys_cron where appid=#{appid} and enable = '1'")
	List<TaskEntity> getCron(@Param("appid") String appid);
}
