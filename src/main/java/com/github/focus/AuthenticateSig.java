package com.github.focus;

import com.github.focus.util.SignaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2018/8/31.
 * @Date 2018/8/31 20:06
 */
public class AuthenticateSig {
    //编码格式
    protected static final String ENCODE = "UTF-8";
    private static final Log log = LogFactory.getLog(EmailExt.class);

    /***
     * 请求人机验证鉴权接口
     * @param accessKeyId 阿里云的 AccessKey
     * @param token 人机验证返回的 token  https://help.aliyun.com/document_detail/66317.html?spm=a2c4g.11186623.4.1.54907757fD4x7D
     * @param sig 人机验证返回的
     * @param csessionid 人机验证返回的
     * @param scene 自定义
     * @param remoteIp 自定义
     * @param appKey 人机验证的key
     * @param keySecret 阿里云的 keySecret
     * @return 100时 验证通过 其他不通过
     */
    public static String sendAuthenticateSig(String accessKeyId, String token, String sig, String csessionid, String scene, String remoteIp, String appKey, String keySecret) {
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
            paras.put("Timestamp", df.format(new Date()));
            paras.put("Format", "XML");
            paras.put("Version", "2018-01-12");
            // 2. 业务API参数
            paras.put("Action", "AuthenticateSig");
            paras.put("Token", token);
            paras.put("Sig", sig);
            paras.put("SessionId", csessionid);
            paras.put("Scene", scene);
            paras.put("RemoteIp", remoteIp);
            paras.put("AppKey", appKey);
            paras.put("RegionId", "cn-hangzhou");


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

            StringBuffer buffer = new StringBuffer("http://afs.aliyuncs.com/?Signature=");
            buffer.append(signature + sortQueryStringTmp);

            String result = SignaUtil.doGet(buffer.toString(), ENCODE);

            //处理结果
            returnMsg = SignaUtil.getCode(result, "/AuthenticateSigResponse");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMsg = "鉴权异常" + e.getMessage();
        }

        return returnMsg;
    }
}
