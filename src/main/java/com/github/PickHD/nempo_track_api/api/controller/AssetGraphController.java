package com.github.PickHD.nempo_track_api.api.controller;

import com.github.PickHD.nempo_track_api.api.dto.request.AssetRequest;
import com.github.PickHD.nempo_track_api.api.dto.response.AssetResponse;
import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.model.Team;
import com.github.PickHD.nempo_track_api.domain.repository.projection.AssetTeamProjection;
import com.github.PickHD.nempo_track_api.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AssetGraphController {
    private final AssetService assetService;

    @QueryMapping
    public AssetResponse assets(@Argument Integer page, @Argument Integer size) {
        int p = (page != null) ? page : 1;
        int s = (size != null) ? size : 10;

        // force page to 1 if user trying to send 0 or negative value to backend
        if (p < 1) {
            p = 1;
        }

        // decrease page - 1 because pagination in spring boot start from 0
        Page<Asset> pageResult = assetService.findAll(p-1, s);

        return new AssetResponse(
                pageResult.getContent(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements(),
                pageResult.getNumber() + 1
        );
    }

    @QueryMapping
    public Optional<Asset> assetById(@Argument String id) {
        return assetService.findById(id);
    }

    @QueryMapping
    public List<Asset> assetsByType(@Argument String type) {
        return assetService.findByType(type);
    }

    @QueryMapping
    public List<Asset> impactAnalysis(@Argument String assetId, @Argument String statusFilter, @Argument String nameFilter) {
        return assetService.findImpactedAssets(assetId, statusFilter, nameFilter);
    }

    @MutationMapping
    public Asset addAsset(@Argument AssetRequest req) {
        return assetService.createAsset(req);
    }

    @MutationMapping
    public Asset linkAssets(@Argument String sourceId, @Argument String targetId) {
        return assetService.linkAssets(sourceId, targetId);
    }

    @BatchMapping(typeName = "Asset", field = "owner")
    public Map<Asset, Team> owner(List<Asset> assets) {
        // retrieve all id from list assets
        List<String> assetIds = assets.stream()
                .map(Asset::getId)
                .toList();

        // only one trip to fetching teams
        List<AssetTeamProjection> projections = assetService.findTeamsByAssetIds(assetIds);

        Map<String, Team> teamMap = projections.stream()
                .collect(Collectors.toMap(
                        proj -> proj.asset().getId(),
                        AssetTeamProjection::team));

        Map<Asset, Team> result = new HashMap<>();

        for (Asset asset : assets) {
            Team team = teamMap.get(asset.getId());
            result.put(asset, team);
        }

        return result;
    }
}
