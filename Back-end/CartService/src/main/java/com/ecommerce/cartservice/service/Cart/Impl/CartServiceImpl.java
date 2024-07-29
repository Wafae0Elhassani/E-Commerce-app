package com.ecommerce.cartservice.service.Cart.Impl;

import com.ecommerce.cartservice.Mapper.Impl.CartItemMapper;
import com.ecommerce.cartservice.dto.CartItemDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.repository.CartItemRepository;
import com.ecommerce.cartservice.repository.CartRepository;
import com.ecommerce.cartservice.service.Cart.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemMapper cartItemMapper;

    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemMapper cartItemMapper, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart createCart(Long userId) {
        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartItem addItemToCart(Long userId, CartItemDto cartItemDto) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->createCart(userId));
        CartItem cartItem = cartItemMapper.toEntity(cartItemDto);
        cartItem.setCart(cart);
        assert cart != null;
        cart.getItems().add(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        CartItem cartItem = cartItemRepository.findById(itemId).orElse(null);
        assert cart != null;
        cart.getItems().remove(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        assert cartItem != null;
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        assert cart != null;
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

    }
}
