package com.baidu.mailtest;

import com.baidu.mailtest.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MailServiceApplicationTests {

    @Resource
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    public final static String TO_USER_NAME = "liuyang@easylabplus.com";

    /**
     * 一般测试
     */
    @Test
    public void sendSimpleMail() {

        mailService.sendSimpleMail(TO_USER_NAME, "SpringBoot邮件测试", "今天需要很多美女陪我");

        log.info("sendSimpleMail" + ":" + "发送成功");
    }

    /**
     * html测试
     */
    @Test
    public void sendHtmlMail() {


        String htmlString = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";

        mailService.sendHtmlMail(TO_USER_NAME, "SpringBoot邮件测试", htmlString);

        log.info("sendHtmlMail" + ":" + "发送成功");
    }


    /**
     * 附件测试1
     */
    @Test
    public void sendAttachmentsMail1() {
        String filePath = "C:\\Users\\Administrator\\Desktop\\SpringCloud2018.mmap";
        mailService.sendAttachmentsMail1(TO_USER_NAME, "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    /**
     * 附件测试2
     */
    @Test
    public void sendAttachmentsMail2() {

        String filePath = "C:\\Users\\Administrator\\Desktop\\rentest.sql";

        mailService.sendAttachmentsMail2(TO_USER_NAME, "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    /**
     * 图片测试
     */
    @Test
    public void sendInlineResourceMail() {
        String imagePath = "C:\\Users\\Administrator\\Pictures\\1.png";
        String resourceId = "1";
        String content = "<html><body>这是有图片的邮件:\n<img src=\'cid:" + resourceId + "\'></body></html>";

        mailService.sendInlineResourceMail("shenshiyishao@163.com", "主题：PNG邮件", content, imagePath, resourceId);
    }



    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

//        mailService.sendHtmlMail("shenshiyishao@163.com","主题：这是模板邮件",emailContent);
        String imagePath = "C:\\Users\\Administrator\\Pictures\\1.png";
        String resourceId = "1";
        mailService.sendInlineResourceMail("shenshiyishao@163.com", "主题：PNG邮件", emailContent, imagePath, resourceId );

    }
}
