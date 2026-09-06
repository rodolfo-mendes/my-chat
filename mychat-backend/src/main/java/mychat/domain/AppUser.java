package mychat.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

public record AppUser(
    @Id Long id,
    @NotBlank String email,
    @NotBlank String passwordHash
) {}
