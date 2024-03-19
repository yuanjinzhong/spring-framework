package 循环依赖;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 循环依赖的demo
 */
@Component
public class A {

	/**
	 * 在配置允许循环依赖的情况下，
	 * yml配置：spring.main.allow-circular-references=true  5.2 的版本 该字段默认是true ,后面的高版本需要自己配置
	 * <p>
	 * 下面2中set方式的循环依赖是可以注入成功的
	 */

 	@Autowired
	private B b;


//	@Autowired
//	public void setB(B b) {
//		this.b = b;
//	}

    // 导致循环依赖
//	@Autowired
//	public A(B b) {
//		this.b = b;
//	}
}