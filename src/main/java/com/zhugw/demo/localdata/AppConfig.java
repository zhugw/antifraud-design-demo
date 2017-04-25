package com.zhugw.demo.localdata;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by zhuguowei on 4/25/17.
 */
@Configuration
@ComponentScan(basePackages = "com.zhugw.demo.localdata")
public class AppConfig {

    @Bean(name = "ruleDaoProperties")
    public PropertiesFactoryBean mapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("rule-dao.properties"));
        return bean;
    }
}
