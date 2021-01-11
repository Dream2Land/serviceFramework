package com.jgrcb.sample.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xdaoy.common.redis.RedisUtils;

@Service
public class RedisService {

	@Resource
	RedisUtils redisUtils;
	
	public String test() {
		String key = "a";
		Object value = redisUtils.get(key);
		if(null == value) {
			redisUtils.set(key, "test from cache", 1L, TimeUnit.MINUTES);
			return "test from no cache";
		}
		return value.toString();
	}
}
