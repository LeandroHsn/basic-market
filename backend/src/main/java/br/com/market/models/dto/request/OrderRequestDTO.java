package br.com.market.models.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class OrderRequestDTO {

    @NotEmpty
    private List<OrderItemRequestDTO> items;

    public void addItem(OrderItemRequestDTO orderItemRequestDTO) {
        if (Objects.isNull(this.items)) {
            this.items = new ArrayList<>();
        }
        this.items.add(orderItemRequestDTO);
    }
}

