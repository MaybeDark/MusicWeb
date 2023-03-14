package com.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BaseConfig {

//  ObjectMapper工具
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

//  nacos依赖的Bean
    @Bean
    public IClientConfig iClientConfig() {
        return new DefaultClientConfigImpl();
    }

}
