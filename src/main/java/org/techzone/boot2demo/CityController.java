package org.techzone.boot2demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

	private final CityRepository cityRepository;

	public CityController(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	@GetMapping(path = "/cities")
	public Flux<City> getCities() {
		Flux<City> cities = this.cityRepository.findAll().filter(this::isInUSA);
		return cities;
	}
	
	@GetMapping(path = "/cities/{name}")
	public Mono<City> getCity(@PathVariable String name) {
		Mono<City> city = this.cityRepository.findByNameIgnoringCase(name);
		return city;
	}
	
	private boolean isInUSA(final City city) {
		return ("USA").equals(city.getCountry());
	}
}
