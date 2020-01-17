package com.lockie.cloudorder.controller;

import com.lockie.cloudorder.model.Order;
import com.lockie.cloudorder.model.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author: lockie
 * @Date: 2020/1/10 16:25
 * @Description:
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/cloudOrder")
public class CloudOrderController extends BaseController {

    @Value("${nacos.test:132}")
    String nacosTest;

    @Autowired
    RestTemplate restTemplate;

    private final static String USER_SERVICE = "cloud-user-service";
    private final static String GET_USER_NAME = "/cloudUser/getUserName";

    @GetMapping("/getProperties")
    public Results getProperties() {
        return succeed(nacosTest);
    }

    /**
     * 获取订单信息
     * @return
     */
    @GetMapping("/getOrder")
    public Results getOrder() {
        // 获取用户名
        String userName = restTemplate.getForObject("http://" + USER_SERVICE + GET_USER_NAME, String.class);

        Order order = new Order();
        order.setId(1);
        order.setUserName(userName);
        order.setCreateTime(new Date());

        return succeed(order);
    }
}
