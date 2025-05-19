package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.entity.GroceryItem;
import com.alephalpha.shopping_list_manager.repository.GroceryItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GroceryItemService {

    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    public List<GroceryItem> getAll() {
        return groceryItemRepository.findAll();
    }
    public GroceryItem create(GroceryItem item) {
        return groceryItemRepository.save(item);
    }
}
