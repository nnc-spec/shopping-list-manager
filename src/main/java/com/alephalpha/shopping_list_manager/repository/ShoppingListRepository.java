package com.alephalpha.shopping_list_manager.repository;

import com.alephalpha.shopping_list_manager.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {
}
