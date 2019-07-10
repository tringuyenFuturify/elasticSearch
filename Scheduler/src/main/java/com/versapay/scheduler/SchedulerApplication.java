package com.versapay.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.versapay.scheduler.config.SchedulerConfig;

@SpringBootApplication
public class SchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApplication.class, args);
	}

	@Bean
	public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext) {
		SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
		SchedulerConfig jobFactory = new SchedulerConfig();
		jobFactory.setApplicationContext(applicationContext);

		factoryBean.setJobFactory(jobFactory);
		factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		return factoryBean;
	}

}