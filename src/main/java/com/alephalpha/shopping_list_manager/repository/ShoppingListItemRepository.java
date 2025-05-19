package com.alephalpha.shopping_list_manager.repository;

import com.alephalpha.shopping_list_manager.entity.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
}

