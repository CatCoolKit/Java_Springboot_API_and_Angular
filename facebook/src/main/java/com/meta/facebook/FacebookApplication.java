package com.meta.facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.util.Collections;

@SpringBootApplication
public class FacebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookApplication.class);
//		var app = new SpringApplication(FacebookApplication.class);
//		app.setDefaultProperties(Collections.singletonMap("spring.profiles.active","test"));
//		var ctx = app.run(args);
//
//		MyFirstService myService = ctx.getBean(MyFirstService.class);
//		System.out.println(myService.tellAStory());
//		System.out.println(myService.getCustomProperty());


	}



}
