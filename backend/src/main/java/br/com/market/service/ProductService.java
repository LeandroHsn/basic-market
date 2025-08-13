package br.com.market.service;

import br.com.market.models.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductResponseDTO> searchProducts(String name, Pageable pageable);
}
