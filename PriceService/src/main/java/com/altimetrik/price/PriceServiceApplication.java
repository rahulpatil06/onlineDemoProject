package com.altimetrik.price;

import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class PriceServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(PriceServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PriceServiceApplication.class, args);
	}
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
