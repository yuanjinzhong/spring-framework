package 测试接口上切面失效的问题;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: yuanjinzhong
 * @date: 2022/11/10 3:46 PM
 * @description: 测试接口上注解失效的问题
 */


public class TestAspectDisabled {


    @Test
    @DisplayName("测试接口上的切面失效")
    public void test(){

		@SuppressWarnings({("rawtypes"),("uncheck")})
		ApplicationContext context = new AnnotationConfigApplicationContext(new Class[]{Config.class,LogAspect.class});


		DemoMapper demoMapper = context.getBean(DemoMapper.class);

		//demoMapper.toString();

        String update = demoMapper.update();

    }

}
