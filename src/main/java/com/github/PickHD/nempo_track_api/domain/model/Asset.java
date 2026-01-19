package com.github.PickHD.nempo_track_api.domain.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.List;

@Node("Asset")
@Data
public class Asset {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String name;

    private String type;

    private String status;

    @Relationship(type = "DEPENDS_ON", direction = Relationship.Direction.OUTGOING)
    private List<Asset> dependencies = new ArrayList<>();

    @Relationship(type = "OWNED_BY", direction = Relationship.Direction.OUTGOING)
    private Team owner;

    public void addDependency(Asset target) {
        this.dependencies.add(target);
    }
}
