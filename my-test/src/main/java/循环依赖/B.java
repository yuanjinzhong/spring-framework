package 循环依赖;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 循环依赖的demo
 */
@Component
public class B {


	/**
	 * 在配置允许循环依赖的情况下，
	 * yml配置：spring.main.allow-circular-references=true   5.2 的版本 该字段默认是true ,后面的高版本需要自己配置
	 * <p>
	 * 下面2中set方式的循环依赖是可以注入成功的
	 */

	@Autowired
	private A a;

//    @Autowired
//    public void setA(A a) {
//        this.a = a;
//    }



	/**
	 * 构造方法上添加@Lazy注解,可以解决构造器循环依赖
	 * @param a
	 */
	// 导致循环依赖
//	@Autowired
//	public B(@LazyA a) {
//		this.a = a;
//	}

	/**
	 * 尽管采用的setter方式的注入,但是由于使用了@Async注解,则会导致无法解析循环依赖(定制化的代理创建逻辑)----->前提是启动类添加{@link EnableAsync}注解,目的是添加 {@link AbstractAutoProxyCreator}
	 *
	 * @Transaction 注解却可以,因为它走的 {@link AbstractAutoProxyCreator} 的创建代理的逻辑(通用的创建代理的逻辑)
	 *
	 * {@link AsyncAnnotationBeanPostProcessor} 内部生成代理的逻辑会导致 循环依赖无法解析
	 */
	@Async
	public void save() {

	}

}