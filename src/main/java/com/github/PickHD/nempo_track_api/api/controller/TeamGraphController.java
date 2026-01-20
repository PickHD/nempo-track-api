package com.github.PickHD.nempo_track_api.api.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.github.PickHD.nempo_track_api.api.dto.request.TeamRequest;
import com.github.PickHD.nempo_track_api.api.dto.response.TeamResponse;
import com.github.PickHD.nempo_track_api.domain.model.Team;
import com.github.PickHD.nempo_track_api.service.TeamService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TeamGraphController {
  private final TeamService teamService;

  @QueryMapping
  public TeamResponse teams(@Argument Integer page, @Argument Integer size) {
    int p = (page != null) ? page : 1;
    int s = (size != null) ? size : 10;

    // force page to 1 if user trying to send 0 or negative value to backend
    if (p < 1) {
      p = 1;
    }
    
    // decrease page - 1 because pagination in spring boot start from 0
    Page<Team> pageResult = teamService.findAll(p-1, s);

    return new TeamResponse(
      pageResult.getContent(),
      pageResult.getTotalPages(),
      pageResult.getTotalElements(),
      pageResult.getNumber()+1
    );
  }

  @QueryMapping
  public Optional<Team> teamById(@Argument String id) {
    return teamService.findById(id);
  }

  @MutationMapping
  public Team addTeam(@Argument TeamRequest input) {
    return teamService.createTeam(input);
  }

  @MutationMapping
  public String removeTeam(@Argument String id) {
    return teamService.deleteTeam(id);
  }
}
