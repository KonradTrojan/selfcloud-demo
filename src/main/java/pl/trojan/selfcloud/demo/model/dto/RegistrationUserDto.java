package pl.trojan.selfcloud.demo.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationUserDto {

    private String username;
    private String mail;
    private String password;
    private boolean enabled;

}