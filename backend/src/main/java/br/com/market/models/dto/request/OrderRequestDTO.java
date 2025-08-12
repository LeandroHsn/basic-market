package br.com.market.models.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    @NotEmpty
    private List<OrderItemRequestDTO> items;

}

