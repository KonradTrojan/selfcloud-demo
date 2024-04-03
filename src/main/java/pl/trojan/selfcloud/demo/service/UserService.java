package pl.trojan.selfcloud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.event.OnRegistrationCompleteEvent;
import pl.trojan.selfcloud.demo.exception.http.conflict.UserAlreadyExistException;
import pl.trojan.selfcloud.demo.exception.http.notfound.UserNotFoundException;
import pl.trojan.selfcloud.demo.exception.http.conflict.UsernameIsTakenException;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.dto.UserDto;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    public User getUser(final long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user.get();
    }

    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    public User registerUser(final UserDto userDto){
        if (emailExists(userDto.getMail())) {
            throw new UserAlreadyExistException("An account associated with this email exists.");
        }
        if (usernameExists(userDto.getUsername())){
            throw new UsernameIsTakenException("The username is taken.");
        }
        Optional<Role> role = roleRepository.findByName(RoleName.USER);
        User user = new User(null,
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getMail(),
                userDto.isEnabled(),
                new HashSet<>(),
                new HashSet<>(List.of(role.get())));

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

        return userRepository.save(user);
    }

    public void deleteUser(final long id){
        userRepository.deleteById(id);
    }

    public User grandModeratorPrivilege(final long id){
        return editRole(id, RoleName.MODERATOR);
    }
    
    public void revokeModeratorPrivilege(final long id){
        editRole(id, RoleName.USER);
    }

    private User editRole(final long id, final RoleName roleName) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional<Role> role = roleRepository.findByName(roleName);
        user.setRoles(new HashSet<>(List.of(role.get())));
        userRepository.save(user);
        return user;
    }

    private boolean emailExists(final String email){
        return userRepository.findByMail(email).isPresent();
    }

    private boolean usernameExists(final String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
