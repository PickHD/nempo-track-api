package com.github.PickHD.nempo_track_api.domain.repository;

import com.github.PickHD.nempo_track_api.domain.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    Optional<User> findByUsername(String username);
}
