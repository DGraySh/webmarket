package com.dgraysh.market.dto;

import com.dgraysh.market.entities.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private String categoryTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto(OrderItem o) {
        this.productId = o.getProduct().getId();
        this.productTitle = o.getProduct().getTitle();
        this.categoryTitle = o.getProduct().getCategory().getTitle();
        this.quantity = o.getQuantity();
        this.pricePerProduct = o.getPricePerProduct();
        this.price = o.getPrice();
    }
}
