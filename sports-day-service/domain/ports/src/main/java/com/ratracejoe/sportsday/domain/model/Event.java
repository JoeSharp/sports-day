package com.ratracejoe.sportsday.domain.model;

import java.util.UUID;

public record Event(
    UUID id, Activity activity, ParticipantType participantType, int maxParticipants) {}
