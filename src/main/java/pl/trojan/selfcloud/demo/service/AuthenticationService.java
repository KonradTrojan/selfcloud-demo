package pl.trojan.selfcloud.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.dto.LoginUserDto;
import pl.trojan.selfcloud.demo.model.dto.RegistrationUserDto;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository, RoleRepository roleRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder
  ) {
    this.roleRepository = roleRepository;
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User signup(RegistrationUserDto input) {
    Optional<Role> userRole = roleRepository.findByName(RoleName.USER);

    User user = User.builder()
        .username(input.getUsername())
        .password(passwordEncoder.encode(input.getPassword()))
        .mail(input.getMail())
        .enabled(true)
        .roles(List.of(userRole.get()))
        .build();

    return userRepository.save(user);
  }

  public User authenticate(LoginUserDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getMail(),
            input.getPassword()
        )
    );

    return userRepository.findByMail(input.getMail())
        .orElseThrow();
  }
}