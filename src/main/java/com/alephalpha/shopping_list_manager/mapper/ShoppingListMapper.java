package com.alephalpha.shopping_list_manager.mapper;

import com.alephalpha.shopping_list_manager.dto.ShoppingListDto;
import com.alephalpha.shopping_list_manager.dto.ShoppingListItemDto;
import com.alephalpha.shopping_list_manager.entity.ShoppingList;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ShoppingListMapper {

    private final UserMapper userMapper;
    private final GroceryItemMapper groceryItemMapper;

    public ShoppingListMapper(UserMapper userMapper, GroceryItemMapper groceryItemMapper) {
        this.userMapper = userMapper;
        this.groceryItemMapper = groceryItemMapper;
    }

    public ShoppingListDto toDto(ShoppingList entity) {
        return ShoppingListDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .user(userMapper.toDto(entity.getUser()))
                .items(entity.getItems().stream()
                        .map(item -> ShoppingListItemDto.builder()
                                .id(item.getId())
                                .status(item.getStatus())
                                .groceryItem(groceryItemMapper.toDto(item.getGroceryItem()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
