package com.github.PickHD.nempo_track_api.api.dto.response;

import java.util.List;

import com.github.PickHD.nempo_track_api.domain.model.Team;

public record TeamResponse(
  List<Team> content,
  int totalPages,
  long totalElements,
  int currentPage
) {}
