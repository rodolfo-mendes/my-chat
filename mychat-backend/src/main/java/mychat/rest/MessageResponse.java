package mychat.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record MessageResponse(
    @NotNull Long id,
    @NotBlank String text,
    @NotNull OffsetDateTime receivedAt
) {}
