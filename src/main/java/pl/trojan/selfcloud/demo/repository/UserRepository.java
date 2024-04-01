package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.repository.CrudRepository;
import pl.trojan.selfcloud.demo.model.User;

public interface  UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByMail(String username);
}
