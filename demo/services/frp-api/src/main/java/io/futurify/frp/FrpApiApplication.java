package io.futurify.frp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FrpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrpApiApplication.class, args);
	}

}
