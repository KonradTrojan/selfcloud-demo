package pl.trojan.selfcloud.demo.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.repository.OrderRepository;
import pl.trojan.selfcloud.demo.repository.RoleRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.*;

@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class DevConfig {

    @Bean
    public CommandLineRunner dataLoader(OrderRepository orderRepository,
                                        UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        PasswordEncoder encoder) {
        return new CommandLineRunner() {
//            Set<Role> adminRole = new HashSet<>(Arrays.asList(new Role(1, "ADMIN")));
//            Set<Role> userRole = new HashSet<>(Arrays.asList(new Role(2, "USER")));
//            ;
//            Set<Order> orders = new HashSet<>();
//            User admin = new User(1, "admin", encoder.encode("admin"), adminRole, orders);
//            User user = new User(2, "user", encoder.encode("user"), userRole, orders);

            @Override
            public void run(String... args) throws Exception {
//                roleRepository.save(new Role(1L, "ADMIN"));
//                roleRepository.save(new Role(2L, "USER"));
//                userRepository.save(admin);
//                userRepository.save(user);
//                orderRepository.save(new Order(1, "test", "test", 100, false));
            }
        };


    }

}
