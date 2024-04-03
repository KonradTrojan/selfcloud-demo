package pl.trojan.selfcloud.demo.model.privileges;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@AllArgsConstructor
public enum AuthorityName implements GrantedAuthority {

    CREATE_ORDER("CREATE_ORDER"),
    UPDATE_ORDER("UPDATE_ORDER"),
    DELETE_ORDER("DELETE_ORDER"),
    READ_ORDER("READ_ORDER"),
    CREATE_USER("CREATE_USER"),
    DELETE_USER("DELETE_USER"),
    GRAND_AUTHORITY("GRAND_AUTHORITY"),
    REVOKE_AUTHORITY("REVOKE_AUTHORITY");

    final String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public static void main(String[] a){
        AuthorityName name1 = AuthorityName.valueOf("READ_ORDER");
        System.out.println(name1);
    }
}
