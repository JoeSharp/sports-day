package com.ratracejoe.sportsday.domain.model;

import java.util.UUID;

public record Competitor(UUID id, CompetitorType type, String name) {}
