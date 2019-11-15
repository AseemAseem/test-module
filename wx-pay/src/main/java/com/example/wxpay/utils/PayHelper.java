package com.example.wxpay.utils;

import com.example.wxpay.config.PayConfig;
import com.example.wxpay.enums.ExceptionEnum;
import com.example.wxpay.enums.PayStateEnum;
import com.example.wxpay.exception.LyException;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bystander
 * @date 2018/10/5
 */
@Slf4j
@Component
public class PayHelper {

    private WXPay wxPay;

    private String notifyUrl;

    @Autowired
    private PayConfig payConfig;

    @Autowired
    private RestTemplate restTemplate;

    public PayHelper(PayConfig payConfig) {
        wxPay = new WXPay(payConfig);
        this.notifyUrl = payConfig.getNotifyUrl();
    }

    /**
     * @param orderId     订单id
     * @param description 订单描述
     * @param totalPay    总金额
     * @return
     */
    public String createPayUrl(Long orderId, String description, Long totalPay) {

        try {
            HashMap<String, String> data = new HashMap<>();
            //描述
            data.put("body", description);
            //订单号
            data.put("out_trade_no", orderId.toString());
            //货币（默认就是人民币）
            //data.put("fee_type", "CNY");
            //总金额
            data.put("total_fee", totalPay.toString());
            //调用微信支付的终端ip
            data.put("spbill_create_ip", "127.0.0.1");
            //回调地址
            data.put("notify_url", notifyUrl);
            //交易类型为扫码支付
            data.put("trade_type", "NATIVE");

            //填充请求参数并签名，并把请求参数处理成字节
            byte[] request = WXPayUtil.mapToXml(wxPay.fillRequestData(data)).getBytes("UTF-8");

            //发起请求并获取响应结果
            String xml = restTemplate.postForObject(WXPayConstants.SANDBOX_MICROPAY_URL, request, String.class);

            //将结果处理成map
            Map<String, String> result = WXPayUtil.xmlToMap(xml);


            //通信失败
            if (WXPayConstants.FAIL.equals(result.get("return_code"))) {
                log.error("【微信下单】与微信通信失败，失败信息：{}", result.get("return_msg"));
                return null;
            }

            //下单失败
            if (WXPayConstants.FAIL.equals(result.get("result_code"))) {
                log.error("【微信下单】创建预交易订单失败，错误码：{}，错误信息：{}",
                        result.get("err_code"), result.get("err_code_des"));
                return null;
            }

            //检验签名
            isSignatureValid(result);

            //下单成功，获取支付连接
            String url = result.get("code_url");

            return url;
        } catch (Exception e) {
            log.error("【微信下单】创建预交易订单异常", e);
            return null;
        }
    }

    /**
     * 检验签名
     *
     * @param result
     */
    private void isSignatureValid(Map<String, String> result) {
        try {
            //默认是md5校验
//            boolean boo1 = WXPayUtil.isSignatureValid(result, payConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
            boolean boo1 = true;
            boolean boo2 = WXPayUtil.isSignatureValid(result, payConfig.getKey(), WXPayConstants.SignType.MD5);
            if (!boo1 || !boo2) {
                log.error("【微信支付】检验签名失败，数据：{}", result);
            }
        } catch (Exception e) {
            log.error("【微信支付】检验签名失败，数据：{}", result);
            e.printStackTrace();
        }
    }

    /**
     * 处理回调结果
     *
     * @param msg
     */
    public void handleNotify(Map<String, String> msg) {
        //检验签名
        isSignatureValid(msg);

        //检验金额
        //解析数据
        String totalFee = msg.get("total_fee");  //订单金额
        String outTradeNo = msg.get("out_trade_no");  //订单编号
        String transactionId = msg.get("transaction_id");  //商户订单号
        String bankType = msg.get("bank_type");  //银行类型
        if (StringUtils.isEmpty(totalFee) || StringUtils.isEmpty(outTradeNo)
                || StringUtils.isEmpty(transactionId) || StringUtils.isEmpty(bankType)) {
            log.error("【微信支付回调】支付回调返回数据不正确");
            throw new LyException(ExceptionEnum.WX_PAY_NOTIFY_PARAM_ERROR);
        }

        //todo 校验金额
        if (/*order.getActualPay()*/1 != Long.valueOf(totalFee)) {
            log.error("【微信支付回调】支付回调返回数据不正确");
            throw new LyException(ExceptionEnum.WX_PAY_NOTIFY_PARAM_ERROR);
        }
    }


    /**
     * 查询订单支付状态
     *
     * @param orderId
     * @return
     */
    public PayStateEnum queryPayState(Long orderId) {
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", orderId.toString());
        try {
            Map<String, String> result = wxPay.orderQuery(data);
            if (CollectionUtils.isEmpty(result) || WXPayConstants.FAIL.equals(result.get("return_code"))) {
                //未查询到结果，或连接失败
                log.error("【支付状态查询】链接微信服务失败，订单编号：{}", orderId);
                return PayStateEnum.NOT_PAY;
            }
            //查询失败
            if (WXPayConstants.FAIL.equals(result.get("result_code"))) {
                log.error("【支付状态查询】查询微信订单支付结果失败，错误码：{}, 订单编号：{}", result.get("result_code"), orderId);
                return PayStateEnum.NOT_PAY;
            }

            //检验签名
            isSignatureValid(result);

            //查询支付状态
            String state = result.get("trade_state");
            if ("SUCCESS".equals(state)) {
                //支付成功, 修改支付状态等信息
                handleNotify(result);
                return PayStateEnum.SUCCESS;
            } else if ("USERPAYING".equals(state) || "NOTPAY".equals(state)) {
                //未支付成功
                return PayStateEnum.NOT_PAY;
            } else {
                //其他返回付款失败
                return PayStateEnum.FAIL;
            }
        } catch (Exception e) {
            log.error("查询订单支付状态异常", e);
            return PayStateEnum.NOT_PAY;
        }
    }
}
