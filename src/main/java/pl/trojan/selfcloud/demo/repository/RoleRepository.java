package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.repository.CrudRepository;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.Role;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;

import java.util.Optional;

public interface  RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
