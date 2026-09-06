package mychat.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

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

        assertNotEquals(first, second);
        assertTrue(encoder.matches(raw, first));
        assertTrue(encoder.matches(raw, second));
        assertFalse(encoder.matches("wrong-password", first));
    }
}
