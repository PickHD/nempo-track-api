package com.github.PickHD.nempo_track_api.api.dto.response;

import com.github.PickHD.nempo_track_api.domain.model.Asset;

import java.util.List;

public record AssetResponse(
        List<Asset> content,
        int totalPages,
        long totalElements,
        int currentPage
) {}
