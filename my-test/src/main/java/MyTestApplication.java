import config.MyConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import service.UserService;
import spring动态代理测试用到的类.Interceptor.OrderServiceIntercept;
import spring动态代理测试用到的类.service.OrderService;
import spring动态代理测试用到的类.service.impl.OrderServiceImpl;
import 循环依赖.A;
import 循环依赖.B;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yjz
 */
public class MyTestApplication {


	@Test
	public void testGetBean() {
		//测试refresh方法
		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class, A.class, B.class);
		UserService userService = context.getBean(UserService.class);
		userService.say();


		//测试循环依赖  Is there an unresolvable circular reference?
		context.getBean(A.class);
		context.getBean(B.class);


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
	@DisplayName("测试BeanDefinition")
	public void testBeanDefinition() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		System.out.println(context.getBeanDefinitionNames().length);
		for (int i = 0; i < context.getBeanDefinitionNames().length; i++) {
			System.out.println(context.getBeanDefinitionNames()[i]);
		}

		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(HelloService.class);
		genericBeanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		//genericBeanDefinition.setDependsOn("sayService");//依赖这个service，则需要这个beanDefinition
		context.registerBeanDefinition("helloService", genericBeanDefinition);

		context.refresh();

		BeanDefinition mergedBeanDefinition = context.getBeanFactory().getMergedBeanDefinition("helloService");
		BeanDefinition beanDefinition = context.getBeanFactory().getMergedBeanDefinition("helloService");
		HelloService helloService = context.getBeanFactory().getBean("helloService", HelloService.class);//bean工厂里面有beanDefinition则可以从bean工厂里面取出这个bean实类
		System.out.println(mergedBeanDefinition);
		System.out.println(beanDefinition);
		System.out.println(helloService);
		helloService.say();

	}


	@Test
	@DisplayName("测试AutowireCapableBeanFactory")
	public void testAutowireCapableBeanFactory() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
		/**
		 * {@link  Child} 类不是spring 管理的，也能被自动装配；
		 *
		 * 但是内部依赖的{@link  HelloService} 需要是spring管理的，这样才能用来装配{@link  Child}
		 */
		Child child = (Child) autowireCapableBeanFactory.createBean(Child.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
		child.getHelloService().say();

		/**
		 * 错误信息：No qualifying bean of type 'Child' available
		 *
		 * 从spring容器里面是获取不到 Child的，（不被spring管理）
		 */
		Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(Child.class));
	}


	@Test
	@DisplayName("测试一些ResolveType的用法")
	public void testResolveType() {
		Child child = new Child();
		child.setAge(20);
		child.setName("张三");
		ApplicationEvent applicationEvent = new PayloadApplicationEvent<>(this, child);
		ResolvableType eventType = ((PayloadApplicationEvent<?>) applicationEvent).getResolvableType();
	}


	@Test
	@DisplayName("测试事件监听-事件多播-异步监听-errorHandle")
	public void simpleApplicationEventMulticasterWithTaskExecutor() {
		@SuppressWarnings("unchecked")
		ApplicationListener<ApplicationEvent> listener = (x) -> {
			throw new RuntimeException("222");
		};
		ApplicationEvent applicationEvent = new PayloadApplicationEvent<>(this, "我是测试消息");
		SimpleApplicationEventMulticaster smc = new SimpleApplicationEventMulticaster();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		smc.setTaskExecutor(executorService);
		smc.setErrorHandler((e)-> System.out.println("errorHandler吃掉了异常，仅仅打印异常信息"+e.getMessage()));
		smc.addApplicationListener(listener);
		smc.multicastEvent(applicationEvent);
	}


	@Test
	@DisplayName("测试Environment抽象")
	public void testEnvironment() {

		//定义多个属性源
		PropertySource<String> propertySource = new PropertySource<String>("localPropertySource") {
			@Override
			public Object getProperty(String name) {
				return name.equals("姓名") ? "张三" : "李四";
			}
		};
		PropertySource<String> apolloSource = new PropertySource<String>("apolloPropertySource") {
			@Override
			public Object getProperty(String name) {
				return name.equals("姓名") ? "正式名字-张三" : "正式名字-李四";
			}
		};

		/**
		 * 多个属性源添加到{@link org.springframework.core.env.Environment} 里面
		 */
		ConfigurableEnvironment environment = new StandardEnvironment();

		environment.getPropertySources().addLast(propertySource);

		/**
		 * 多个propertySource里面存在相同的key，则哪个propertySource在前面则取哪个key的值
		 */
		environment.getPropertySources().addFirst(apolloSource);

		String name = environment.getProperty("姓名");
		/**
		 *  占位符解析，${姓名} 表示去寻找 key为 "姓名"的属性，然后替换该占位符
		 */
		String placeholders = environment.resolvePlaceholders("Replace this ${姓名}");
		System.out.println(name);
		System.out.println(placeholders);

	}


	@Test
	@DisplayName("测试@profile注解")
	public void testProfileAnnotation() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("test", "UAT", "PRE");
		context.register(MyConfig.class);
		context.refresh();

		//config.MyConfig.getStr中配置的UAT环境，所以能获取到这个Bean
		Object myStr = context.getBean("myStr");

		System.out.println(myStr);

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
class HelloService {
	void say() {
		System.out.println("*******hello*******");
	}
}


@Configuration
class RootConfig {
	@Bean
	HelloService helloService() {
		return new HelloService();
	}



	/**
	 * 测试自定义注解修饰的类被spring管理之后，@autowire 其他service
	 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProviderTests#testWithNoFilters()
	 */
	@Test
	public void testCustomAnnotation() {

	}


}
