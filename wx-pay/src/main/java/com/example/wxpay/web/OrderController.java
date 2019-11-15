package com.example.wxpay.web;

import com.example.wxpay.utils.PayHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author bystander
 * @date 2018/10/4
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private PayHelper payHelper;

    /**
     * 生成微信支付链接
     *
     * @return
     */
    @GetMapping("url")
    public String generateUrl() {
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.generateUrl(orderId));
        Long orderId = 987984998111L;
        String url = payHelper.createPayUrl(orderId, "生成微信支付链接", 1L);
        return url;
    }

    /**
     * 查询订单支付状态
     *
     * @param
     * @return
     */
    @GetMapping("state")
    public Integer queryOrderStateByOrderId() {
        Long orderId = 987984998111L;
        return payHelper.queryPayState(orderId).getValue();
    }

}
