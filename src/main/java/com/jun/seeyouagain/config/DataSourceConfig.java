package com.jun.seeyouagain.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@PropertySource(value = {"classpath:datasource.properties"},encoding = "utf-8")
@Configuration
public class DataSourceConfig {
    /**
     * mysql的配置
     * */
    @Value("${mysql.username}")
    private String mysqlUserName;
    @Value("${mysql.password}")
    private String mysqlPassword;
    @Value("${mysql.driverclassname}")
    private String mysqlDriverClassName;
    @Value("${mysql.url}")
    private String mysqlUrl;

    /**
     * h2的配置
     * */
    @Value("${h2.username}")
    private String h2UserName;
    @Value("${h2.password}")
    private String h2Password;
    @Value("${h2.driverclassname}")
    private String h2DriverClassName;
    @Value("${h2.url}")
    private String h2Url;

    @Bean("mysqlTemplate")
    public JdbcTemplate mysqlTemplate(){
        DataSource mySqlDataSource = getMySqlDataSource();
        return new JdbcTemplate(mySqlDataSource);
    }
    @Bean("h2Template")
    public JdbcTemplate h2Template(){
        DataSource h2DataSources = getH2DataSource();
        return new JdbcTemplate(h2DataSources);
    }

    @Bean("mysqlDataSource")
    @Primary
    public DataSource getMySqlDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(mysqlUserName);
        dataSource.setPassword(mysqlPassword);
        dataSource.setDriverClassName(mysqlDriverClassName);
        dataSource.setUrl(mysqlUrl);
        return dataSource;
    }

    @Bean("h2DataSource")
    public DataSource getH2DataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(h2UserName);
        dataSource.setPassword(h2Password);
        dataSource.setDriverClassName(h2DriverClassName);
        dataSource.setUrl(h2Url);
        return dataSource;
    }
}
