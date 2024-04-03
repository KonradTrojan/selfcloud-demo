package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.repository.CrudRepository;
import pl.trojan.selfcloud.demo.model.User;

import java.util.Optional;

public interface  UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByMail(String username);
}
