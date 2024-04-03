package pl.trojan.selfcloud.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.event.OnRegistrationCompleteEvent;
import pl.trojan.selfcloud.demo.model.dto.Oauth2UserInfoDto;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;
import pl.trojan.selfcloud.demo.service.mapper.GrantedAuthoritiesMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Oauth2UserInfoDto userInfoDto = Oauth2UserInfoDto.builder()
                .name(oAuth2User.getAttributes().get("name").toString())
                .id(oAuth2User.getAttributes().get("sub").toString())
                .email(oAuth2User.getAttributes().get("email").toString())
                .build();

        Optional<User> userOptional = userRepository.findByMail(userInfoDto.getEmail());

        User user = userOptional
                .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
                .orElseGet(() -> registerNewUser(oAuth2UserRequest, userInfoDto));

        return new DefaultOAuth2User(GrantedAuthoritiesMapper.mapRolesToGrantedAuthorities(user.getRoles()), oAuth2User.getAttributes(), "email");
    }

    //TODO
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfoDto userInfoDto) {
        Optional<Role> role = roleRepository.findByName(RoleName.USER);

        User user = User.builder()
                .username(userInfoDto.getName())
                .mail(userInfoDto.getEmail())
                .enabled(true)
                .roles(List.of(role.get()))
                .build();

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, Oauth2UserInfoDto userInfoDto) {
        existingUser.setUsername(userInfoDto.getName());
        return userRepository.save(existingUser);
    }
}