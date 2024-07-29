package com.ecommerce.cartservice.dto;

import com.ecommerce.cartservice.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
