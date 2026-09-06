package mychat.security;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtProperties properties;

    public TokenService(JwtEncoder encoder, JwtProperties properties) {
        this.encoder = encoder;
        this.properties = properties;
    }

    public String generateToken(@NonNull Authentication authentication) {
        final var principal = Objects.requireNonNull(
            (AppUserDetails) authentication.getPrincipal(),
            "Authentication principal must be non-null and of type AppUserDetails");

        final var now = Instant.now();

        final var claims = JwtClaimsSet.builder()
            .issuer(properties.issuer())
            .subject(String.valueOf(principal.getId()))
            .issuedAt(now)
            .expiresAt(now.plus(properties.expiration()))
            .claim("email", principal.getUsername())
            .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
