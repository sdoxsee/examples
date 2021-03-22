package ca.simplestep.example.resourceserver.jwt.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SimpleLauncher {

	public static void main(String[] args) {
		SpringApplication.run(SimpleLauncher.class, args);
	}

	@RestController
	public static class MyController {
		@GetMapping("/")
		public String getHello() {
			return "hello";
		}
	}

}
