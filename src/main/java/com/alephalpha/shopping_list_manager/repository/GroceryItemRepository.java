package com.alephalpha.shopping_list_manager.repository;
import com.alephalpha.shopping_list_manager.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    List<GroceryItem> findByNameStartingWithIgnoreCase(String prefix);
}
