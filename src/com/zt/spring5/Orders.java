package com.zt.spring5;

/**
 * 使用有参构造注入
 */
public class Orders {
    private String orderName;
    private String address;

    public Orders(String orderName, String address) {
        this.orderName = orderName;
        this.address = address;
    }

    public void printOrders() {
        System.out.println(orderName + ": " + address);
    }
}
