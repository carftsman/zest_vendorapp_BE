package com.dhatvibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer  // added for enable config server
public class ZestVendorappConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZestVendorappConfigServerApplication.class, args);
	}

}
