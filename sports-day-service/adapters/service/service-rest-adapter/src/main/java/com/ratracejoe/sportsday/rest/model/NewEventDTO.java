package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.domain.model.ScoreType;
import java.util.UUID;

public record NewEventDTO(
    UUID activityId, CompetitorType competitorType, ScoreType scoreType, int maxParticipants) {}
