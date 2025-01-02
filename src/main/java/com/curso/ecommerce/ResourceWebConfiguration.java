package com.curso.ecommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//doble asterisco indica q se tomará todo lo que haya despues del /image
		registry.addResourceHandler("/images/**").addResourceLocations("file:images/"); 
		
	}
	
}
