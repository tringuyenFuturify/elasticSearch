package io.futurify.authentication.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizeException extends RuntimeException {

  private static final long serialVersionUID = -3041818760189950945L;
  
  private String message;

}
