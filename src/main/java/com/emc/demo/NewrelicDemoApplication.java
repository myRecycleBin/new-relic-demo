package com.emc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
//@EnableAsync()
@Configuration
@EnableAsync(mode=AdviceMode.ASPECTJ)
@EnableScheduling
public class NewrelicDemoApplication implements CommandLineRunner {
  @Autowired
  InBoundServiceClient client;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("NewrelicDemoApplication.AppRunner.run()");
    for (int i = 0; i < 10; i++) {
      client.simulateRegistrationMC();
      client.simulateContentMC();
    }
  }
  
  @Bean(name="workExecutor")
  public TaskExecutor taskExecutor() {
      ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
      taskExecutor.setMaxPoolSize(40);
      taskExecutor.setQueueCapacity(20);
      taskExecutor.afterPropertiesSet();
      taskExecutor.setCorePoolSize(30);
      return taskExecutor;
  }

  public static void main(String[] args) {
    SpringApplication.run(NewrelicDemoApplication.class, args);
  }
}
