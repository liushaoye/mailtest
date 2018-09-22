package com.baidu.mailtest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/******************************
 * @author : liuyang
 * <p>ProjectName:mail  </p>
 * @ClassName :  MailService
 * @date : 2018/9/22 0022
 * @time : 22:57
 * @createTime 2018-09-22 22:57
 * @version : 2.0
 * @description :
 *
 *
 *
 *******************************/

@Service
@Slf4j
public class MailService {


    /**
     * 发件人邮箱地址
     */
    @Value("${spring.mail.username}")
    private String fromUserName;


    @Autowired
    private JavaMailSender javaMailSender;

    public final static Integer TO_MAIL_COUNT = 3;


    /**
     * 一般发送邮件方法
     *
     * @param to      发送给某人
     * @param subject 邮件主题
     * @param context 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String context) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(context);
        simpleMailMessage.setFrom(fromUserName);

        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送一封Html的邮件
     *
     * @param to      发送给某人
     * @param subject 邮件主题
     * @param context 邮件内容
     */
    public void sendHtmlMail(String to, String subject, String context) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromUserName);

            mimeMessageHelper.setTo(to);

            mimeMessageHelper.setSubject(subject);

            mimeMessageHelper.setText(context, true);

            javaMailSender.send(mimeMessage);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
        }

    }

    /**
     * 发送一封带一个附件的邮件
     *
     * @param to       发送给某人
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 邮件附件的文件地址
     */
    public void sendAttachmentsMail1(String to, String subject, String content, String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


            mimeMessageHelper.setFrom(fromUserName);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            mimeMessageHelper.addAttachment(fileName, file);

            javaMailSender.send(mimeMessage);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        } catch (StringIndexOutOfBoundsException e) {
            log.error("路径名字长度越界！", e);
        }
    }

    /**
     * 发送一封带多个附件的邮件
     *
     * @param to       发送给某人
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 邮件附件的文件地址
     */
    public void sendAttachmentsMail2(String to, String subject, String content, String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


            mimeMessageHelper.setFrom(fromUserName);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));

            //发送多个附件,将fileName做成数组,然后遍历给addAttachment进行多个不重复的附件发送.
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));

            //添加多个邮件的附件
            for (int i = 0; i < TO_MAIL_COUNT; i++) {
                mimeMessageHelper.addAttachment(fileName + i, file);
            }

            javaMailSender.send(mimeMessage);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        } catch (StringIndexOutOfBoundsException e) {
            log.error("路径名字长度越界！", e);
        } catch (MailSendException e) {
            log.error("发送路径异常！", e);
        }
    }


    /**
     * 发送静态资源,即图片发送的方法
     *
     * @param to           发送给某人
     * @param subject      邮件主题
     * @param content      邮件内容
     * @param resourcePath 资源路径
     * @param resourceId   图片编号
     */
    public void sendInlineResourceMail(String to, String subject, String content, String resourcePath, String resourceId) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromUserName);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(resourcePath));
            mimeMessageHelper.addInline(resourceId, fileSystemResource);

            javaMailSender.send(mimeMessage);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        } catch (MailSendException e) {
            log.error("文件路径过长异常！", e);
        }
    }


}
