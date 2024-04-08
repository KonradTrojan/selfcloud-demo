package pl.trojan.selfcloud.demo.service.mapper;

import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.dto.UserDto;
import pl.trojan.selfcloud.demo.model.dto.UserWithRolesDto;

public class UserMapper {

    public static UserDto mapToUserDto(final User user){
        return UserDto.builder()
                .username(user.getUsername())
                .mail(user.getMail())
                .build();
    }

    public static UserWithRolesDto mapToUserWithRolesDto(final User user){
        return UserWithRolesDto.builder()
                .username(user.getUsername())
                .mail(user.getMail())
                .roles(user.getRoles())
                .build();
    }
}
