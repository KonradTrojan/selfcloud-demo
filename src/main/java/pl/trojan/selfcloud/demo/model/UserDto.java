package pl.trojan.selfcloud.demo.model;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String mail;
    private boolean enabled;

}
