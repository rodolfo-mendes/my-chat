package mychat.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import java.time.OffsetDateTime;

public record Chat(
    @Id
    Long id,
    @Column("app_user_id")
    @NotNull
    AggregateReference<User, Long> userId,
    @NotBlank
    String title,
    OffsetDateTime createdAt
) {}
