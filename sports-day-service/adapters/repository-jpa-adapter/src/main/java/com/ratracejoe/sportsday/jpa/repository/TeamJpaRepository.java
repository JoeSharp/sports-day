package com.ratracejoe.sportsday.jpa.repository;

import com.ratracejoe.sportsday.jpa.model.TeamEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamJpaRepository extends JpaRepository<TeamEntity, UUID> {}
