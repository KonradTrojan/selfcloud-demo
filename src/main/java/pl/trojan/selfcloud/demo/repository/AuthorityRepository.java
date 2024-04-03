package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.model.privileges.AuthorityName;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Optional<Authority> findByName(AuthorityName name);
}
