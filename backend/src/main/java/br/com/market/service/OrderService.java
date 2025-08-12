package br.com.market.service;

import br.com.market.models.db.Order;
import br.com.market.models.dto.request.OrderRequestDTO;

public interface OrderService {

    Order createOrder(OrderRequestDTO request);
}
