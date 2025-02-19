package com.ratracejoe.sportsday.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TestUser {
  JOE("joesharp", "password");

  final String username;
  final String password;
}
