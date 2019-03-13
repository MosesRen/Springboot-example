package com.jehu.demo;

import com.jehu.demo.entity.Userinfo;
import com.jehu.demo.mapper.UserinfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class DemoApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Test
    public void contextLoads() throws Exception{
        logger.info("test start!");
        //userinfoMapper.register("tom","123456","123456@163.com");
        Userinfo testusrinfo = userinfoMapper.findByUsername("tom");
        logger.info(testusrinfo.getEmail());

    }

}
