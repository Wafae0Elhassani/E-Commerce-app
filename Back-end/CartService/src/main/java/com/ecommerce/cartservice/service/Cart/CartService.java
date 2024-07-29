package com.ecommerce.cartservice.service.Cart;

import com.ecommerce.cartservice.dto.CartItemDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;

import java.util.Optional;

public interface CartService {
    Cart createCart(Long userId);
    Optional<Cart> getCartById(Long cartId);
    Optional<Cart> getCartByUserId(Long userId);
    CartItem addItemToCart(Long userId, CartItemDto cartItemDto);
    void removeItemFromCart(Long cartId, Long itemId);
    void clearCart(Long cartId);

}
