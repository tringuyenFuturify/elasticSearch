package io.futurify.frp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HelloWorld3Application {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorld3Application.class, args);
	}

}
