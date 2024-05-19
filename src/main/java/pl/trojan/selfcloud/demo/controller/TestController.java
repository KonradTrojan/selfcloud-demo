package pl.trojan.selfcloud.demo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.dto.UserDto;
import pl.trojan.selfcloud.demo.service.UserService;

@RequestMapping("/users")
@RestController
public class TestController {
  private final UserService userService;

  public TestController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<User> authenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    User currentUser = (User) authentication.getPrincipal();

    return ResponseEntity.ok(currentUser);
  }

  @GetMapping("/o")
  public String oooo() {
    return "hahahahah";
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserDto>> allUsers() {
    List <UserDto> users = userService.getAllUsers();

    return ResponseEntity.ok(users);
  }
}
