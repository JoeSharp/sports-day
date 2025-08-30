package com.ratracejoe.sportsday.web.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TestUser {
  JOE("joesharp", "password");

  final String username;
  final String password;
}
