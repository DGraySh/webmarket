package com.dgraysh.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewProductDto {
    private String title;
    private int price;
    private Long categoryId;
}
