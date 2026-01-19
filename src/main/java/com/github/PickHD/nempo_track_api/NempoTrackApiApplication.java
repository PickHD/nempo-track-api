package com.github.PickHD.nempo_track_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NempoTrackApiApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		System.out.println("Environment Variables Loaded Manually...");

		SpringApplication.run(NempoTrackApiApplication.class, args);
	}

}
