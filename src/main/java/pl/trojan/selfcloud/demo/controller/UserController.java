package pl.trojan.selfcloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.trojan.selfcloud.demo.exception.http.notfound.CustomNotFoundException;
import pl.trojan.selfcloud.demo.exception.http.notfound.OrderNotFoundException;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.dto.RegistrationUserDto;
import pl.trojan.selfcloud.demo.model.dto.UserDto;
import pl.trojan.selfcloud.demo.model.dto.UserWithRolesDto;
import pl.trojan.selfcloud.demo.service.UserService;

import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable final long id){
        return userService.getUser(id);
    }

    @GetMapping("/{id}/roles")
    public UserWithRolesDto getUserWithRoles(@PathVariable final long id){
        return userService.getUserWithRoles(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<UserWithRolesDto> getAllUsersWithRoles(){
        return userService.getAllUsersWithRoles();
    }

    @PostMapping
    public UserDto createUser(@RequestBody final RegistrationUserDto userDto){
        return userService.registerUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final long id){
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/mod")
    public UserWithRolesDto grandModeratorPrivilege(@PathVariable final long id){
        return userService.grandModeratorPrivilege(id);
    }

    @DeleteMapping("/{id}/mod")
    public void revokeModeratorPrivilege(@PathVariable final long id){
        userService.revokeModeratorPrivilege(id);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFound(
            OrderNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsers() {
        List <UserDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
}
