package org.techzone.boot2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class Boot2demoApplication {

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> commonTags() {
		return (registry) -> registry.config().commonTags("application","boot2demo");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Boot2demoApplication.class, args);
	}
}
