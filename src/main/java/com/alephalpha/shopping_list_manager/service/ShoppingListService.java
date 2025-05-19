package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.dto.ShoppingListDto;
import com.alephalpha.shopping_list_manager.entity.*;
import com.alephalpha.shopping_list_manager.mapper.ShoppingListMapper;
import com.alephalpha.shopping_list_manager.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    private final GroceryItemRepository groceryItemRepository;
    private final ShoppingListItemRepository shoppingListItemRepository;
    private final ShoppingListMapper shoppingListMapper;

    public ShoppingListService(
            ShoppingListRepository shoppingListRepository,
            UserRepository userRepository,
            GroceryItemRepository groceryItemRepository,
            ShoppingListItemRepository shoppingListItemRepository,
            ShoppingListMapper shoppingListMapper) {

        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
        this.groceryItemRepository = groceryItemRepository;
        this.shoppingListItemRepository = shoppingListItemRepository;
        this.shoppingListMapper = shoppingListMapper;
    }

    public ShoppingListDto createList(String name, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> notFound("User"));

        ShoppingList list = ShoppingList.builder()
                .name(name)
                .user(user)
                .items(new ArrayList<>())
                .build();

        return shoppingListMapper.toDto(shoppingListRepository.save(list));
    }

    public ShoppingListDto getList(UUID listId) {
        ShoppingList list = shoppingListRepository.findById(listId)
                .orElseThrow(() -> notFound("Shopping list"));

        return shoppingListMapper.toDto(list);
    }

    public List<ShoppingListDto> getListsByUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw notFound("User");
        }

        return shoppingListRepository.findAll().stream()
                .filter(list -> list.getUser().getId().equals(userId))
                .map(shoppingListMapper::toDto)
                .toList();
    }

    @Transactional
    public void addItemToList(UUID listId, Long itemId) {
        ShoppingList list = shoppingListRepository.findById(listId)
                .orElseThrow(() -> notFound("Shopping list"));

        GroceryItem item = groceryItemRepository.findById(itemId)
                .orElseThrow(() -> notFound("Grocery item"));

        ShoppingListItem sli = ShoppingListItem.builder()
                .shoppingList(list)
                .groceryItem(item)
                .status(ShoppingListItem.Status.ADDED)
                .build();

        list.getItems().add(sli);
        shoppingListItemRepository.save(sli);
    }

    @Transactional
    public void markItemAsBought(Long listItemId) {
        ShoppingListItem sli = shoppingListItemRepository.findById(listItemId)
                .orElseThrow(() -> notFound("List item"));

        sli.setStatus(ShoppingListItem.Status.BOUGHT);
        shoppingListItemRepository.save(sli);
    }

    @Transactional
    public void removeItem(Long listItemId) {
        if (!shoppingListItemRepository.existsById(listItemId)) {
            throw notFound("List item");
        }
        shoppingListItemRepository.deleteById(listItemId);
    }

    private ResponseStatusException notFound(String resourceName) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, resourceName + " not found");
    }
}
