package com.alephalpha.shopping_list_manager.mapper;

import com.alephalpha.shopping_list_manager.dto.GroceryItemDto;
import com.alephalpha.shopping_list_manager.entity.GroceryItem;
import org.springframework.stereotype.Component;

@Component
public class GroceryItemMapper {

    public static GroceryItemDto toDto(GroceryItem entity) {
        return GroceryItemDto.builder()
                .name(entity.getName())
                .build();
    }

    public static GroceryItem toEntity(GroceryItemDto dto) {
        return GroceryItem.builder()
                .name(dto.getName())
                .build();
    }
}
