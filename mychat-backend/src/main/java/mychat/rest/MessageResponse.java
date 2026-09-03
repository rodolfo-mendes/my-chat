package mychat.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

@Schema(description = "Response body for retrieving a message, including its content and the timestamp when it was received.")
public record MessageResponse(
    @Schema(description = "The unique identifier of the message.", example = "1")
    @NotNull Long id,
    @Schema(description = "The content of the message.", minLength = 1)
    @NotBlank String text,
    @Schema(description = "The timestamp when the message was received.", example = "2024-06-01T12:00:00Z")
    @NotNull OffsetDateTime receivedAt
) {}
