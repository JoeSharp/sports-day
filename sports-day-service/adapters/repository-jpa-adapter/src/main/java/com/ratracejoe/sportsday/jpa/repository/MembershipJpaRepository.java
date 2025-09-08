package com.ratracejoe.sportsday.jpa.repository;

import com.ratracejoe.sportsday.jpa.model.MemberId;
import com.ratracejoe.sportsday.jpa.model.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipJpaRepository extends JpaRepository<MembershipEntity, MemberId> {}
