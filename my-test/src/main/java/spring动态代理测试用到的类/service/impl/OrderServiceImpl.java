package spring动态代理测试用到的类.service.impl;

import spring动态代理测试用到的类.service.OrderService;

public class OrderServiceImpl implements OrderService {
	@Override
	public void getOrderInfo() {
		System.out.println("order info");
	}
}
