package br.com.market.models.dto.response;

import br.com.market.models.db.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private int stock;

    private boolean active = true;

    private int version;

    public Page<ProductResponseDTO> fromPage(Page<Product> page) {
        return page.map(product -> new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.isActive(),
                product.getVersion()
        ));
    }
}
