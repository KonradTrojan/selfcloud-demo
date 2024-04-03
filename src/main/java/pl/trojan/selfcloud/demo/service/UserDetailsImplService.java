package pl.trojan.selfcloud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsImplService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsImplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), user.isEnabled(), true, true, true, getRolesAndAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getRolesAndAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getAuthorities(roles));
    }

    private List<String> getAuthorities(Collection<Role> roles) {

        List<String> rolesNames = new ArrayList<>();
        List<Authority> authorities = new ArrayList<>();

        for (Role role : roles) {
            rolesNames.add(role.getName());
            authorities.addAll(role.getAuthorities());
        }
        for (Authority item : authorities) {
            rolesNames.add(item.getName());
        }
        return rolesNames;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }
}
