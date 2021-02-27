package com.jun.seeyouagain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = {"classpath:datasource.properties"}, encoding = "utf-8")
@Component
public class DataSourceProperties {
    @Value("${mysql.username}")
    private String mysqlUserName;
    @Value("${mysql.password}")
    private String mysqlPassword;
    @Value("${mysql.driverclassname}")
    private String mysqlDriver;
    @Value("${mysql.url}")
    private String mysqlUrl;
}
