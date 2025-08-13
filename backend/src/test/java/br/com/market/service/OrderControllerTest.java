package br.com.market.service;

import br.com.market.models.db.Product;
import br.com.market.models.dto.request.OrderItemRequestDTO;
import br.com.market.models.dto.request.OrderRequestDTO;
import br.com.market.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void shouldFailWhenFinalizingOrderWithInsufficientStock() throws Exception {
        OrderRequestDTO request = new OrderRequestDTO();
        request.addItem(new OrderItemRequestDTO(1L, 3)); // Garrafa Térmica 1L 3 unidades
        request.addItem(new OrderItemRequestDTO(2L, 2)); // Café Torrado 500g 2 unidades

        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product(1L, "Garrafa Térmica 1L", BigDecimal.valueOf(50), 2, true, 0)));
        when(productRepository.findById(2L)).thenReturn(Optional.of(new Product(2L, "Café Torrado 500g", BigDecimal.valueOf(20), 10, true, 0)));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict()) // 409
                .andExpect(jsonPath("$.message").value("Estoque insuficiente para produtos: [Garrafa Térmica 1L]"));
    }
}