package cn.xdaoy.common.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.xdaoy.utils.PropertiesUtils;
import cn.xdaoy.utils.StringUtils;


public class TaskFactory {
	// 通信线程池
	private static final ThreadPoolExecutor TRANS_POOL = new ThreadPoolExecutor(50, 100, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),
			new NamedThreadFactory("TRANS"));
	// 异步处理线程池
	private static final ThreadPoolExecutor ASYNC_POOL = new ThreadPoolExecutor(20, 50, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),
			new NamedThreadFactory("ASYNC"));
	// 批量任务
	private static final ThreadPoolExecutor BATCH_POOL = new ThreadPoolExecutor(20, 50, 1L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(),
			new NamedThreadFactory("BATCH"));

	static {

		try {
			String tran_min = PropertiesUtils.getProperty("pool.trans.min");
			if (!StringUtils.isEmpty(tran_min)) {
				TRANS_POOL.setCorePoolSize(Integer.valueOf(tran_min));
			}
			String tran_max = PropertiesUtils.getProperty("pool.trans.max");
			if (!StringUtils.isEmpty(tran_max)) {
				TRANS_POOL.setMaximumPoolSize(Integer.valueOf(tran_max));
			}
			String async_min = PropertiesUtils.getProperty("pool.async.min");
			if (!StringUtils.isEmpty(async_min)) {
				ASYNC_POOL.setCorePoolSize(Integer.valueOf(async_min));
			}
			String async_max = PropertiesUtils.getProperty("pool.async.max");
			if (!StringUtils.isEmpty(async_max)) {
				ASYNC_POOL.setMaximumPoolSize(Integer.valueOf(async_max));
			}
			String batch_min = PropertiesUtils.getProperty("pool.batch.min");
			if (!StringUtils.isEmpty(batch_min)) {
				BATCH_POOL.setCorePoolSize(Integer.valueOf(batch_min));
			}
			String batch_max = PropertiesUtils.getProperty("pool.batch.max");
			if (!StringUtils.isEmpty(batch_max)) {
				BATCH_POOL.setMaximumPoolSize(Integer.valueOf(batch_max));
			}
			//allow core timeout for new thread
			BATCH_POOL.allowCoreThreadTimeOut(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Executor getBatchExcutor() {
		return BATCH_POOL;
	}
	
	public static Executor getTransExcutor() {
		return TRANS_POOL;
	}
	
	public static Executor getAsyncExcutor() {
		return ASYNC_POOL;
	}

	public static void addTransTask(Runnable task) {
		TRANS_POOL.execute(task);
	}

	public static <T> T addTransCall(Callable<T> call) {
		Future<T> f = TRANS_POOL.submit(call);
		try {
			return f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addAsyncTask(Runnable task) {
		ASYNC_POOL.execute(task);
	}

	public static <T> T addAsyncCall(Callable<T> call) {
		Future<T> f = ASYNC_POOL.submit(call);
		try {
			return f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addBatchTask(Runnable task) {
		BATCH_POOL.execute(task);
	}

	public static <T> T addBatchCall(Callable<T> call) {
		Future<T> f = BATCH_POOL.submit(call);
		try {
			return f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
