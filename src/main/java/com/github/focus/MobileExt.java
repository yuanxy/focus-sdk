package com.github.focus;

import com.github.focus.util.SignaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 发送手机短信
 *
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2018/8/6.
 * @Date 2018/8/6 20:11
 */
public class MobileExt {
    //编码格式
    protected static final String ENCODE = "UTF-8";
    private static final Log log = LogFactory.getLog(MobileExt.class);

    /***
     *发送文本短信
     * @param key 阿里云的 AccessKey
     * @param phoneNumbers 需要发送短信的手机号 多个逗号分隔 13312341234,18912341234
     * @param signName 短信签名
     * @param tParam 短信参数 json字符串 如 {"oId":"123","words":"456","time":"2018-8-6"}
     * @param tCode 如 SMS_136
     * @param keySecret 阿里云的 accessSecret
     * @return 成功返回ok 失败返回失败信息
     */
    public static String sendMobileSms(String key, String phoneNumbers, String signName, String tParam, String tCode, String keySecret) {
        String returnMsg = "OK";

        try {
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
            java.util.Map<String, String> paras = new java.util.HashMap<String, String>();
            // 1. 系统参数
            paras.put("SignatureMethod", "HMAC-SHA1");
            paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
            paras.put("AccessKeyId", key);
            paras.put("SignatureVersion", "1.0");
            paras.put("Timestamp", df.format(new java.util.Date()));
            paras.put("Format", "XML");
            // 2. 业务API参数
            paras.put("Action", "SendSms");
            paras.put("Version", "2017-05-25");
            paras.put("RegionId", "cn-hangzhou");
            paras.put("PhoneNumbers", phoneNumbers);
            paras.put("SignName", signName);
            if (!SignaUtil.isEmpty(tParam)) {
                paras.put("TemplateParam", tParam);
            }
            paras.put("TemplateCode", tCode);

            //paras.put("OutId", "123");
            // 3. 去除签名关键字Key
            if (paras.containsKey("Signature"))
                paras.remove("Signature");
            // 4. 参数KEY排序
            java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
            sortParas.putAll(paras);
            // 5. 构造待签名的字符串
            java.util.Iterator<String> it = sortParas.keySet().iterator();
            StringBuilder sortQueryStringTmp = new StringBuilder();
            while (it.hasNext()) {
                String strKey = it.next();
                sortQueryStringTmp.append("&").append(SignaUtil.specialUrlEncode(strKey)).append("=").append(SignaUtil.specialUrlEncode(paras.get(strKey)));
            }
            String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
            StringBuilder stringToSign = new StringBuilder();
            stringToSign.append("GET").append("&");
            stringToSign.append(SignaUtil.specialUrlEncode("/")).append("&");
            stringToSign.append(SignaUtil.specialUrlEncode(sortedQueryString));
            String sign = SignaUtil.sign(keySecret + "&", stringToSign.toString());
            // 6. 签名最后也要做特殊URL编码
            String signature = SignaUtil.specialUrlEncode(sign);

            StringBuffer buffer = new StringBuffer("http://dysmsapi.aliyuncs.com/?Signature=");
            buffer.append(signature + sortQueryStringTmp);
            String result = SignaUtil.doGet(buffer.toString(), ENCODE);
            //处理结果
            returnMsg = SignaUtil.getMessage(result, "/SendSmsResponse");

            if (returnMsg != null && returnMsg.equals("OK")) {
                log.debug(phoneNumbers + " 短信发送成功！！！");
            } else {
                log.error("发送失败：" + returnMsg);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMsg = "短信发送异常" + e.getMessage();
        }
        return returnMsg;
    }
}
