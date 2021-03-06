package com.codecool.videoservice;

import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.repository.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class VideoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	CommandLineRunner initDatabase(VideoRepository repository) {
		return args -> {
			repository.save(new Video(1L, "Deploy NodeJS", "uEVmD6n8Il0"));
			repository.save(new Video(2L, "CSS Tricks", "Qhaz36TZG5Y"));
			repository.save(new Video(3L, "HTML tutorial", "ZtyMdRzvi0w"));
		};
	}

}
