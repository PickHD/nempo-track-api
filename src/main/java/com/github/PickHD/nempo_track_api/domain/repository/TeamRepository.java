package com.github.PickHD.nempo_track_api.domain.repository;

import com.github.PickHD.nempo_track_api.domain.model.Team;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends Neo4jRepository<Team, String> {
  @Query(
    value = "MATCH (n:Team) RETURN n ORDER BY n.name ASC SKIP $skip LIMIT $limit",
    countQuery = "MATCH (n:Team) RETURN count(n)"
  )
  Page<Team> findAllTeamOnly(Pageable pageable);

  @Query("""
    MATCH (n:Team)
    WHERE toLower(n.name) CONTAINS toLower($name)
    return n
  """)
  Optional<Team> findByName(String name);
}
