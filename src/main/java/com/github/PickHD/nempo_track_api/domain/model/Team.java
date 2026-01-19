package com.github.PickHD.nempo_track_api.domain.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node("Team")
@Data
public class Team {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String name;

    private String email;
}
