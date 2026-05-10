// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.colls;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class StdLibTest {

  record Profile(UUID uuid, String email) {}

  Map<UUID, Profile> profiles = new HashMap<>();

  void add(Profile profile) {
    profiles.put(profile.uuid(), profile);
  }

  Optional<Profile> findProfile(String email) {
    for (final var profile : profiles.values()) {
      if (profile.email().equals(email))
        return Optional.of(profile);
    }

    return Optional.empty();
  }

  {
    add(new Profile(UUID.randomUUID(), "john@gmail.com"));
    add(new Profile(UUID.randomUUID(), "anna@yahoo.com"));
  }

  @Test
  void testOptional1() {
    final var tom = findProfile("tom@altavista.com");
    assertEquals(true, tom.isEmpty());
    assertEquals(false, tom.isPresent());

    IO.println("tom is " + tom);
  }

  static Function<Profile, String> emailReader = Profile::email;

  static <T, S> Function<? super T, ? extends Optional<? extends S>> liftO(
      Function<? super T, ? extends S> f) {
    return p -> Optional.ofNullable(f.apply(p));
  }

  @Test
  void testOptional2() {
    final var optionalProfile = findProfile("john@gmail.com");
    assertEquals(false, optionalProfile.isEmpty());
    assertEquals(true, optionalProfile.isPresent());

    final var optionalEmail = optionalProfile.flatMap(liftO(Profile::email));

    final var email = optionalEmail.orElseGet(() -> "non-existent@one");
    assertEquals("john@gmail.com", email);
  }

}
