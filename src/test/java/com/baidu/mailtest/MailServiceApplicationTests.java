package com.baidu.mailtest;

import com.baidu.mailtest.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MailServiceApplicationTests {

    @Resource
    MailService mailService;

    @Test
    public void sayMail() {

        mailService.sendSimpleMail("liuyang@easylabplus.com", "SpringBoot邮件测试", "今天需要很多美女陪我");

        log.info("发送成功");
    }

}
