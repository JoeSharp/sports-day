package com.ratracejoe.sportsday.ports.incoming.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ISportsDayUserSupplierTest {
  @Test
  void hasSingleRole() {
    // Given
    ISportsDayUserSupplier supplier = withRoles(SportsDayRole.PE_TEACHER);

    // When, Then
    assertDoesNotThrow(() -> supplier.userHasRole(SportsDayRole.PE_TEACHER));
  }

  @Test
  void hasOneOfRoles() {
    // Given
    ISportsDayUserSupplier supplier = withRoles(SportsDayRole.STUDENT, SportsDayRole.VOLUNTEER);

    // When, Then
    assertDoesNotThrow(() -> supplier.userHasRole(SportsDayRole.VOLUNTEER));
  }

  @Test
  void doesNotHaveSpecificRole() {
    // Given
    ISportsDayUserSupplier supplier = withRoles(SportsDayRole.FORM_TUTOR);

    // When, Then
    assertThatThrownBy(() -> supplier.userHasRole(SportsDayRole.STUDENT))
        .isInstanceOf(UnauthorisedException.class);
  }

  @Test
  void doesNotHaveOneOfRoles() {
    // Given
    ISportsDayUserSupplier supplier = withRoles(SportsDayRole.STUDENT, SportsDayRole.VOLUNTEER);

    // When, Then
    assertThatThrownBy(() -> supplier.userHasRole(SportsDayRole.PE_TEACHER))
        .isInstanceOf(UnauthorisedException.class);
  }

  @Test
  void doesNotHaveAllRoles() {
    // Given
    ISportsDayUserSupplier supplier = withRoles(SportsDayRole.STUDENT, SportsDayRole.VOLUNTEER);

    // When, Then
    assertThatThrownBy(() -> supplier.userHasRoles(SportsDayRole.STUDENT, SportsDayRole.PE_TEACHER))
        .isInstanceOf(UnauthorisedException.class);
  }

  @Test
  void noUserIsUnauthorised() {
    // Given
    ISportsDayUserSupplier supplier = () -> null;

    // When, Then
    assertThatThrownBy(() -> supplier.userHasRoles(SportsDayRole.STUDENT))
        .isInstanceOf(UnauthorisedException.class);
  }

  private ISportsDayUserSupplier withRoles(SportsDayRole... roles) {
    return () -> new SportsDayUser(UUID.randomUUID().toString(), "Foo", List.of(roles));
  }
}
