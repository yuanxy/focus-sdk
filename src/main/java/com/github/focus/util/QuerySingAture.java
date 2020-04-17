package com.github.focus.util;

import java.util.Map;

/**
 * 参数处理封装
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2020/4/16.
 * @Date 2020/4/16 18:50
 */
public  class QuerySingAture {
    private String keySecret;
    private Map<String, String> paras;
    private StringBuilder sortQueryStringTmp;
    private String signature;

    public QuerySingAture(String keySecret, Map<String, String> paras) {
        this.keySecret = keySecret;
        this.paras = paras;
    }

    public StringBuilder getSortQueryStringTmp() {
        return sortQueryStringTmp;
    }

    public String getSignature() {
        return signature;
    }

    public QuerySingAture invoke() throws Exception {
        // 4. 参数KEY排序
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(paras);

        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String strKey = it.next();
            sortQueryStringTmp.append("&").append(SignaUtil.specialUrlEncode(strKey)).append("=").append(SignaUtil.specialUrlEncode(paras.get(strKey)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("POST").append("&");
        stringToSign.append(SignaUtil.specialUrlEncode("/")).append("&");
        stringToSign.append(SignaUtil.specialUrlEncode(sortedQueryString));
        String sign = SignaUtil.sign(keySecret + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        signature = SignaUtil.specialUrlEncode(sign);
        return this;
    }
}
