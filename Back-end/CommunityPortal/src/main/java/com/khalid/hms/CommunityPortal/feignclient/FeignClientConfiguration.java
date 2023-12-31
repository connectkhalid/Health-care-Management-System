package com.khalid.hms.CommunityPortal.feignclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public TokenInterceptor customTokenInterceptor() {
        return new TokenInterceptor();
    }
}
