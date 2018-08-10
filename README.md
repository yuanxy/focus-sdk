# focus-sdk
基于阿里云提供的生态HTTP接口的短信以及邮件的封装，可以实现邮件、国内短信的发送。

# maven 
http://mvnrepository.com/artifact/com.github.yuanxy/focus-sdk/1.0.0
# 安装教程
pom.xml 粘贴即可使用。

<dependency>
  <groupId>com.github.yuanxy</groupId>
  <artifactId>focus-sdk</artifactId>
  <version>1.0.0</version>
</dependency>

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
    
使用说明
确认 pom.xml 添加了 focus-sdk 的配置信息
阿里短信 MobileExt.sendMobileSms("","","","","","");
阿里邮件 EmailExt.sendEmail("","","","","","","");

# 已实现功能:

邮件发送[√]
国内短信发送[√]

# 使用参考

//短信
        System.out.println(MobileExt.sendMobileSms("***","18912344321","XX助手",
                "{\"oId\":\"12\",\"words\":\"45\",\"time\":\"2018-8-6\"}","SMS_136","***"));
                
 //邮件
        System.out.println(EmailExt.sendEmail("***", "admin@message.git.com",
                "rain.yuan@transn.com", "管理员","123","测试邮件","***"));
