package pl.trojan.selfcloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.trojan.selfcloud.demo.exception.OrderNotFound;
import pl.trojan.selfcloud.demo.exception.UserNotFound;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.UserDto;
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
    public User getUser(@PathVariable final long id){
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody final UserDto userDto){
        return userService.registerUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final long id){
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/mod")
    public User grandModeratorPrivilege(@PathVariable final long id){
        return userService.grandModeratorPrivilege(id);
    }
    @DeleteMapping("/{id}/mod")
    public void revokeModeratorPrivilege(@PathVariable final long id){
        userService.revokeModeratorPrivilege(id);
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFound(
            OrderNotFound exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
