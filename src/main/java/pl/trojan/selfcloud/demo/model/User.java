package pl.trojan.selfcloud.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String mail;

    private boolean enabled;

    @OneToMany(targetEntity = Order.class)
    private Set<Order> orders;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

}
