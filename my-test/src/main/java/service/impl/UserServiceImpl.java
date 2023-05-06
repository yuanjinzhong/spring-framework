package service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import service.UserService;

//@Service
public class UserServiceImpl implements UserService , InitializingBean, BeanPostProcessor {
	@Override
	public void say() {
		System.out.println("UserServiceImpl---say");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("UserServiceImpl---afterPropertiesSet");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("UserServiceImpl----postProcessBeforeInitialization");
		return  bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("UserServiceImpl----postProcessAfterInitialization");
		return bean;
	}
}
