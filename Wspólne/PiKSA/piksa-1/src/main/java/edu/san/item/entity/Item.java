package edu.san.item.entity;

import java.util.Objects;
import java.util.UUID;

public record Item(UUID id, String name, String description) {

  public Item {
    Objects.requireNonNull(id);
    Objects.requireNonNull(name);
    Objects.requireNonNull(description);
  }
}
