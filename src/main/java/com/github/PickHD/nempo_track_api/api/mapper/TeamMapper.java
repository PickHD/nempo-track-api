package com.github.PickHD.nempo_track_api.api.mapper;

import org.springframework.stereotype.Component;

import com.github.PickHD.nempo_track_api.api.dto.request.TeamRequest;
import com.github.PickHD.nempo_track_api.domain.model.Team;

@Component
public class TeamMapper {
  public Team toEntity(TeamRequest input) {
    Team team = new Team();
    team.setName(input.name());
    team.setEmail(input.email());

    return team;
  }
}
