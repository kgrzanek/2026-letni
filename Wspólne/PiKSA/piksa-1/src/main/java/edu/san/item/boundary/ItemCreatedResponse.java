// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.item.boundary;

import java.util.Objects;
import java.util.UUID;

public record ItemCreatedResponse(UUID itemId) {

  public ItemCreatedResponse {
    Objects.requireNonNull(itemId);
  }

}
