// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.item.boundary;

import edu.san.validation.ValidName;
import jakarta.validation.constraints.NotNull;

public record CreateItemCmd(
    @ValidName String name,
    @NotNull String description) {}