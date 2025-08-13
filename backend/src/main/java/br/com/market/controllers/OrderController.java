package br.com.market.controllers;

import br.com.market.models.dto.request.OrderRequestDTO;
import br.com.market.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }
}
