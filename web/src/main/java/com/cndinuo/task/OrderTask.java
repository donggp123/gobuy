package com.cndinuo.task;

import com.cndinuo.service.OrderService;
import com.cndinuo.service.SettleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("orderTask")
public class OrderTask {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SettleAccountService accountService;

    /**
     * //判断商家是否已经接单
     * @throws Exception
     */
    public void execute() throws Exception{
        orderService.judgeMrhtIsOrders();
    }

    /**
     * 生成结算单
     */
    public void createSettle() {
        accountService.createSettleOrder();
    }
}