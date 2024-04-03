package pl.trojan.selfcloud.demo.model.privileges;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@AllArgsConstructor
public enum RoleName implements GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR"),
    USER("ROLE_USER");

    final String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
