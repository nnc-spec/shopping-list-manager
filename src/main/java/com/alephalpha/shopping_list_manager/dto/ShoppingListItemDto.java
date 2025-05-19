package com.alephalpha.shopping_list_manager.dto;

import com.alephalpha.shopping_list_manager.entity.ShoppingListItem;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItemDto {
    private Long id;
    private GroceryItemDto groceryItem;
    private ShoppingListItem.Status status;
}
