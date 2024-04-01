package pl.trojan.selfcloud.demo.configuration;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.trojan.selfcloud.demo.model.*;
import pl.trojan.selfcloud.demo.repository.AuthorityRepository;
import pl.trojan.selfcloud.demo.repository.OrderRepository;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Profile("!prod")
@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<Privilege> privileges = List.of(Privilege.values());

        if (alreadySetup)
            return;
        List<Authority> userAuthorities = List.of(
                createAuthorityIfNotFound(Privilege.READ_ORDER.toString())
        );
        List<Authority> modAuthorities = List.of(
                createAuthorityIfNotFound(Privilege.READ_ORDER.toString()),
                createAuthorityIfNotFound(Privilege.CREATE_ORDER.toString())
        );
        List<Authority> adminAuthorities = privileges.stream()
                .map(privilege -> createAuthorityIfNotFound(privilege.toString()))
                .toList();

        createRoleIfNotFound("ROLE_USER", userAuthorities);
        createRoleIfNotFound("ROLE_MODERATOR", modAuthorities);
        createRoleIfNotFound("ROLE_ADMIN", adminAuthorities);

        Role userRole = roleRepository.findByName("ROLE_USER");
        Role modRole = roleRepository.findByName("ROLE_MODERATOR");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        User user = new User(null, "user", passwordEncoder.encode("user"), "user@gmail.com", true, new HashSet<>(), List.of(userRole));
        User test = new User(null, "test", passwordEncoder.encode("test"), "user@gmail.com", true, new HashSet<>(), List.of(userRole));
        User mod = new User(null, "mod", passwordEncoder.encode("mod"), "mod@gmail.com", true, new HashSet<>(), List.of(modRole));
        User admin = new User(null, "admin", passwordEncoder.encode("admin"), "admin@gmail.com", true, new HashSet<>(), List.of(adminRole));

        userRepository.save(user);
        userRepository.save(test);
        userRepository.save(mod);
        userRepository.save(admin);

        orderRepository.save(new Order(null, "test", "test", 100, false));

        alreadySetup = true;
    }

    @Transactional
    private Authority createAuthorityIfNotFound(String name) {

        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private Role createRoleIfNotFound(
            String name, List<Authority> authorities) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name, authorities);
            roleRepository.save(role);
        }
        return role;
    }
}