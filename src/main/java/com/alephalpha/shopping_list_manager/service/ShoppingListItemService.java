package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.entity.ShoppingListItem;
import com.alephalpha.shopping_list_manager.repository.ShoppingListItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShoppingListItemService {

    private final ShoppingListItemRepository shoppingListItemRepository;

    public ShoppingListItemService(ShoppingListItemRepository shoppingListItemRepository) {
        this.shoppingListItemRepository = shoppingListItemRepository;
    }

    @Transactional
    public void markAsBought(Long listItemId) {
        ShoppingListItem item = shoppingListItemRepository.findById(listItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List item not found"));
        item.setStatus(ShoppingListItem.Status.BOUGHT);
        shoppingListItemRepository.save(item);
    }

    @Transactional
    public void remove(Long listItemId) {
        if (!shoppingListItemRepository.existsById(listItemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List item not found");
        }
        shoppingListItemRepository.deleteById(listItemId);
    }
}
