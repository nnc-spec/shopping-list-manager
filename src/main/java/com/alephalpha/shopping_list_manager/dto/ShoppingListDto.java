package com.alephalpha.shopping_list_manager.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {
    private UUID id;
    private String name;
    private UserDto user;
    private List<ShoppingListItemDto> items;
}
