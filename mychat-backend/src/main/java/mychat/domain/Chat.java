package mychat.domain;

import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

public record Chat(
        @Id Long id,
        OffsetDateTime createdAt
) {}
