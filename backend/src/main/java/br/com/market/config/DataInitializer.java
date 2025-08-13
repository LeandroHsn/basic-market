package br.com.market.config;

import br.com.market.models.db.Product;
import br.com.market.repository.OrderItemRepository;
import br.com.market.repository.OrderRepository;
import br.com.market.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public DataInitializer(ProductRepository productRepository,
                           OrderRepository orderRepository,
                           OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void run(String... args) {
        orderItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();

        productRepository.saveAll(List.of(
                new Product(null, "Café Torrado 500g", BigDecimal.valueOf(18.90), 5, true, 0),
                new Product(null, "Filtro de Papel nº103", BigDecimal.valueOf(7.50), 10, true, 0),
                new Product(null, "Garrafa Térmica 1L", BigDecimal.valueOf(79.90), 2, true, 0),
                new Product(null, "Açúcar Mascavo 1kg", BigDecimal.valueOf(16.00), 0, true, 0),
                new Product(null, "Caneca Inox 300ml", BigDecimal.valueOf(29.00), 8, true, 0)
        ));
    }
}
