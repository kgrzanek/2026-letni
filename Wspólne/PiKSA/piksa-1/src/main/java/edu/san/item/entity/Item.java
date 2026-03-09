package edu.san.item.entity;

import java.util.Objects;

public record Item(String name, String description) {

  public Item {
    Objects.requireNonNull(name);
    Objects.requireNonNull(description);
  }
}
