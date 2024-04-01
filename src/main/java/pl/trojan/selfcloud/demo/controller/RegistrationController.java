package pl.trojan.selfcloud.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.UserDto;
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


}
