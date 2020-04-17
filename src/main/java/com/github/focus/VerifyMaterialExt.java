package com.github.focus;

import com.github.focus.util.QuerySingAture;
import com.github.focus.util.SignaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * 阿里云图片识别
 *
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2020/4/15.
 * @Date 2020/4/15 18:51
 */
public class VerifyMaterialExt {
    //编码格式
    protected static final String ENCODE = "UTF-8";
    private static final Log log = LogFactory.getLog(VerifyMaterialExt.class);

    /***
     *通用图片识别
     * @param key 阿里云的 AccessKey
     * @param imageURL 要识别的图片地址
     * @param keySecret 阿里云的 accessSecret
     *                  @param action
     *                  RecognizeBankCard 银行卡 ；
     *                  RecognizeAccountPage 户口页；
     *                  RecognizeDrivingLicense 行驶证识别;
     *                  RecognizeLicensePlate 车牌识别；
     *                  RecognizeBusinessCard 名片；
     *                  RecognizeChinapassport 中国护照；
     *                  RecognizeBusinessLicense 营业执照；
     *                  RecognizeStamp 公章识别；
     *
     * @return
     */
    public static String recognize(String key, String imageURL, String keySecret, String action) {
        String returnMsg = "";
        java.util.Map<String, String> paras = new java.util.HashMap<String, String>();
        try {
            setParasMap(key, paras);
            paras.put("ImageURL", imageURL);
            paras.put("Action", action);
            QuerySingAture querySingAture = new QuerySingAture(keySecret, paras).invoke();
            String signature = querySingAture.getSignature();
            StringBuilder sortQueryStringTmp = querySingAture.getSortQueryStringTmp();
            StringBuffer buffer = new StringBuffer("http://ocr.cn-shanghai.aliyuncs.com/?Signature=");
            buffer.append(signature + sortQueryStringTmp);
            returnMsg = SignaUtil.doPost(buffer.toString(), ENCODE);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMsg = "识别异常" + e.getMessage();
        }
        return returnMsg;
    }

    /***
     * 身份证正反面识别
     * @param key 阿里云的 AccessKey
     * @param imageURL 要识别的身份证图片地址
     * @param keySecret 阿里云的 accessSecret
     * @param side 身份证正反面类型。face：正面。back：反面。
     * @return
     */
    public static String identityCard(String key, String imageURL, String keySecret, String side) {
        //身份证正反面类型。face：正面。back：反面。
        if (SignaUtil.isEmpty(side)) {
            throw new RuntimeException("side 参数缺失");
        }
        if ("face".equals(side)) {
            return identityCardByFace(key, imageURL, keySecret);
        }
        if ("back".equals(side)) {
            return identityCardByBack(key, imageURL, keySecret);
        }
        throw new RuntimeException("side 参数不匹配");
    }

    /***
     *识别身份证正面
     * @param key 阿里云的 AccessKey
     * @param imageURL 要识别的身份证图片地址
     * @param keySecret 阿里云的 accessSecret
     * @return
     * 失败返回示例
     * {"RequestId":"E1597033-D127-491E-A521-22EBE5C23722","HostId":"ocr.cn-shanghai.aliyuncs.com","Code":"InvalidImage.Content","Message":"url response: [404, Not Found]"}
     * 成功返回示例
     * {"RequestId":"FEFC0AEB-6DCB-4E32-8FA8-66809126C8D1","Data":{"FrontResult":{"Address":"广州市","Gender":"女","Nationality":"汉","Name":"李XX","IDNumber":"4205xxxx069","BirthDate":"19891010"}}}
     *
     */
    protected static String identityCardByFace(String key, String imageURL, String keySecret) {
        String returnMsg = "";
        java.util.Map<String, String> paras = new java.util.HashMap<String, String>();
        try {
            setParasMap(key, paras);
            paras.put("ImageURL", imageURL);
            paras.put("Side", "face");
            paras.put("Action", "RecognizeIdentityCard");

            // 4. 参数KEY排序
            java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
            sortParas.putAll(paras);
            QuerySingAture querySingAture = new QuerySingAture(keySecret, paras).invoke();
            String signature = querySingAture.getSignature();
            StringBuilder sortQueryStringTmp = querySingAture.getSortQueryStringTmp();
            StringBuffer buffer = new StringBuffer("http://ocr.cn-shanghai.aliyuncs.com/?Signature=");
            buffer.append(signature + sortQueryStringTmp);
            returnMsg = SignaUtil.doPost(buffer.toString(), ENCODE);
            log.debug("阿里云图片识别接口调用成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMsg = "识别异常" + e.getMessage();
        }
        return returnMsg;
    }

    /***
     *识别身份证背面
     * @param key 阿里云的 AccessKey
     * @param imageURL 要识别的身份证图片地址
     * @param keySecret 阿里云的 accessSecret
     * @return
     * 失败返回示例
     * {"RequestId":"E1597033-D127-491E-A521-22EBE5C23722","HostId":"ocr.cn-shanghai.aliyuncs.com","Code":"InvalidImage.Content","Message":"url response: [404, Not Found]"}
     * 成功返回示例
     * {"RequestId":"FEFC0AEB-6DCB-4E32-8FA8-66809126C8D1","Data":{"FrontResult":{"Address":"广州市","Gender":"女","Nationality":"汉","Name":"李XX","IDNumber":"4205xxxx069","BirthDate":"19891010"}}}
     *
     */
    protected static String identityCardByBack(String key, String imageURL, String keySecret) {
        String returnMsg = "";
        java.util.Map<String, String> paras = new java.util.HashMap<String, String>();
        try {
            setParasMap(key, paras);
            paras.put("ImageURL", imageURL);
            paras.put("Side", "back");
            paras.put("Action", "RecognizeIdentityCard");
            QuerySingAture querySingAture = new QuerySingAture(keySecret, paras).invoke();
            String signature = querySingAture.getSignature();
            StringBuilder sortQueryStringTmp = querySingAture.getSortQueryStringTmp();
            StringBuffer buffer = new StringBuffer("http://ocr.cn-shanghai.aliyuncs.com/?Signature=");
            buffer.append(signature + sortQueryStringTmp);
            returnMsg = SignaUtil.doPost(buffer.toString(), ENCODE);
            log.debug("阿里云图片识别接口调用成功");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMsg = "识别异常" + e.getMessage();
        }
        return returnMsg;
    }

    protected static void setParasMap(String key, Map<String, String> paras) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        paras.put("AccessKeyId", key);
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new java.util.Date()));
        paras.put("Format", "JSON");
        //paras.put("ServiceCode", "ocr");
        // 2. 业务API参数
        paras.put("Version", "2019-12-30");
        paras.put("RegionId", "cn-shanghai");
    }


}
