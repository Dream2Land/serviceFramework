package cn.xdaoy.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * set applicationContext
     * 
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * get applicationContext
     * 
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * get bean by bean name
     * 
     * @param bean name
     * @return bean instance
     * 
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }
    
    /**
     * get bean by bean class
     * 
     * @param bean class
     * @return bean instance
     * @throws BeansException
     */
	public static <T> T getBean(Class<T> cls) throws BeansException {
        return applicationContext.getBean(cls);
    }
	
	/**
	 * get bean by bean type
	 * @param cls
	 * @return
	 * @throws BeansException
	 */
	public static <T> Map<String,T> getBeanOfType(Class<T> cls) throws BeansException {
        return applicationContext.getBeansOfType(cls);
    }
    
	/**
	 * get all bean's name 
	 * @return
	 * @throws BeansException
	 */
    public static String[] getBeanNames() throws BeansException {
        return applicationContext.getBeanDefinitionNames();
    }
    


}
