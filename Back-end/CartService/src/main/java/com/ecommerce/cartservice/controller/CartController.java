package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.Mapper.Impl.CartItemMapper;
import com.ecommerce.cartservice.Mapper.Impl.CartMapper;
import com.ecommerce.cartservice.dto.CartDto;
import com.ecommerce.cartservice.dto.CartItemDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.service.Cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    private final CartItemMapper cartItemMapper;

    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartItemMapper cartItemMapper, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartItemMapper = cartItemMapper;
        this.cartMapper = cartMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> createCart(@RequestParam Long userId){
        Cart cart = cartService.createCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @GetMapping("/cartById/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long cartId) {
        Optional<Cart> cart = cartService.getCartById(cartId);
        return cart.map(c -> ResponseEntity.ok(cartMapper.toDto(c)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/cardByUser/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        return cart.map(c -> ResponseEntity.ok(cartMapper.toDto(c)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping("/user/{userId}/addItem")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto) {
        CartItem cartItem = cartService.addItemToCart(userId, cartItemDto);
        return ResponseEntity.ok(cartItemMapper.toDto(cartItem));
    }

    @DeleteMapping("{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        cartService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
