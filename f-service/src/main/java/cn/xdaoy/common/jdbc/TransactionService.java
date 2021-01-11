package cn.xdaoy.common.jdbc;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.xdaoy.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionService {
	
	private static PlatformTransactionManager manager;

	private static synchronized PlatformTransactionManager getManager() {
		if(null == manager) {
			manager =SpringContextUtils.getBean("dataSourceTransactionManager");
		}
		return manager;
	}
	public interface Action{
		void action() throws Exception;
	}
	
	public static void doTrans(Action action) throws Exception{
		TransactionStatus txStatus = getManager().getTransaction(new DefaultTransactionDefinition());
		try {
			action.action();
			getManager().commit(txStatus);
		}catch (Exception e) {
			log.error("==>transcation commit error",e);
			getManager().rollback(txStatus);
			throw e;
		}
	}
}
