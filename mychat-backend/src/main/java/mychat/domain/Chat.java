package mychat.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.OffsetDateTime;

public record Chat(
    @Id
    Long id,
    @NotNull
    AggregateReference<AppUser, Long> appUserId,
    @NotBlank
    String title,
    OffsetDateTime createdAt
) {}
