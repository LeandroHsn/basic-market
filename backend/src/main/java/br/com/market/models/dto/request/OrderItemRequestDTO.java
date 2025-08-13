package br.com.market.models.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;

}
