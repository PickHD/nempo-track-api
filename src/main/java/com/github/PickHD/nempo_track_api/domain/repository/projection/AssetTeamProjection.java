package com.github.PickHD.nempo_track_api.domain.repository.projection;

import com.github.PickHD.nempo_track_api.domain.model.Asset;
import com.github.PickHD.nempo_track_api.domain.model.Team;

public record AssetTeamProjection(
        Asset asset,
        Team team
) {}
