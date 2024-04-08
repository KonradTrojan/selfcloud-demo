package pl.trojan.selfcloud.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    private String name;
    private String description;
    private double price;
    private boolean sell;
    private UserDto userDto;
}
