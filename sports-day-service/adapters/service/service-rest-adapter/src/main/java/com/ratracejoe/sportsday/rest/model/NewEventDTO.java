package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.domain.model.EventState;
import com.ratracejoe.sportsday.domain.model.ScoreType;
import java.util.UUID;

public record NewEventDTO(
    UUID activityId,
    EventState state,
    CompetitorType competitorType,
    ScoreType scoreType,
    int maxParticipants) {}
