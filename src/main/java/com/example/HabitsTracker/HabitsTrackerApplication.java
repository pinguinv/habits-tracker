package com.example.HabitsTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class HabitsTrackerApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(HabitsTrackerApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:5173")
				.allowedMethods("*");
	}

	/*
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		String VuePath = "http://localhost:5173";
		String ApiHabitsPath = "/api/habits";
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(ApiHabitsPath + "/save")
					.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/all")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/findById")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/find/{habitId}/{status}")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/deleteById")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/addDone")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/isHabitDoneCheckAndSaveOrDeleteById")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/findDoneByCreated")
						.allowedOrigins(VuePath);
				registry.addMapping(ApiHabitsPath + "/allDone")
						.allowedOrigins(VuePath);
			}
		};
	}
	*/

}
