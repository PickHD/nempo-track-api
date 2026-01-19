package com.github.PickHD.nempo_track_api.config;

import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.model.Team;
import com.github.PickHD.nempo_track_api.domain.repository.AssetRepository;
import com.github.PickHD.nempo_track_api.domain.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("dev")
public class DatabaseSeeder {
    @Bean
    CommandLineRunner initDatabase(AssetRepository assetRepo, TeamRepository teamRepo) {
        return args -> {
            System.out.println("Start initialize Neo4j data..");

            assetRepo.deleteAll();
            teamRepo.deleteAll();
            System.out.println("Database cleaned..");

            // create 4 nodes (3 asset, 1 team)
            Asset neo4j = new Asset();
            neo4j.setName("Neo4j Database");
            neo4j.setType("DATABASE");
            neo4j.setStatus("ACTIVE");

            Asset backendApp = new Asset();
            backendApp.setName("Nempotrack-Backend-Service");
            backendApp.setType("SERVICE");
            backendApp.setStatus("ACTIVE");

            Asset physicServer = new Asset();
            physicServer.setName("Server-Rack-01");
            physicServer.setType("SERVER");
            physicServer.setStatus("ACTIVE");

            Team alphaTeam = new Team();
            alphaTeam.setName("Alpha Team");
            alphaTeam.setEmail("alpha@gmail.com");

            teamRepo.save(alphaTeam);

            neo4j.setOwner(alphaTeam);
            backendApp.setOwner(alphaTeam);
            physicServer.setOwner(alphaTeam);

            assetRepo.save(physicServer);
            assetRepo.save(neo4j);
            assetRepo.save(backendApp);

            System.out.println("Seeder successfully created..");
        };
    }
}
