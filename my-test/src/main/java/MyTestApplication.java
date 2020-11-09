import config.MyConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.UserService;

/**
 * @author yjz
 */
public class MyTestApplication {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
		UserService userService = context.getBean(UserService.class);
		userService.say();

	}
}
