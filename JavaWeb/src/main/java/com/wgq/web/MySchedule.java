package com.wgq.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Repository
public class MySchedule {

    @Autowired
    private PersonUrlRepository repository;
    @Autowired
    MailService mailService;
    @Autowired
    TemplateEngine templateEngine;

//    @Scheduled(fixedDelay = 10000)
//    public void fixedDelay() {
//        System.out.println("fixedDelay:" + new Date());
//    }

    // 每天晚上8点执行
    @Scheduled(cron = "0 0 20 * * ?")
    public void cron() {
        System.out.println("MySchedule cron:" + new Date());
        //获取今天日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        String tomorrowDate = sdf.format(new Date(new Date().getTime() + 24*60*60*1000));
        System.out.println("nowDate: " + nowDate);
        System.out.println("tomorrowDate: " + tomorrowDate);

        try {
            List<PersonUrl> result = repository.findAllByCreateTimeBetween(
                    new SimpleDateFormat("yyyy-MM-dd").parse(nowDate),
                    new SimpleDateFormat("yyyy-MM-dd").parse(tomorrowDate));

            if (result.size() > 0) {
                Context ctx = new Context();
                ctx.setVariable("personUrlList", result);
                String mail = templateEngine.process("mailtemplate.html", ctx);
                mailService.sendHtmlMail("13433956705@163.com",
                        "13433956705@163.com",
                        "测试邮件主题",
                        mail);

                for (PersonUrl data : result) {
                    System.out.println("data: " + data.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
