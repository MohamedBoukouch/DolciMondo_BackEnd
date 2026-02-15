package com.example.DolciMondo_backend.dtos;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

    private String clientFullName;
    private String clientPhone;
    private String clientAddress;

    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}