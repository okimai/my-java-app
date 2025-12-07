package com.example.java_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class JavaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaAppApplication.class, args);
	}

	/**
	 * CORS対策.
	 * ローカルでビルドしたAngularアプリからのアクセスを許可する
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:4200", "http://127.0.0.1:4200");
			}
		};
	}

}
