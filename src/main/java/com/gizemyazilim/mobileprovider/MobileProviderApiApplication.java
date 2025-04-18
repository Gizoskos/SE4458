package com.gizemyazilim.mobileprovider;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MobileProviderApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		// Bu şekilde Spring context tarafından da erişilebilir hale gelir:
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));
		System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));

		SpringApplication.run(MobileProviderApiApplication.class, args);
	}
}
