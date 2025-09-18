package com.ratracejoe.sportsday.domain.auth;

import java.util.List;

public record SportsDayUser(String id, String name, List<SportsDayRole> roles) {
}
