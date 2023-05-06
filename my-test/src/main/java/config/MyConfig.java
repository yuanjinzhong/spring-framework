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


	/**
	 * 换成object 会监听到其他系统消息
	 * @param event
	 */
	@EventListener
	public void listen(String event){
		System.out.println("监听者收到消息:"+event);
	}
}
