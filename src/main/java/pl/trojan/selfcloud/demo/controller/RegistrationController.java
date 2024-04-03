package pl.trojan.selfcloud.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.trojan.selfcloud.demo.exception.http.conflict.CustomConflictException;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.dto.UserDto;
import pl.trojan.selfcloud.demo.service.UserService;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    @Autowired
    private final UserService userService;

    @PostMapping
    public User processRegistration(final UserDto userDto){
        return userService.registerUser(userDto);
    }

    @ExceptionHandler(CustomConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleHttpConflict(
            CustomConflictException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
