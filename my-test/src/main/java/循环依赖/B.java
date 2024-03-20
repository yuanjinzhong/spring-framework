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


	// 导致循环依赖
//	@Autowired
//	public B(A a) {
//		this.a = a;
//	}

	/**
	 * {@link AsyncAnnotationBeanPostProcessor} 内部生成代理的逻辑会导致 循环依赖无法解析
	 */
	@Async
	public void save() {

	}

}