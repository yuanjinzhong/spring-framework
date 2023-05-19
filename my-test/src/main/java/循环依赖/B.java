package 循环依赖;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 循环依赖的demo
 */
@Component
public class B {


	/**
	 * 在配置允许循环依赖的情况下，
	 * <p>
	 * 下面2中set方式的循环依赖是可以注入成功的
	 */

	//@Autowired
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
}