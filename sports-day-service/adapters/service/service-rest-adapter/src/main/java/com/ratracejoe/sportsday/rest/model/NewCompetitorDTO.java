package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.CompetitorType;

public record NewCompetitorDTO(CompetitorType type, String name) {}
