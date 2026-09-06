package mychat.security;

import mychat.domain.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenServiceTest {
    private final TokenService tokenService;
    private final JwtConfig config;

    public TokenServiceTest() {
        final var testSecret = "7+PLd8f2iij3+191CPlA5ML49p2agvn1amACymHmGVY=";
        final var properties = new JwtProperties("https://test.local", testSecret, Duration.ofSeconds(3600));
        this.config = new JwtConfig(properties);
        this.tokenService = new TokenService(config.jwtEncoder(), properties);
    }

    @Test
    public void GivenAuthentication_WhenGenerateToken_ThenReturnsValidToken() {
        final var principal = new AppUserDetails(new AppUser(42L, "me@test.com", null));
        final var authentication = new UsernamePasswordAuthenticationToken(principal, null, null);

        final var token = tokenService.generateToken(authentication);

        assertThat(token).isNotBlank();

        final var claims = config.jwtDecoder().decode(token);

        assertThat(claims).isNotNull();
        assertThat(claims.getSubject()).isEqualTo("42");
        assertThat(claims.getIssuer()).isNotNull();
        assertThat(claims.getIssuer().toString()).isEqualTo("https://test.local");
        assertThat((String) claims.getClaim("email")).isEqualTo("me@test.com");
    }
}
