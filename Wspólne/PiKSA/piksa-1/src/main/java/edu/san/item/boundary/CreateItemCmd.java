// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.item.boundary;

import edu.san.validation.ValidName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotNull;

@RegisterForReflection
public record CreateItemCmd(
    @ValidName String name,
    @NotNull String description) {}