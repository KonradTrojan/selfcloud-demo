package pl.trojan.selfcloud.demo.model;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public enum Privilege implements Serializable {

    CREATE_ORDER("CREATE_ORDER"),
    UPDATE_ORDER("UPDATE_ORDER"),
    DELETE_ORDER("DELETE_ORDER"),
    READ_ORDER("READ_ORDER"),
    CREATE_USER("CREATE_USER"),
    DELETE_USER("DELETE_USER"),
    GRAND_AUTHORITY("GRAND_AUTHORITY"),
    REVOKE_AUTHORITY("REVOKE_AUTHORITY");

    final String name;
}
