package edu.san.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserSessionTest {

  @Test
  void counterStartsAtZero() {
    final var session = new UserSession();
    assertThat(session.getRequestCount()).isZero();
  }

  @Test
  void incrementAndGetReturnsOne() {
    final var session = new UserSession();
    assertThat(session.incrementAndGet()).isEqualTo(1L);
  }

  @Test
  void consecutiveIncrementsAreOrdered() {
    final var session = new UserSession();
    assertThat(session.incrementAndGet()).isEqualTo(1L);
    assertThat(session.incrementAndGet()).isEqualTo(2L);
    assertThat(session.incrementAndGet()).isEqualTo(3L);
  }
}
