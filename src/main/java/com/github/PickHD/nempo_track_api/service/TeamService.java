package com.github.PickHD.nempo_track_api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.github.PickHD.nempo_track_api.api.dto.request.TeamRequest;
import com.github.PickHD.nempo_track_api.domain.model.Team;

public interface TeamService {
  Page<Team> findAll(int page, int size);
  Optional<Team> findById(String id);

  Team createTeam(TeamRequest req);
  String deleteTeam(String id); 
}
