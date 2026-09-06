package mychat.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    private final PasswordEncoder encoder;

    public PasswordEncoderTest() {
        encoder = new PasswordEncoderConfig().getPasswordEncoder();
    }

    @Test
    public void testPasswordEncoder() {
        final var raw = "test-password-1234";

        final var first = encoder.encode(raw);
        final var second = encoder.encode(raw);

        IO.println(first);
        IO.println(second);

        assertThat(first).isNotEqualTo(second);
        assertThat(encoder.matches(raw, first)).isTrue();
        assertThat(encoder.matches(raw, second)).isTrue();
        assertThat(encoder.matches("wrong-password", first)).isFalse();
    }
}
