package com.ratracejoe.sportsday.repository.jpa.repository;

import com.ratracejoe.sportsday.repository.jpa.entity.EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, UUID> {}
