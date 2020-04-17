# focus-sdk
基于阿里云提供的HTTP接口的封装，可以实现邮件、国内短信的发送、人机验证鉴权、证照识别。

# 已实现功能:

###邮件发送[√]

###国内短信发送[√]

###人机验证鉴权[√]

###银行卡识别[√]

###户口页识别[√]

###行驶证识别[√]

###车牌识别[√]

###名片识别[√]

###名中国护照识别[√]

###营业执照识别[√]

###公章识别[√]


# 使用参考

# pom.xml 粘贴即可使用。
```
 <dependency>
  <groupId>com.github.yuanxy</groupId>
  <artifactId>focus-sdk</artifactId>
  <version>2.0.0</version>
 </dependency>
 ```
```
//短信
System.out.println(MobileExt.sendMobileSms("***","18912344321","XX助手",
                "{\"oId\":\"12\",\"words\":\"45\",\"time\":\"2018-8-6\"}","SMS_136","***"));
                
//邮件
System.out.println(EmailExt.sendEmail("***", "admin@message.git.com",
                "rain.yuan@transn.com", "管理员","123","测试邮件","***"));
                
//人机验证鉴权              
String code = AuthenticateSig.sendAuthenticateSig("1","1","1","1","1","1","1","1");
        System.out.println(code); 

//图片识别 - 身份证正面识别
String json = VerifyMaterialExt.identityCard("omwjWNNKLnSStH", "imgUrl", "UOcUWVK3sefX0qDXqhf8","face");
更多用法查看 VerifyMaterialExt 源码
```

```
注意如果你项目有用到 httpcomponents 请注意你的版本，如果运行报错，请复制下面的配置到pom.xml
        <!--apacheHttp-->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpcore</artifactId>
            <version>4.4.3</version>
        </dependency>

        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpmime</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient-cache</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.1</version>
        </dependency>
        <!--apacheHttp-->
使用说明 确认 pom.xml 添加了 focus-sdk 的配置信息
```
##English document
Focus-sdk
Based on the encapsulation of HTTP interface provided by Alibaba cloud, it can realize the sending of email, domestic SMS, human-computer authentication and license recognition.
#Implemented features:
###Email [√]
###Domestic SMS sending [√]
###Man machine verification and authentication [√]
###Bank card identification [√]
###Account page identification [√]
###Driving license identification [√]
###License plate recognition [√]
###Business card identification [√]
###First name Chinese passport identification [√]
###Business license identification [√]
###Official seal identification [√]
#Use reference
#You can paste pom.xml and use it.
```$xslt
 <dependency>
  <groupId>com.github.yuanxy</groupId>
  <artifactId>focus-sdk</artifactId>
  <version>2.0.1</version>
 </dependency>

```

###If your project is useful to httpcomponents, please pay attention to your version. If you run an error report, please copy the following configuration to pom.xml

```$xslt

 <!--apacheHttp-->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpcore</artifactId>
            <version>4.4.3</version>
        </dependency>

        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpmime</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient-cache</artifactId>
            <version>4.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.1</version>
        </dependency>
        <!--apacheHttp-->

```
