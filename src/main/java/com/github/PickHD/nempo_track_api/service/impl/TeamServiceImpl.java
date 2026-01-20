package com.github.PickHD.nempo_track_api.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.github.PickHD.nempo_track_api.api.dto.request.TeamRequest;
import com.github.PickHD.nempo_track_api.api.mapper.TeamMapper;
import com.github.PickHD.nempo_track_api.domain.model.Team;
import com.github.PickHD.nempo_track_api.domain.repository.TeamRepository;
import com.github.PickHD.nempo_track_api.exception.ResourceNotFoundException;
import com.github.PickHD.nempo_track_api.service.TeamService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
  private final TeamRepository teamRepo;
  private final TeamMapper teamMapper;

  @Override
  @Transactional(readOnly = true)
  public Page<Team> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);

    return teamRepo.findAllTeamOnly(pageRequest);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Team> findById(String id) {
    return teamRepo.findById(id);
  }

  @Override
  @Transactional
  public Team createTeam(TeamRequest req) {
    Team team = teamMapper.toEntity(req);

    Optional<Team> checkTeamOpt = teamRepo.findByName(req.name());
    if (checkTeamOpt.isPresent()) {
      throw new IllegalArgumentException("team name already exist");
    }
    
    team.setName(req.name());
    
    if (req.email() != null) {
      team.setEmail(req.email());
    }

    return teamRepo.save(team);
  }

  @Override
  @Transactional
  public String deleteTeam(String id) {
    if (!teamRepo.existsById(id)) {
      throw new ResourceNotFoundException("team not found");
    }

    teamRepo.deleteById(id);

    return id;
  }
}

