package cn.xdaoy.sample.task;

import cn.xdaoy.common.task.AbstractTask;
import cn.xdaoy.common.task.TaskFactory;

public class TaskSampleCaller implements Runnable{

	public void run() {
		TaskFactory.addBatchTask(new AbstractTask() {
			public void run() {
				info("this is task runner");
			}
		});
	}

}
