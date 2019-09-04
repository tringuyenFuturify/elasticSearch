package com.example.demo.config;

import java.util.Base64;
import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;

@ConfigurationProperties(prefix = "security.config")
@Getter
public class SecurityProperties {
  
  private Integer time;
  
  private String prefix;
  
  private String key;
  
  private String header;
  
  @PostConstruct
  protected void init() {
    key = Base64.getEncoder().encodeToString(key.getBytes());
  }

}
