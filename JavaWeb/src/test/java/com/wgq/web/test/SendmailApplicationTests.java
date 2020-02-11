package com.wgq.web.test;

import com.wgq.web.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendmailApplicationTests {

    @Autowired
    MailService mailService;

//    @Test
//    public void sendMailWithImg() {
//        mailService.sendMailWithImg("13433956705@163.com",
//                "13433956705@163.com",
//                "测试邮件主题(图片)",
//                "<div>hello,这是一封带图片资源的邮件：" +
//                        "这是图片1：<div><img src='cid:p01'/></div>" +
//                        "这是图片2：<div><img src='cid:p02'/></div>" +
//                        "</div>",
//                new String[]{"C:\\Users\\wuguoquan\\Desktop\\1.png",
//                             "C:\\Users\\wuguoquan\\Desktop\\2.png"},
//                new String[]{"p01", "p02"});
//    }
//
//    @Test
//    public void sendAttachFileMail() {
//        mailService.sendAttachFileMail("13433956705@163.com",
//                "13433956705@163.com",
//                "测试邮件主题",
//                "测试邮件内容",
//                new File("D:\\GitProject\\123.txt"));
//    }
//
//    @Test
//    public void sendSimpleMail() {
//        mailService.sendSimpleMail("13433956705@163.com",
//                "13433956705@163.com",
//                "13433956705@163.com",
//                "测试邮件主题",
//                "测试邮件内容");
//    }

    @Autowired
    TemplateEngine templateEngine;
    @Test
    public void sendHtmlMailThymeleaf() {
        Context ctx = new Context();
        ctx.setVariable("username", "sang");
        ctx.setVariable("gender", "男");
        String mail = templateEngine.process("mailtemplate.html", ctx);
        mailService.sendHtmlMail("13433956705@163.com",
                "13433956705@163.com",
                "测试邮件主题",
                mail);
    }
}
