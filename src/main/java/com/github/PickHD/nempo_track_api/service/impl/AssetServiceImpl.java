package com.github.PickHD.nempo_track_api.service.impl;

import com.github.PickHD.nempo_track_api.api.dto.request.AssetRequest;
import com.github.PickHD.nempo_track_api.api.mapper.AssetMapper;
import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.model.Team;
import com.github.PickHD.nempo_track_api.domain.repository.AssetRepository;
import com.github.PickHD.nempo_track_api.domain.repository.TeamRepository;
import com.github.PickHD.nempo_track_api.domain.repository.projection.AssetTeamProjection;
import com.github.PickHD.nempo_track_api.exception.ResourceNotFoundException;
import com.github.PickHD.nempo_track_api.service.AssetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepo;
    private final TeamRepository teamRepo;
    private final AssetMapper assetMapper;

    @Override
    @Transactional(readOnly= true)
    public Page<Asset> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return assetRepo.findAllAssetsOnly(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> findByType(String type) {
        return assetRepo.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Asset> findById(String id) {
        return assetRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetTeamProjection> findTeamsByAssetIds(List<String> assetIds) {
        return assetRepo.findTeamsByAssetIds(assetIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asset> findImpactedAssets(String rootAssetId, String targetStatus, String targetName) {
        if (!assetRepo.existsById(rootAssetId)) {
            throw new ResourceNotFoundException("root asset id not found");
        }

        return assetRepo.findImpactedAssets(rootAssetId, targetStatus, targetName);
    }

    @Override
    @Transactional
    public Asset createAsset(AssetRequest req) {
        Asset asset = assetMapper.toEntity(req);

        if (req.teamId() != null) {
            Team team = teamRepo.findById(req.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("team id not found"));
            asset.setOwner(team);
        }

        return assetRepo.save(asset);
    }

    @Override
    @Transactional
    public Asset linkAssets(String sourceId, String targetId) {
        Asset source = assetRepo.findById(sourceId)
                .orElseThrow(() -> new ResourceNotFoundException("asset source id not found"));
        Asset target = assetRepo.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("asset target id not found"));

        // ensure source & target id is different, due avoid circular self
        if (source.getId().equals(target.getId())) {
            throw new IllegalArgumentException("Asset cannot depend on itself");
        }

        source.addDependency(target);

        return assetRepo.save(source);
    }
}
