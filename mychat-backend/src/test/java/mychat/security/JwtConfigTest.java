package mychat.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.*;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JwtConfigTest {
    private final JwtConfig jwtConfig;

    public JwtConfigTest() {
        final var testSecret = "ipZi0XKZbBAMLf0dwhgFdzV7Wd7OS1n+QSUO7+++yIs=";
        final var properties = new JwtProperties("https://mychat.local", testSecret, Duration.ofMinutes(1));
        this.jwtConfig = new JwtConfig(properties);
    }

    @Test
    public void GivenJwtConfig_WhenEncodeDecode_ThenClaimsMatch() {
        final var claims = JwtClaimsSet.builder()
            .subject("42")
            .issuer("https://mychat.local")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(900))
            .build();

        final var token = jwtConfig.jwtEncoder()
            .encode(JwtEncoderParameters.from(claims))
            .getTokenValue();

        IO.println("Encoded JWT: " + token);

        final var decodedClaims = jwtConfig.jwtDecoder().decode(token);

        assertThat(decodedClaims.getSubject()).isEqualTo("42");
        assertThat(decodedClaims.getIssuer()).isNotNull();
        assertThat(decodedClaims.getIssuer().toString()).isEqualTo("https://mychat.local");
    }

    @Test
    public void GivenExpiredToken_WhenDecode_ThenThrowsJwtValidationException() {
        final var claims = JwtClaimsSet.builder()
                .subject("42")
                .issuer("https://mychat.local")
                .expiresAt(Instant.now().minusSeconds(900))
                .build();

        final var token = jwtConfig.jwtEncoder()
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();

        IO.println("Encoded JWT: " + token);

        assertThatThrownBy(() -> jwtConfig.jwtDecoder().decode(token))
            .isInstanceOf(JwtValidationException.class);
    }

    @Test
    public void GivenCorruptedToken_WhenDecode_ThenThrowsBadJwtException() {
        final var claims = JwtClaimsSet.builder()
            .subject("42")
            .issuer("https://mychat.local")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(900))
            .build();

        final var token = jwtConfig.jwtEncoder()
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();

        final var badToken = token.replace(token.charAt(0), (char) (token.charAt(0) + 1));

        assertThatThrownBy(() -> jwtConfig.jwtDecoder().decode(badToken))
                .isInstanceOf(BadJwtException.class);
    }
}
