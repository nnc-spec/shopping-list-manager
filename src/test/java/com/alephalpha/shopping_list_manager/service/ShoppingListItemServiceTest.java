package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.entity.ShoppingListItem;
import com.alephalpha.shopping_list_manager.repository.ShoppingListItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingListItemServiceTest {

    private ShoppingListItemRepository shoppingListItemRepository;
    private ShoppingListItemService shoppingListItemService;

    @BeforeEach
    void setUp() {
        shoppingListItemRepository = mock(ShoppingListItemRepository.class);
        shoppingListItemService = new ShoppingListItemService(shoppingListItemRepository);
    }

    @Test
    void markAsBought_shouldUpdateStatusToBought() {
        Long itemId = 1L;
        ShoppingListItem item = ShoppingListItem.builder()
                .id(itemId)
                .status(ShoppingListItem.Status.ADDED)
                .build();

        when(shoppingListItemRepository.findById(itemId)).thenReturn(Optional.of(item));

        shoppingListItemService.markAsBought(itemId);

        assertEquals(ShoppingListItem.Status.BOUGHT, item.getStatus());
        verify(shoppingListItemRepository).save(item);
    }

    @Test
    void markAsBought_shouldThrowIfItemNotFound() {
        when(shoppingListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListItemService.markAsBought(999L));
        assertEquals("List item not found", ex.getReason());
    }

    @Test
    void remove_shouldDeleteItemIfExists() {
        Long itemId = 2L;
        when(shoppingListItemRepository.existsById(itemId)).thenReturn(true);

        shoppingListItemService.remove(itemId);

        verify(shoppingListItemRepository).deleteById(itemId);
    }

    @Test
    void remove_shouldThrowIfItemDoesNotExist() {
        Long itemId = 3L;
        when(shoppingListItemRepository.existsById(itemId)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListItemService.remove(itemId));
        assertEquals("List item not found", ex.getReason());
    }
}
