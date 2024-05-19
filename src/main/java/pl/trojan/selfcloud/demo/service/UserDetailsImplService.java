package pl.trojan.selfcloud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.repository.UserRepository;
import pl.trojan.selfcloud.demo.service.mapper.GrantedAuthoritiesMapper;

import java.util.Optional;

@Service
public class UserDetailsImplService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsImplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByMail(mail);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with mail" + mail + " not found");
        }
      return optionalUser.get();
    }

}
