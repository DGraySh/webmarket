package com.dgraysh.market.controllers;

import com.dgraysh.market.dto.OrderDto;
import com.dgraysh.market.entities.Order;
import com.dgraysh.market.entities.User;
import com.dgraysh.market.exceptions.ResourceNotFoundException;
import com.dgraysh.market.services.OrderService;
import com.dgraysh.market.services.UserService;
import com.dgraysh.market.utils.Cart;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final Cart cart;

    @GetMapping
    public List<OrderDto> getAllOrders(Principal principal) {
        return orderService.findAllUsersOrderDtosByUsername(principal.getName() );
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(Principal principal, @RequestParam String address) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to create new order for user " + principal.getName() + ", user doesn't exist"));
        Order order = new Order(user, cart, address);
        orderService.save(order);
        cart.clear();
    }

//    @PostMapping("/confirm")
//    @ResponseBody
//    public String confirmOrder(Principal principal,
//                              @RequestParam(name = "address") String address,
//                              @RequestParam(name = "receiver_name") String receiverName,
//                              @RequestParam(name = "phone_number") String phone
//                              ) {
//        User user = userService.findByUsername(principal.getName());
//        Order order = new Order(user, cart, address);
//        order = orderService.save(order);
//        return "Ваш заказ #" + order.getId();
//    }
}
