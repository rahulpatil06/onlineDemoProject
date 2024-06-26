package com.altimetrik.serviceregistry;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //Collects data from Sleuth and provides it to Zipkin Client
    @Bean
    public Sampler samplerOb() {
        return Sampler.ALWAYS_SAMPLE;
    }

    //Creates RestTemplate Object
    @Bean
    public RestTemplate rt() {
        return new RestTemplate();
    }
}