package com.ratracejoe.sportsday.domain.model;

import java.util.UUID;

public record Event(
    UUID id, UUID activityId, ParticipantType participantType, int maxParticipants) {}
