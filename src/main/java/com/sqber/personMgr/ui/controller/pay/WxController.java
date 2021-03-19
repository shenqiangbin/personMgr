package com.sqber.personMgr.ui.controller.pay;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.IpKit;
import com.ijpay.core.kit.QrCodeKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.model.OrderQueryModel;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.IPayOrderService;
import com.sqber.personMgr.entity.PayOrder;
import com.sqber.personMgr.ui.config.pay.WxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.ijpay.wxpay.WxPayApiConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/wxPay")
public class WxController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WxConfig wxConfig;

    @Autowired
    IPayOrderService payOrderService;

    public void getApiConfig() {
        WxPayApiConfig apiConfig;

        try {
            apiConfig = WxPayApiConfigKit.getApiConfig(wxConfig.getAppId());
        } catch (Exception e) {
            apiConfig = WxPayApiConfig.builder()
                    .appId(wxConfig.getAppId())
                    .mchId(wxConfig.getMchId())
                    .partnerKey(wxConfig.getPartnerKey())
                    .certPath(wxConfig.getCertPath())
                    .domain(wxConfig.getDomain())
                    .build();
            WxPayApiConfigKit.setThreadLocalWxPayApiConfig(apiConfig);
        }
    }


    /**
     * 扫码支付模式二
     */
    @PostMapping("/scanCode2")
    public BaseResponse scanCode2(HttpServletRequest request, HttpServletResponse response, String totalFee) {

        if (StringUtils.isEmpty(totalFee)) {
            return BaseResponse.fail("支付金额不能为空");
        }

        String ip = IpKit.getRealIp(request);
        if (StringUtils.isEmpty(ip)) {
            ip = "127.0.0.1";
        }

        return createCode(WxPayKit.generateStr(), ip,"","","", 1);

    }

    private BaseResponse createCode(String orderNo, String ip, String body, String detail, String attach, int totalFee){

        getApiConfig();
        WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();

        String notifyUrl = wxPayApiConfig.getDomain().concat("/wxPay/payNotify");

        Map<String, String> params = UnifiedOrderModel
                .builder()
                .appid(wxPayApiConfig.getAppId())
                .mch_id(wxPayApiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .body(body)
                .attach(attach)
                .detail(detail)
                .out_trade_no(orderNo)
                .total_fee(String.valueOf(totalFee))
                .spbill_create_ip(ip)
                .notify_url(notifyUrl)
                .trade_type(TradeType.NATIVE.getTradeType())
                .build()
                .createSign(wxPayApiConfig.getPartnerKey(), SignType.HMACSHA256);

        String xmlResult = WxPayApi.pushOrder(false, params);
        log.info("统一下单:" + xmlResult);

        Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        System.out.println(returnMsg);
        if (!WxPayKit.codeIsOk(returnCode)) {
            return BaseResponse.fail("error:" + returnMsg);
        }
        String resultCode = result.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            return BaseResponse.fail("error:" + returnMsg);
        }
        //生成预付订单success

        String qrCodeUrl = result.get("code_url");

        // 生成二维码，并返回 BASE64 格式的图片
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            QrCodeKit.encodeOutPutSteam(output, qrCodeUrl, BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200);
            byte[] byteArr = output.toByteArray();
            String str = Base64.getEncoder().encodeToString(byteArr);
            String imgStr = new StringBuilder().append("data:image/png;base64,").append(str).toString();

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(imgStr);
            return baseResponse;

        } catch (IOException e) {
            log.error("error:{}", e);
            e.printStackTrace();
            return BaseResponse.fail("服务器错误");
        }
    }

    @GetMapping("/setRole")
    // 创建业务订单
    private BaseResponse createOrder(HttpServletRequest request, HttpServletResponse response){

        // 从数据库中获取角色的价格

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(WxPayKit.generateStr());
        payOrder.setAmount(1); // 1分 1元 = 10角 = 100分
        payOrder.setUserId(SessionHelper.GetLoginUserID());
        payOrder.setOrderState(PayOrder.NOT_PAYED);
        payOrder.setProduct("admin管理页");
        payOrder.setContent("获取管理员角色");

        payOrderService.insert(payOrder);

        String ip = IpKit.getRealIp(request);
        if (StringUtils.isEmpty(ip)) {
            ip = "127.0.0.1";
        }

        return createCode(payOrder.getOrderNo(),ip, payOrder.getContent(), "详情", "附加", payOrder.getAmount());
    }

    /**
     * 异步通知
     */
    @RequestMapping(value = "/payNotify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String payNotify(HttpServletRequest request) {
        String xmlMsg = HttpKit.readData(request);
        log.info("支付通知=" + xmlMsg);
        Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);

        String returnCode = params.get("return_code");
        String resultCode = params.get("result_code");
        String transactionId = params.get("transaction_id"); // 微信订单号
        String out_trade_no = params.get("out_trade_no"); // 商户订单号
        String attach = params.get("attach"); // 附加数据

        log.info("微信订单号：" + transactionId);
        log.info("订单号：" + out_trade_no);

        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        // 注意此处签名方式需与统一下单的签名类型一致
        getApiConfig();
        if (WxPayKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPartnerKey(), SignType.HMACSHA256)) {
            if (WxPayKit.codeIsOk(returnCode) && WxPayKit.codeIsOk(resultCode)) {
                // 更新订单信息
                // 发送通知等

                updateOrder(out_trade_no);

                Map<String, String> xml = new HashMap<String, String>(2);
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return WxPayKit.toXml(xml);
            }
        }
        return null;
    }

    // 更新订单 && 发送商品
    private void updateOrder(String orderNo){
        payOrderService.completeOrder(orderNo);
        PayOrder payOrder = payOrderService.getByOrderNo(orderNo);


        log.info("设置指定角色");
        // 赋予用户特定角色
        // userRole.updateRole(userId,payOrder.getProduct());
    }


    /**
     * 查询订单
     *
     * @param transactionId
     * @param outTradeNo
     * @return
     */
    @RequestMapping(value = "/queryOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String queryOrder(@RequestParam(value = "transactionId", required = false) String transactionId, @RequestParam(value = "outTradeNo", required = false) String outTradeNo) {
        try {
            getApiConfig();
            WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig();

            Map<String, String> params = OrderQueryModel.builder()
                    .appid(wxPayApiConfig.getAppId())
                    .mch_id(wxPayApiConfig.getMchId())
                    .transaction_id(transactionId)
                    .out_trade_no(outTradeNo)
                    .nonce_str(WxPayKit.generateStr())
                    .build()
                    .createSign(wxPayApiConfig.getPartnerKey(), SignType.MD5);
            log.info("请求参数：{}", WxPayKit.toXml(params));
            String query = WxPayApi.orderQuery(params);
            log.info("查询结果: {}", query);

//            if(成功了){
//                updateOrder();
//            }

            return query;
        } catch (Exception e) {
            e.printStackTrace();
            return "系统错误";
        }
    }

}
