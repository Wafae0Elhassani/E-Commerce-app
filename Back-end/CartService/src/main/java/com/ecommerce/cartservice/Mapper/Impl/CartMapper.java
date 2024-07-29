package com.ecommerce.cartservice.Mapper.Impl;

import com.ecommerce.cartservice.Mapper.EntityMapper;
import com.ecommerce.cartservice.dto.CartDto;
import com.ecommerce.cartservice.dto.CartItemDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapper implements EntityMapper<CartDto, Cart> {

    private final CartItemMapper cartItemMapper;

    public CartMapper(CartItemMapper cartItemMapper) {
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public Cart toEntity(CartDto dto) {
        Cart cart = new Cart();
        cart.setId(dto.getId());
        cart.setUserId(dto.getUserId());
        List<CartItem> cartItemList = new ArrayList<>();
        if (dto.getItems() != null) {
            for (CartItemDto cartItemDto : dto.getItems()) {
                CartItem cartItem = cartItemMapper.toEntity(cartItemDto);
                cartItem.setCart(cart);
                cartItemList.add(cartItem);
            }
        }
        cart.setItems(cartItemList);
        return cart;
    }

    @Override
    public CartDto toDto(Cart entity) {
        CartDto cartDto = new CartDto();
        cartDto.setId(entity.getId());
        cartDto.setUserId(entity.getUserId());
        cartDto.setCreatedAt(entity.getCreatedAt());
        cartDto.setUpdatedAt(entity.getUpdatedAt());
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        if(entity.getItems() != null){
            for (CartItem cartItem: entity.getItems()){
                CartItemDto cartItemDto = cartItemMapper.toDto(cartItem);
                cartItemDto.setCartId(entity.getId());
                cartItemDtoList.add(cartItemDto);
            }
        }
        cartDto.setItems(cartItemDtoList);
        return cartDto;
    }

    @Override
    public List<Cart> toEntity(List<CartDto> dtoList) {
        List<Cart> cartList = new ArrayList<>();
        for(CartDto cartDto: dtoList){
            cartList.add(toEntity(cartDto));
        }
        return cartList;
    }

    @Override
    public List<CartDto> toDto(List<Cart> entityList) {
        List<CartDto> cartDtoList = new ArrayList<>();
        for (Cart cart: entityList){
            cartDtoList.add(toDto(cart));
        }
        return cartDtoList;
    }
}
