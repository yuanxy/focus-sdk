# focus-sdk
基于阿里云提供的HTTP接口的封装，可以实现邮件、国内短信的发送、人机验证鉴权。

# 已实现功能:

###邮件发送[√]

###国内短信发送[√]

###人机验证鉴权[√]

# 使用参考

# pom.xml 粘贴即可使用。
```
 <dependency>
  <groupId>com.github.yuanxy</groupId>
  <artifactId>focus-sdk</artifactId>
  <version>1.0.0</version>
 </dependency>
 2.0.0也可以
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
```

```
注意如果你项目有用到 httpcomponents 请注意你的版本，如果运行报错，请复制下面的配置到pom.xml

<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpcore</artifactId>
    <version>4.3.1</version>
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
    <version>4.3.1</version>
</dependency>
使用说明 确认 pom.xml 添加了 focus-sdk 的配置信息
```