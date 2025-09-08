package com.ratracejoe.sportsday.jpa.repository;

import com.ratracejoe.sportsday.jpa.model.CompetitorEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitorJpaRepository extends JpaRepository<CompetitorEntity, UUID> {}
