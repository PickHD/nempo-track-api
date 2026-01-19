package com.github.PickHD.nempo_track_api.api.dto.request;

public record AssetRequest(
        String name,
        String type,
        String status,
        String teamId
) {
}
