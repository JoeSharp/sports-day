package com.ratracejoe.sportsday.repository;

import com.ratracejoe.sportsday.model.entity.ActivityEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, UUID> {}
