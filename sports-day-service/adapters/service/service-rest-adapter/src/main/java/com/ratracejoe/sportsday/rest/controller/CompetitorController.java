package com.ratracejoe.sportsday.rest.controller;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.rest.model.CompetitorDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/competitors", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CompetitorController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompetitorController.class);
  private final ICompetitorService competitorService;

  @GetMapping("/{id}")
  public CompetitorDTO getById(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Retrieving Competitor by {}", id);
    return CompetitorDTO.fromDomain(competitorService.getById(id));
  }
}
