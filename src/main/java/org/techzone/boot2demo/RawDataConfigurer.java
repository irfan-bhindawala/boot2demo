package org.techzone.boot2demo;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class RawDataConfigurer {

	@Bean
	public CommandLineRunner initdata(MongoOperations mongoOperations) {
		
		return (String... args) -> {
			mongoOperations.dropCollection(City.class);
			mongoOperations.createCollection(City.class);
			getCities().forEach(mongoOperations::save);
		};
	}
	
	private List<City> getCities() {
		Properties yamlProperties = loadCitiesYaml();
		ConfigurationPropertySource source = new MapConfigurationPropertySource(yamlProperties);
		return new Binder(source).bind("cities", Bindable.listOf(City.class)).get();
	}
	
	private Properties loadCitiesYaml() {
		YamlPropertiesFactoryBean yamlPropertiesFactory = new YamlPropertiesFactoryBean();
		yamlPropertiesFactory.setResources(new ClassPathResource("cities.yml"));
		return yamlPropertiesFactory.getObject();
	}
}
