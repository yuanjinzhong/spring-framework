package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import service.UserService;
import service.impl.UserServiceImpl;

/**
 * @author yjz
 */
@Configuration
@EnableAsync
public class MyConfig {

	@Bean
	UserService getUserService() {
		return new UserServiceImpl();
	}


	/**
	 * 换成object 会监听到其他系统消息
	 * @param event
	 */
	//@Async("TaskExecutor-异步任务专用")
	// 默认会去bean工厂找名字为：taskExecutor 的线程池
	// 最好是指定线程池
	@Async/*(value = "TaskExecutor-异步任务专用")*/
	@EventListener
	public void listen(String event){
		System.out.println("监听者收到消息:"+event);
	}


	@Bean("TaskExecutor-yjz")
	public TaskExecutor  taskExecutor(){
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		return threadPoolTaskExecutor;
	}

	@Bean("TaskExecutor-异步任务专用")
	public TaskExecutor  taskExecutor2(){
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		return threadPoolTaskExecutor;
	}


	/**
	 * 测试@Profile注解哦
	 * @return
	 */
	@Bean("myStr")
	@Profile("UAT")
	public  String getStr(){
		return "我是配置";
	}
}
