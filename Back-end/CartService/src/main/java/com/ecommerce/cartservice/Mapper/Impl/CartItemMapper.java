package com.ecommerce.cartservice.Mapper.Impl;

import com.ecommerce.cartservice.Mapper.EntityMapper;
import com.ecommerce.cartservice.dto.CartItemDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.repository.CartRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemMapper implements EntityMapper<CartItemDto, CartItem> {

    private final CartRepository cartRepository;
    public CartItemMapper(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem toEntity(CartItemDto dto) {
        CartItem cartItem = new CartItem();
        cartItem.setId(dto.getId());
        cartItem.setProductId(dto.getProductId());
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setPrice(dto.getPrice());
        if(dto.getCartId() != null){
            Cart cart = cartRepository.findById(dto.getCartId()).orElse(null);
            cartItem.setCart(cart);
        }
        return cartItem;
    }

    @Override
    public CartItemDto toDto(CartItem entity) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(entity.getId());
        cartItemDto.setProductId(entity.getProductId());
        cartItemDto.setQuantity(entity.getQuantity());
        cartItemDto.setPrice(entity.getPrice());
        cartItemDto.setCreatedAt(entity.getCreatedAt());
        cartItemDto.setUpdatedAt(entity.getUpdatedAt());
        if(entity.getCart() != null){
            cartItemDto.setCartId(entity.getCart().getId());
        }
        return cartItemDto;
    }

    @Override
    public List<CartItem> toEntity(List<CartItemDto> dtoList) {
        List<CartItem> cartItemList = new ArrayList<>();
        for(CartItemDto cartItemDto: dtoList){
            cartItemList.add(toEntity(cartItemDto));
        }
        return cartItemList;
    }

    @Override
    public List<CartItemDto> toDto(List<CartItem> entityList) {
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for (CartItem cartItem: entityList){
            cartItemDtoList.add(toDto(cartItem));
        }
        return cartItemDtoList;
    }
}
