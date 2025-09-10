package com.ratracejoe.sportsday.repository.jpa.repository;

import com.ratracejoe.sportsday.repository.jpa.entity.ActivityEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, UUID> {}
