package cn.xdaoy.sample.task;

import cn.xdaoy.common.task.AbstractTask;
import cn.xdaoy.common.task.TaskFactory;

public class TaskSampleCaller implements Runnable{

	@Override
	public void run() {
		TaskFactory.addBatchTask(new AbstractTask() {
			@Override
			public void run() {
				info("this is task runner");
			}
		});
	}

}
