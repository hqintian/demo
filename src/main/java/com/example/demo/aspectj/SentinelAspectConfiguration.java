package com.example.demo.aspectj;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hqintian
 */
@Configuration
public class SentinelAspectConfiguration {


    @Bean
    public SentinelResourceAspect sentinelResourceAspect(int i){
        return new SentinelResourceAspect();
    }
}