package br.com.market.service.impl;

import br.com.market.models.db.Order;
import br.com.market.models.db.OrderItem;
import br.com.market.models.db.Product;
import br.com.market.models.dto.request.OrderItemRequestDTO;
import br.com.market.models.dto.request.OrderRequestDTO;
import br.com.market.repository.OrderRepository;
import br.com.market.repository.ProductRepository;
import br.com.market.service.OrderService;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(OrderRequestDTO request) {
        List<String> outOfStock = new ArrayList<>();
        Order order = new Order();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

            if (product.getStock() < itemReq.getQuantity()) {
                outOfStock.add(product.getName());
                continue;
            }

            product.setStock(product.getStock() - itemReq.getQuantity());
            total = total.add(addOrderItem(order, product, itemReq));
        }

        if (!outOfStock.isEmpty()) {
            throw new RuntimeException("Estoque insuficiente para produtos: " + outOfStock);
        }

        order.setTotal(total);
        return saveOrder(order);
    }

    private BigDecimal addOrderItem(Order order, Product product, OrderItemRequestDTO itemReq) {
        OrderItem orderItem = new OrderItem(product, itemReq.getQuantity(), product.getPrice());
        order.addItem(orderItem);
        return orderItem.getLineTotal();
    }

    @Transactional
    private Order saveOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Conflito de concorrência. Tente novamente.");
        }
    }
}
