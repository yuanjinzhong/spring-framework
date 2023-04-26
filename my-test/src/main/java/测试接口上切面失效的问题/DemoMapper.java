package 测试接口上切面失效的问题;

/**
 * @author: yuanjinzhong
 * @date: 2022/11/10 11:40 AM
 * @description:
 */

public interface DemoMapper {

    /**
     * 遇到过一个特别的场景，log注解对应的切面失效； 但是当前例子中测试是可以的
     * @return
     */
    @Log
    String update();

}
