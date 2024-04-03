package pl.trojan.selfcloud.demo.mapper;

import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.model.dto.OrderDto;

public class OrderMapper {

    public static Order mapToOrder(final OrderDto orderDto){
        return Order.builder()
                .name(orderDto.getName())
                .description(orderDto.getDescription())
                .price(orderDto.getPrice())
                .sell(orderDto.isSell())
                .build();
    }
    public static Order mapToOrder(final long id,final OrderDto orderDto){
        return Order.builder()
                .id(id)
                .name(orderDto.getName())
                .description(orderDto.getDescription())
                .price(orderDto.getPrice())
                .sell(orderDto.isSell())
                .build();
    }
}
