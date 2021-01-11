package cn.xdaoy.common.redis;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig extends CachingConfigurerSupport{
	
	@Autowired
	RedisProperties redisProperties;
	
	@Bean
    public RedisConnectionFactory getConnectionFactory() {
		if(null == redisProperties.getSentinel()) {
	        RedisStandaloneConfiguration configuration =
	                new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
	        return new LettuceConnectionFactory(configuration);
		}
		Set<String> sentinels = new HashSet<String>();
		redisProperties.getSentinel().getNodes().forEach(c->sentinels.add(c));
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration(redisProperties.getSentinel().getMaster(), sentinels);
		return new LettuceConnectionFactory(sentinelConfig);
	}
    

	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		//serializer
        template.setConnectionFactory(factory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        
        template.afterPropertiesSet();
        return template;
	}

}
