package com.altimetrik.category;


import brave.sampler.Sampler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@OpenAPIDefinition(info = @Info(title = "Category-service API",version = "1.0",description = "OnlineShopping Application"))
@ComponentScan(basePackages = "com.altimetrik.category")
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class CategoryApplication {
	private static final Logger logger = LoggerFactory.getLogger(CategoryApplication.class);
	@Bean
    public WebClient webClient(){
		return WebClient.builder().build();
	}
	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}
	//Collects data from Sleuth and provides it to Zipkin Client
	@Bean
	public Sampler samplerOb() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
