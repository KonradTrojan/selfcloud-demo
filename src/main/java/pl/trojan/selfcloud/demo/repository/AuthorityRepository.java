package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.repository.CrudRepository;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.User;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByName(String name);
}
