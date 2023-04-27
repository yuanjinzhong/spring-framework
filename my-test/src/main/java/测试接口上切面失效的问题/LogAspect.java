package 测试接口上切面失效的问题;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author: yuanjinzhong
 * @date: 2022/11/10 2:51 PM
 * @description:
 */
@Aspect
@Component
public class LogAspect {
    @Before(value = "@annotation(log)",argNames = "point,log")
    public void before(JoinPoint point, Log log){
        System.out.println("记录日志->before");
    }
    @After(value = "@annotation(log)",argNames = "point,log")
    public void afterDemo(JoinPoint point, Log log) {
        System.out.println("记录日志->after");
    }
}
