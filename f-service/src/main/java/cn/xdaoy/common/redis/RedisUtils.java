package cn.xdaoy.common.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;


@Repository
public class RedisUtils {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			redisTemplate.opsForValue().set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * set for expire
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 * @param timeUnit
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
		boolean result = false;
		try {
			redisTemplate.opsForValue().set(key, value);
			redisTemplate.expire(key, expireTime, timeUnit);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * remove batch
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * scan key match
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		Set<String> keys = redisTemplate.execute(new RedisCallback<Set<String>>() {

			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
				Set<String> keys = new HashSet<>();
				Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(1000).build());
				while (cursor.hasNext()) {
					keys.add(new String(cursor.next()));
				}
				return keys;
			}
		});
		return keys;
	}

	/**
	 * delete key match
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<String> keys = keys(pattern);
		if (keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}

	/**
	 * delete key batch
	 * 
	 * @param set
	 */
	public void removePattern(final Set<String> set) {
		if (set.size() > 0) {
			redisTemplate.delete(set);
		}
	}

	/**
	 * delete key
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		redisTemplate.delete(key);
	}

	/**
	 * exists key
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * get
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * hash set
	 * 
	 * @param key
	 * @param map
	 */
	public void hmSet(String key, Map<String, Object> map) {
		map.forEach((k, v) -> {
			redisTemplate.opsForHash().put(key, k, v);
		});

	}

	/**
	 * hash set
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * hash get
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * list push
	 * 
	 * @param k
	 * @param v
	 */
	public void lPush(String k, Object v) {
		redisTemplate.opsForList().rightPush(k, v);
	}

	/**
	 * list get
	 * 
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * set add
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object... value) {
		redisTemplate.opsForSet().add(key, value);
	}

	/**
	 * set get
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * order set
	 * 
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) {
		redisTemplate.opsForZSet().add(key, value, scoure);
	}

	/**
	 * order set get
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}

	/**
	 * select db
	 * 
	 * @param dbIndex
	 */
	public void SelectDB(int dbIndex) {
		redisTemplate.getConnectionFactory().getConnection().select(dbIndex);
	}

	/**
	 * flush db
	 */
	public void flushDB() {
		synchronized (redisTemplate) {
			redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					connection.flushDb();
					return "ok";
				}
			});
		}
	}

	/**
	 * flush all
	 */
	public void flushAll() {
		synchronized (redisTemplate) {
			redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					connection.flushAll();
					return "ok";
				}
			});
		}
	}
}
