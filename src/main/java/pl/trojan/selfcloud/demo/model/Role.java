package pl.trojan.selfcloud.demo.model;

import jakarta.persistence.*;
import lombok.*;
import pl.trojan.selfcloud.demo.model.privileges.RoleName;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Data
@Table(name = "ROLES")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    private String description;

    @ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public Role(String name, List<Authority> authorities, String description){
        this.name = RoleName.valueOf(name);
        this.authorities = authorities;
        this.description = description;
    }

    public Role(RoleName name, List<Authority> authorities, String description){
        this.name = name;
        this.authorities = authorities;
        this.description = description;
    }


}