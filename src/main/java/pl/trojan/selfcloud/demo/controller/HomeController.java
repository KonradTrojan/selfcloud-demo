package pl.trojan.selfcloud.demo.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {


    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }


    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello Admin!";
    }


    @GetMapping("/moderator")
    public String helloModerator(){
        return "Hello Moderator!";
    }


    @GetMapping("/user")
    public String helloUser(){
        return "Hello User!";
    }
}
