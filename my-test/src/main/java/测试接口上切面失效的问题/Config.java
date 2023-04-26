package 测试接口上切面失效的问题;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: yuanjinzhong
 * @date: 2022/11/10 2:41 PM
 * @description:
 */
@Configuration
@EnableAspectJAutoProxy
public class Config {

    @Bean
	@SuppressWarnings({("rawtypes"),("uncheck")})
    public FactoryBean<DemoMapper> getProxyDemoMapper() {


		return new FactoryBean<DemoMapper>() {
            @Override
            public DemoMapper getObject() throws Exception {

                InvocationHandler invocationHandler = (proxy, method, args) -> {

                    if (method.getName().equals("update")) {
                        System.out.println("代理方法拦截");
                        return "update方法返回值";
                    }
                    if (method.getName().equals("toString")) {
                        return "toString方法返回值";
                    }
                    return null;
                };
                return (DemoMapper) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{DemoMapper.class}, invocationHandler);
            }

            @Override
            public Class<?> getObjectType() {
                return DemoMapper.class;
            }
        };
    }

}
