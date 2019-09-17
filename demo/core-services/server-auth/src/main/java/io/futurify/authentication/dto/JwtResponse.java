package io.futurify.authentication.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JwtResponse {	
	@JsonProperty("token_type")
	private final String tokenType = "Bearer";
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expired_at")
	private Date expiredAt;
}
