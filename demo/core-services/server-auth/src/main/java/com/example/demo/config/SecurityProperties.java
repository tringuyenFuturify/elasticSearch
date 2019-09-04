package com.example.demo.config;

import java.util.Base64;
import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "security.config")
@Getter
@Setter
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
