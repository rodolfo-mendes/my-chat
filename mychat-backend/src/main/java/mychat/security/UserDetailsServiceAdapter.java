package mychat.security;

import mychat.domain.AppUserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceAdapter implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public UserDetailsServiceAdapter(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(username)
            .map(appUser -> User.builder()
                    .username(appUser.email())
                    .password(appUser.passwordHash())
                    .roles("USER")
                    .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
