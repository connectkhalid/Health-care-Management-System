package com.khalid.hms.UserProfile.networkmanager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public TokenInterceptor customTokenInterceptor() {
        return new TokenInterceptor();
    }
}
