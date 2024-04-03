package pl.trojan.selfcloud.demo.model.dto;

import lombok.Data;

@Data
public class OrderDto {

    private String name;
    private String description;
    private double price;
    private boolean sell;
}
