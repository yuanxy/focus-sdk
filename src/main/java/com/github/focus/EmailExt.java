package com.github.focus;

import com.github.focus.util.SignaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2018/8/6.
 * @Date 2018/8/6 20:45
 */
public class EmailExt {
    //编码格式
    protected static final String ENCODE = "UTF-8";
    private static final Log log = LogFactory.getLog(EmailExt.class);

    /***
     * 发送邮件
     * @param accessKeyId 阿里云的 AccessKey
     * @param accountName 发件账号 来源阿里云 配置的 例如 admin@message.git.com
     * @param receiver 收件人
     * @param fromname 发件人显示名称
     * @param title 标题
     * @param content 邮件内容
     * @param keySecret 阿里云的 accessSecret
     * @return 成功返回ok 失败返回失败信息
     */
    public static String sendEmail(String accessKeyId, String accountName, String receiver, String fromname, String title, String content, String keySecret) {
        String returnMsg = "OK";
        try {

            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
            java.util.Map<String, String> paras = new java.util.HashMap<String, String>();
            // 1. 系统参数
            paras.put("SignatureMethod", "HMAC-SHA1");
            paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
            paras.put("AccessKeyId", accessKeyId);
            paras.put("SignatureVersion", "1.0");
            paras.put("Timestamp", df.format(new java.util.Date()));
            paras.put("Format", "XML");
            // 2. 业务API参数
            paras.put("Action", "SingleSendMail");
            paras.put("Version", "2015-11-23");
            paras.put("RegionId", "cn-hangzhou");
            paras.put("AccountName", accountName);

            paras.put("ReplyToAddress", "true");
            paras.put("AddressType", "1");
            paras.put("ToAddress", receiver);
            paras.put("FromAlias", fromname);
            paras.put("Subject", title);
            paras.put("HtmlBody", content);

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
                String key = it.next();
                sortQueryStringTmp.append("&").append(SignaUtil.specialUrlEncode(key)).append("=").append(SignaUtil.specialUrlEncode(paras.get(key)));
            }
            String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
            StringBuilder stringToSign = new StringBuilder();
            stringToSign.append("GET").append("&");
            stringToSign.append(SignaUtil.specialUrlEncode("/")).append("&");
            stringToSign.append(SignaUtil.specialUrlEncode(sortedQueryString));
            String sign = SignaUtil.sign(keySecret + "&", stringToSign.toString());
            // 6. 签名最后也要做特殊URL编码
            String signature = SignaUtil.specialUrlEncode(sign);

            StringBuffer buffer = new StringBuffer("https://dm.aliyuncs.com/?Signature=");
            buffer.append(signature + sortQueryStringTmp);
            String result = SignaUtil.doGet(buffer.toString(), ENCODE);
            //处理结果
            returnMsg = SignaUtil.getMessage(result, "/SingleSendMailResponse");
            if (returnMsg != null && returnMsg.equals("OK")) {
                log.info(receiver + "邮件发送成功！！！");
            } else {
                log.error("邮件发送失败：" + returnMsg);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMsg = "邮件发送异常" + e.getMessage();
        }
        return returnMsg;
    }
}
