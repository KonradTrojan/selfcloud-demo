package pl.trojan.selfcloud.demo.configuration;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.trojan.selfcloud.demo.model.*;
import pl.trojan.selfcloud.demo.model.privileges.AuthorityName;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;
import pl.trojan.selfcloud.demo.repository.AuthorityRepository;
import pl.trojan.selfcloud.demo.repository.OrderRepository;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

        List<AuthorityName> authoritiesNames = List.of(AuthorityName.values());

        if (alreadySetup)
            return;
        List<Authority> userAuthorities = List.of(
                createAuthorityIfNotFound(AuthorityName.READ_ORDER)
        );
        List<Authority> modAuthorities = List.of(
                createAuthorityIfNotFound(AuthorityName.READ_ORDER),
                createAuthorityIfNotFound(AuthorityName.CREATE_ORDER)
        );
        List<Authority> adminAuthorities = authoritiesNames.stream()
                .map(this::createAuthorityIfNotFound)
                .toList();

        createRoleIfNotFound(RoleName.USER, userAuthorities, "Default role for new users.");
        createRoleIfNotFound(RoleName.MODERATOR, modAuthorities, "Moderator Role");
        createRoleIfNotFound(RoleName.ADMIN, adminAuthorities, "Admin role");

        Optional<Role> userRole = roleRepository.findByName(RoleName.USER);
        Optional<Role> modRole = roleRepository.findByName(RoleName.MODERATOR);
        Optional<Role> adminRole = roleRepository.findByName(RoleName.ADMIN);

        User user = new User(null, "user", passwordEncoder.encode("user"), "user@gmail.com", true, new HashSet<>(), List.of(userRole.get()));
        User mod = new User(null, "mod", passwordEncoder.encode("mod"), "mod@gmail.com", true, new HashSet<>(), List.of(modRole.get()       ));
        User admin = new User(null, "admin", passwordEncoder.encode("admin"), "admin@gmail.com", true, new HashSet<>(), List.of(adminRole.get()));

        userRepository.save(user);
        userRepository.save(mod);
        userRepository.save(admin);

        orderRepository.save(new Order(null, "test", "test", 100, false, admin));

        alreadySetup = true;
    }

    @Transactional
    private Authority createAuthorityIfNotFound(AuthorityName name) {

        Optional<Authority> authority = authorityRepository.findByName(name);
        if (authority.isEmpty()) {
            Authority newAuthority = new Authority(name);
            return authorityRepository.save(newAuthority);
        }
        return authority.get();
    }

    @Transactional
    private Role createRoleIfNotFound(
            RoleName name, List<Authority> authorities,
            String description) {

        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            Role newRole = new Role(name, authorities, description);
            return roleRepository.save(newRole);
        }
        return role.get();
    }
}