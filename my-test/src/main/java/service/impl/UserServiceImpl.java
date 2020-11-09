package service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import service.UserService;

@Service
public class UserServiceImpl implements UserService , InitializingBean {
	@Override
	public void say() {
		System.out.println("*****************************UserServiceImpl---say**********************************");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("*********************************afterPropertiesSet*****************");
	}
}
