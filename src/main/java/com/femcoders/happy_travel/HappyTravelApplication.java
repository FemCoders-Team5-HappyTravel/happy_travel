package com.femcoders.happy_travel;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HappyTravelApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(HappyTravelApplication.class);


		Dotenv dotenv = Dotenv.configure().load();
		Map<String, Object> envMap = new HashMap<>();
		dotenv.entries().forEach(entry -> {
			envMap.put(entry.getKey(), entry.getValue());
			System.out.println("🔍 Injected env var: " + entry.getKey() + "=" + entry.getValue());
		});

		app.addInitializers(ctx -> {
			ConfigurableEnvironment env = ctx.getEnvironment();
			env.getPropertySources().addFirst(new MapPropertySource("dotenv", envMap));
		});

		app.run(args);
	}
}
