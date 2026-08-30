package mychat.rest;

import jakarta.validation.constraints.NotBlank;

public record MessageRequest(
    @NotBlank String text
) {}
