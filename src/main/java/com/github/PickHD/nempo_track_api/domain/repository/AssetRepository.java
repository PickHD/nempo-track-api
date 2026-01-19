package com.github.PickHD.nempo_track_api.domain.repository;

import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.repository.projection.AssetTeamProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends Neo4jRepository<Asset, String> {
    @Query(
        value = "MATCH (n:Asset) RETURN n ORDER BY n.name ASC SKIP $skip LIMIT $limit",
        countQuery = "MATCH (n:Asset) RETURN count(n)"
    )
    Page<Asset> findAllAssetsOnly(Pageable pageable);

    List<Asset> findByType(String type);

    @Query("""
        MATCH (a:Asset)-[:OWNED_BY]->(t:Team)
        WHERE a.id IN $assetIds RETURN a AS asset, t AS team
    """)
    List<AssetTeamProjection> findTeamsByAssetIds(List<String> assetIds);

    @Query("""
        MATCH (impacted: Asset)-[:DEPENDS_ON*1..5]->(root: Asset)
        WHERE root.id = $rootId
        AND ($targetStatus IS NULL
            OR impacted.status = $targetStatus)
        AND ($targetName IS NULL
            OR toLower(impacted.name) CONTAINS toLower($targetName))
        RETURN DISTINCT impacted
    """)
    List<Asset> findImpactedAssets(String rootId, String targetStatus, String targetName);
}
