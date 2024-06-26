package com.altimetrik.apiGateway;

import brave.Tracer;
import brave.sampler.Sampler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@ServletComponentScan
@Slf4j
public class ApiGatewayApplication {

	private static final Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
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
