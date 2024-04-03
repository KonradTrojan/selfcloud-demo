package pl.trojan.selfcloud.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.trojan.selfcloud.demo.model.privileges.AuthorityName;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Getter
@Table(name = "authorities")
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    private String description;

    public Authority(String name, String description){
        this.name = AuthorityName.valueOf(name);
    }

    public Authority(AuthorityName name, String description){
        this.name = name;
    }

    public Authority(String name){
        this.name = AuthorityName.valueOf(name);
    }

    public Authority(AuthorityName name){
        this.name = name;
    }
}
