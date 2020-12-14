package config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import service.UserService;
import service.impl.UserServiceImpl;

/**
 * @author yjz
 */
@Configuration
public class MyConfig {

	@Bean
	UserService getUserService() {
		return new UserServiceImpl();
	}


	@EventListener
	public void listen(Object event){
		System.out.println("****XXXXXXXX"+event);
	}
}
