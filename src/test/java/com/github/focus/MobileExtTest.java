package com.github.focus;

import org.junit.Test;

/**
 * @Author 袁旭云【rain.yuan@transn.com】
 * Created by rain on 2018/8/6.
 * @Date 2018/8/6 20:33
 */
public class MobileExtTest {

    @Test
    public void sendTest(){

        //短信
        System.out.println(MobileExt.sendMobileSms("***","18912344321","XX助手",
                "{\"oId\":\"12\",\"words\":\"45\",\"time\":\"2018-8-6\"}","SMS_136","***"));

        //邮件
        System.out.println(EmailExt.sendEmail("***", "admin@message.git.com",
                "rain.yuan@transn.com", "管理员","123","测试邮件","***"));

    }
}
