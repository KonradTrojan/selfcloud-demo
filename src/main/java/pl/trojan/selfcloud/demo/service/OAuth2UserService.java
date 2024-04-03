package pl.trojan.selfcloud.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.exception.UserNotFound;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.Oauth2UserInfoDto;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

        return new DefaultOAuth2User(getRolesAndAuthorities(user.getRoles()), oAuth2User.getAttributes(), "email");
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfoDto userInfoDto) {
        Role role = roleRepository.findByName("ROLE_USER");
        User user = User.builder()
                .username(userInfoDto.getName())
                .mail(userInfoDto.getEmail())
                .enabled(true)
                .roles(List.of(role))
                .build();
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, Oauth2UserInfoDto userInfoDto) {
        existingUser.setUsername(userInfoDto.getName());
        return userRepository.save(existingUser);
    }

    private Collection<? extends GrantedAuthority> getRolesAndAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getAuthorities(roles));
    }

    private List<String> getAuthorities(Collection<Role> roles) {

        List<String> rolesNames = new ArrayList<>();
        List<Authority> authorities = new ArrayList<>();

        for (Role role : roles) {
            rolesNames.add(role.getName());
            authorities.addAll(role.getAuthorities());
        }
        for (Authority item : authorities) {
            rolesNames.add(item.getName());
        }
        return rolesNames;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }
}
