package com.tiklab.matflow.config;

import com.tiklab.eas.stater.EasEmbedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

@Configuration
@PropertySource(value = "classpath:application-${env:dev}.properties")
public class EasConfig {

    @Autowired
    Environment environment;

    private static final Logger logger = LoggerFactory.getLogger(EasConfig.class);

    @Bean
    public void startEas() throws IOException {

        EasEmbedUtil easEmbedUtil = new EasEmbedUtil();

        String authType = environment.getProperty("auth.type");

        String url = environment.getProperty("jdbc.url");
        String mysqlName = environment.getProperty("mysql.name");

        if (url!= null && mysqlName != null){
            url = url.replaceAll(mysqlName, "tiklab_eas");
            url = url.split("\\?")[0];
        }

        String username = environment.getProperty("jdbc.username");
        String password = environment.getProperty("jdbc.password");

        String javaHome = System.getProperty("user.dir");

        if (javaHome != null){
            javaHome= new File(javaHome).getParent()+"/jdk-16.0.2";
        }
        //
        //logger.info("数据库地址 ："+url);
        //logger.info("用户名 ：" + username);
        //logger.info("密码 ： "+ password);
        //logger.info("JAVA_HOME地址 ：" + javaHome);

        Process process = easEmbedUtil.startShellEasProcess("2", url, username, password, javaHome);

        if (process == null){
            return;
        }
        InputStream inputStream = process.getInputStream();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String s;
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logger.info("开始："+s);
        }
        inputStreamReader.close();
        bufferedReader.close();
        logger.info("执行完成");
    }
}
