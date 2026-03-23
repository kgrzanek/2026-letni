// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.item.boundary;

import java.util.Objects;

public record CreateItemCmd(String name, String description) {

  public CreateItemCmd {
    Objects.requireNonNull(name);
    Objects.requireNonNull(description);
  }
}