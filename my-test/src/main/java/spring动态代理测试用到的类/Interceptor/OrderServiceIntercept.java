package spring动态代理测试用到的类.Interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class OrderServiceIntercept implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("start");
		Object obj = invocation.proceed();
		System.out.println("end");
		return obj;
	}
}
