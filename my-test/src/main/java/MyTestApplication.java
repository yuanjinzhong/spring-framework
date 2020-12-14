import config.MyConfig;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.UserService;
import spring动态代理测试用到的类.Interceptor.OrderServiceIntercept;
import spring动态代理测试用到的类.service.OrderService;
import spring动态代理测试用到的类.service.impl.OrderServiceImpl;

/**
 * @author yjz
 */
public class MyTestApplication {

	public static void main(String[] args) {
        //测试refresh方法
		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
		UserService userService = context.getBean(UserService.class);
		userService.say();

		//测试发布事件
		context.publishEvent("event*******XXXXXXX");

        //编程式创建动态代理
		ProxyFactory proxyFactory = new ProxyFactory(new OrderServiceImpl());
		proxyFactory.addAdvice(new OrderServiceIntercept());
		OrderService orderService = (OrderService) proxyFactory.getProxy();
		orderService.getOrderInfo();


		//测试BeanPostProcessor和BeanFactoryPostProcessor


		//测试ImportBeanDefinitionRegistrar

	}
}
