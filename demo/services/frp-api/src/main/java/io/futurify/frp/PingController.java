package io.futurify.frp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {

	@GetMapping("/ping")
	public String Ping() {
		return "frp api 2";
	}
	
}