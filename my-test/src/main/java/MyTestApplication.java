import config.MyConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.UserService;
import spring动态代理测试用到的类.Interceptor.OrderServiceIntercept;
import spring动态代理测试用到的类.service.OrderService;
import spring动态代理测试用到的类.service.impl.OrderServiceImpl;

/**
 * @author yjz
 */
public class MyTestApplication {


	@Test
	public  void testGetBean() {
        //测试refresh方法
		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
		UserService userService = context.getBean(UserService.class);
		userService.say();

		//测试发布事件,MyConfig.class里面消费
		context.publishEvent("我是发出的消息");

        //编程式创建动态代理
		ProxyFactory proxyFactory = new ProxyFactory(new OrderServiceImpl());
		proxyFactory.addAdvice(new OrderServiceIntercept());
		OrderService orderService = (OrderService) proxyFactory.getProxy();
		orderService.getOrderInfo();


		//测试BeanPostProcessor和BeanFactoryPostProcessor


		//测试ImportBeanDefinitionRegistrar

	}

	@Test
	public void testBeanDefinition(){


	}


	@Test
	@DisplayName("测试AutowireCapableBeanFactory")
	public void testAutowireCapableBeanFactory(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
		/**
		 * {@link  Child} 类不是spring 管理的，也能被自动装配；
		 *
		 * 但是内部依赖的{@link  HelloService} 需要是spring管理的，这样才能用来装配{@link  Child}
		 */
		Child child = (Child)autowireCapableBeanFactory.createBean(Child.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
		child.getHelloService().say();

		/**
		 * 错误信息：No qualifying bean of type 'Child' available
		 *
		 * 从spring容器里面是获取不到 Child的，（不被spring管理）
		 */
		Assertions.assertThrows(NoSuchBeanDefinitionException.class,()->context.getBean(Child.class));
	}



}

 class Child {

	// 注意：这里并没有@Autowired注解的
	private HelloService helloService;
	private String name;
	private Integer age;

	public HelloService getHelloService() {
		return helloService;
	}

	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}


// 需要交给spring 管理
class  HelloService{
	void say(){
		System.out.println("*******hello*******");
	}
}

@Configuration
 class RootConfig {
	@Bean
	HelloService getHelloService(){
		return new HelloService();
	}

}
