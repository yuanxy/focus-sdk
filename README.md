# focus-sdk
基于阿里云提供的生态HTTP接口的短信以及邮件的封装，可以实现邮件、国内短信的发送。

#已实现功能:

邮件发送[√]
国内短信发送[√]

#使用参考

//短信
        System.out.println(MobileExt.sendMobileSms("***","18912344321","XX助手",
                "{\"oId\":\"12\",\"words\":\"45\",\"time\":\"2018-8-6\"}","SMS_136","***"));
                
 //邮件
        System.out.println(EmailExt.sendEmail("***", "admin@message.git.com",
                "rain.yuan@transn.com", "管理员","123","测试邮件","***"));
