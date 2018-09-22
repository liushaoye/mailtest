package com.baidu.mailtest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

}
