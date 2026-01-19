package com.github.PickHD.nempo_track_api.domain.repository;

import com.github.PickHD.nempo_track_api.domain.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, String> {
    Optional<User> findByUsername(String username);
}
