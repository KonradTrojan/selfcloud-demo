package pl.trojan.selfcloud.demo.service.mapper;

import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.model.User;
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

    public static Order mapToOrder(final OrderDto orderDto, final User user){
        return Order.builder()
                .name(orderDto.getName())
                .description(orderDto.getDescription())
                .price(orderDto.getPrice())
                .sell(orderDto.isSell())
                .user(user)
                .build();
    }

    public static OrderDto mapToOrderDto(final Order order){
        return OrderDto.builder()
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .sell(order.isSell())
                .userDto(UserMapper.mapToUserDto(order.getUser()))
                .build();
    }

}
