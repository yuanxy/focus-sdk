package com.github.focus.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 阿里POP的签名算法工具类
 *
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2018/8/6.
 * @Date 2018/8/6 14:55
 */
public class SignaUtil {

    /***
     * specialUrlEncode
     * @param value
     * @return
     * @throws Exception
     */
    public static String specialUrlEncode(String value) throws Exception {
        // return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        return value != null ? URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }

    /***
     * sign
     * @param accessSecret
     * @param stringToSign
     * @return
     * @throws Exception
     */
    public static String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return DatatypeConverter.printBase64Binary(signData);//new sun.misc.BASE64Encoder().encode(signData);
    }

    /***
     * 解析阿里接口返回的  Message 短信邮件使用
     * @param str
     * @param nodesKey
     * @return
     * @throws DocumentException
     */
    public static String getMessage(String str, String nodesKey) throws DocumentException {
        if (!isEmpty(str)) {
            Document doc = DocumentHelper.parseText(str);
            List<Element> tplList = doc.selectNodes(nodesKey);
            if (isEmpty(tplList)) {
                //兼容发送异常时获取的数据
                tplList = doc.selectNodes("/Error");
            }
            String message = null;
            Element tpl = tplList.get(0);
            Iterator msgIt = tpl.elementIterator("Message");
            if (msgIt.hasNext()) {
                message = ((Element) msgIt.next()).getTextTrim();
            } else {
                //发送成功 没有返回值 自定义一个
                message = "OK";
            }
            return message;
        } else {
            return "";
        }
    }

    /***
     * 解析阿里接口返回的  Message 人机验证接口返回的数据
     * @param str
     * @param nodesKey
     * @return
     * @throws DocumentException
     */
    public static String getCode(String str, String nodesKey) throws DocumentException {
        if (!isEmpty(str)) {
            Document doc = DocumentHelper.parseText(str);
            List<Element> tplList = doc.selectNodes(nodesKey);
            if (isEmpty(tplList)) {
                //兼容发送异常时获取的数据
                tplList = doc.selectNodes("/Error");
            }
            String message = null;
            Element tpl = tplList.get(0);
            Iterator msgIt = tpl.elementIterator("Code");
            if (msgIt.hasNext()) {
                message = ((Element) msgIt.next()).getTextTrim();
            } else {
                //发送成功 没有返回值 自定义一个
                message = "OK";
            }
            return message;
        } else {
            return "";
        }
    }

    /***
     * 发送get请求
     * @param url
     * @param encode
     * @return
     * @throws IOException
     */
    public static String doGet(String url, String encode) throws IOException {
        String result = "";
        System.setProperty("sun.net.client.defaultConnectTimeout", "7000");
        System.setProperty("sun.net.client.defaultReadTimeout", "7000");
        HttpGet get = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(get);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, encode);
            }
        }
        return result;
    }

    /***
     * 发送get请求
     * @param url
     * @param encode
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String encode) throws IOException {
        String result = "";
        System.setProperty("sun.net.client.defaultConnectTimeout", "7000");
        System.setProperty("sun.net.client.defaultReadTimeout", "7000");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        /*post.setHeader("Accept-Encoding","identity");
        post.setHeader("x-sdk-invoke-type","normal");
        post.setHeader("Accept","JSON");
        post.setHeader("x-sdk-client","Java/2.0.0");
        post.setHeader("User-Agent","AlibabaCloud (Windows 10; amd64) Java/1.8.0_65-b17 Core/4.4.8 HTTPClient/ApacheHttpClient");
        post.setHeader("Content-MD5","yNv2dAjWtmyG76JwCHMcaA==");
       */
        CloseableHttpResponse response1 = httpclient.execute(post);
        if (response1 != null) {
            HttpEntity resEntity = response1.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, encode);
            }
        }
        return result;
    }


    /**
     * 判断参数是否非NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().trim().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }
}
