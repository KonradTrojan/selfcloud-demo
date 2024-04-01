package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.repository.CrudRepository;
import pl.trojan.selfcloud.demo.model.Authority;
import pl.trojan.selfcloud.demo.model.Role;

public interface  RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
