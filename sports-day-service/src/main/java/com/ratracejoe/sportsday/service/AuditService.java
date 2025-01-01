package com.ratracejoe.sportsday.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {
    public static final String AUDIT_TOPIC = "audit";

    private final KafkaTemplate<String, String> kafka;

    public void activitiesRead() {
        kafka.send(AUDIT_TOPIC, "Activities were read");
    }

}
