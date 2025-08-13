package br.com.market.service.impl;

import br.com.market.models.dto.response.ProductResponseDTO;
import br.com.market.repository.ProductRepository;
import br.com.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductResponseDTO> searchProducts (String search, Pageable pageable) {
        return new ProductResponseDTO().fromPage(productRepository.findByNameContainingIgnoreCase(search, pageable));
    }

}
