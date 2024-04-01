package pl.trojan.selfcloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.trojan.selfcloud.demo.exception.OrderNotFound;
import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.model.Privilege;
import pl.trojan.selfcloud.demo.service.OrderService;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService){
        this.orderService = orderService;
    }

    @Secured("READ_ORDER")
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable final long id) {
        return orderService.getOrder(id);
    }

    @Secured("READ_ORDER")
    @GetMapping
    public List<Order> getOrders(){
        return orderService.getAllOrders();
    }

    @Secured("CREATE_ORDER")
    @PostMapping
    public Order createOrder(@RequestBody final Order order){
        return orderService.createOrder(order);
    }

    @Secured("UPDATE_ORDER")
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable final long id, @RequestBody final Order order){
        return orderService.createOrder(order);
    }

//    @Secured("DELETE_ORDER")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable final long id){
        orderService.deleteOrder(id);
    }

    @ExceptionHandler(OrderNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleOrderNotFound(
            OrderNotFound exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}
