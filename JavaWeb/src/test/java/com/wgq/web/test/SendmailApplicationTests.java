package com.wgq.web.test;

import com.wgq.web.MailService;
import com.wgq.web.PersonUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        PersonUrl personUrl = new PersonUrl();
        personUrl.setId(1);
        personUrl.setUrl("www.baidu.com");
        personUrl.setTitle("百度");
        personUrl.setLabel("搜索");
        personUrl.setCreateTime(Date.from(LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant()));
        List<PersonUrl> personUrlList = new ArrayList<>();
        personUrlList.add(personUrl);
        personUrlList.add(personUrl);

        ctx.setVariable("personUrlList", personUrlList);

        String mail = templateEngine.process("mailtemplate.html", ctx);
        mailService.sendHtmlMail("13433956705@163.com",
                "13433956705@163.com",
                "测试邮件主题",
                mail);
    }
}
