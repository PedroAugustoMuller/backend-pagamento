package app.pagamento.shared.password.infrastructure;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.pagamento.shared.password.domain.Password;
import app.pagamento.shared.password.domain.PasswordHash;
import app.pagamento.shared.password.domain.PasswordHasher;

@Component 
public class SpringPasswordHasher implements PasswordHasher {

    private final PasswordEncoder encoder;

    public SpringPasswordHasher(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public PasswordHash hash(Password password) {
        return new PasswordHash(encoder.encode(password.value()));
    }

    @Override
    public boolean matches(Password password, PasswordHash hash) {
        return encoder.matches(password.value(), hash.value());
    }
}