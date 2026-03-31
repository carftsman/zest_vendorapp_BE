package com.dhatvibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer   //added for enable eureka server
public class ZestVendorappRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZestVendorappRegistryApplication.class, args);
	}


}
