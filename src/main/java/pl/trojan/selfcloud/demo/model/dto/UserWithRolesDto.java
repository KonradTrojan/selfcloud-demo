package pl.trojan.selfcloud.demo.model.dto;

import lombok.*;
import pl.trojan.selfcloud.demo.model.Role;

import java.util.Collection;


@Data
@Builder
public class UserWithRolesDto{

    private final String username;
    private final String mail;
    private final boolean enabled;
    private final Collection<Role> roles;

}
