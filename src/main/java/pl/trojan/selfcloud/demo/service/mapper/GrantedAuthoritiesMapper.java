package pl.trojan.selfcloud.demo.service.mapper;


import org.springframework.security.core.GrantedAuthority;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.Role;

import java.util.ArrayList;
import java.util.Collection;

public final class GrantedAuthoritiesMapper {
    public static Collection<? extends GrantedAuthority> mapRolesToGrantedAuthorities(Collection<Role> roles) {

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Role role : roles) {
            grantedAuthorities.add(role.getName());
            for (Authority authority : role.getAuthorities()){
                grantedAuthorities.add(authority.getName());
            }
        }

        return grantedAuthorities;
    }
}
