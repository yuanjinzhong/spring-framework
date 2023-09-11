package example.scannable;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: yuanjinzhong
 * @date: 2023/9/11 16:55
 * @description: 测试自定义注解修饰的类被spring管理之后，@autowire 其他service
 */


@CustomStereotype(value = "defaultNamedComponentWithOtherService")
public class DefaultNamedComponentWithOtherService {
	@Autowired
	private  DefaultNamedComponent defaultNamedComponent;

	public void  debug(){
		System.out.println(defaultNamedComponent);
	}

}
