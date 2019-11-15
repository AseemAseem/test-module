package com.example.wxpay.web;

import com.example.wxpay.utils.PayHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author bystander
 * @date 2018/10/5
 */
@RestController
@Slf4j
public class PayNotifyController {

    @Autowired
    private PayHelper payHelper;;

    //支付成功后，微信会以一定频率，调用这个接口，需要外网能访问。
    @PostMapping(value = "/wxpay/notify",produces = "application/xml")
    public ResponseEntity<String> payNotify(@RequestBody Map<String, String> msg) {
        //处理回调结果
        payHelper.handleNotify(msg);
        // 没有异常，则返回成功
        String result = "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
        return ResponseEntity.ok(result);

    }
}
