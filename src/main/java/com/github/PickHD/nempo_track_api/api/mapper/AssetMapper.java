package com.github.PickHD.nempo_track_api.api.mapper;

import com.github.PickHD.nempo_track_api.api.dto.request.AssetRequest;
import com.github.PickHD.nempo_track_api.domain.model.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {
    public Asset toEntity(AssetRequest input) {
        Asset asset = new Asset();
        asset.setName(input.name());
        asset.setType(input.type());

        if (input.status() != null) {
            asset.setStatus(input.status());
        } else {
            asset.setStatus("ACTIVE");
        }

        return asset;
    }
}
