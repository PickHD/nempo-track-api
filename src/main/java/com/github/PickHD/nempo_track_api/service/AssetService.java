package com.github.PickHD.nempo_track_api.service;

import com.github.PickHD.nempo_track_api.api.dto.request.AssetRequest;
import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.repository.projection.AssetTeamProjection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AssetService {
    Page<Asset> findAll(int page, int size);
    List<Asset> findByType(String type);
    Optional<Asset> findById(String id);
    List<AssetTeamProjection> findTeamsByAssetIds(List<String> assetIds);
    List<Asset> findImpactedAssets(String rootId, String targetStatus, String targetName);

    Asset createAsset(AssetRequest req);
    Asset linkAssets(String sourceId, String targetId);
}
