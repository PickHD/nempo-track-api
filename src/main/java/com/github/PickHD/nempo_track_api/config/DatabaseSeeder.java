package com.github.PickHD.nempo_track_api.config;

import com.github.PickHD.nempo_track_api.config.properties.AppConfig;
import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.model.Team;
import com.github.PickHD.nempo_track_api.domain.model.User;
import com.github.PickHD.nempo_track_api.domain.repository.AssetRepository;
import com.github.PickHD.nempo_track_api.domain.repository.TeamRepository;
import com.github.PickHD.nempo_track_api.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDatabase(AssetRepository assetRepo,
                                   TeamRepository teamRepo,
                                   UserRepository userRepo,
                                   PasswordEncoder passwordEncoder,
                                   AppConfig appConfig) {
        return args -> {
            String adminUsername = appConfig.getUsername();
            String adminPassword = appConfig.getPassword();

            // check if user with role superadmin already created or not
            if (userRepo.findByUsername(adminUsername).isEmpty()) {
                System.out.println("[Seeder] Creating default Admin User...");

                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("SUPERADMIN");

                userRepo.save(admin);
                System.out.println("[Seeder] Admin Created..");
            }

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
