package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
