package com.jun.seeyouagain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "data.t3")
@Data
public class MyProperties {
    private String name;
}
