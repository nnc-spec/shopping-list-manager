package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.entity.GroceryItem;
import com.alephalpha.shopping_list_manager.repository.GroceryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroceryItemServiceTest {

    private GroceryItemRepository groceryItemRepository;
    private GroceryItemService groceryItemService;

    @BeforeEach
    void setUp() {
        // Create a mock instance of the repository
        groceryItemRepository = mock(GroceryItemRepository.class);
        // Inject the mock into the service
        groceryItemService = new GroceryItemService(groceryItemRepository);
    }

    @Test
    void getAll_shouldReturnListOfItems() {
        // Arrange: prepare mock data
        GroceryItem item1 = new GroceryItem();
        item1.setId(1L);
        item1.setName("Milk");

        GroceryItem item2 = new GroceryItem();
        item2.setId(2L);
        item2.setName("Bread");

        when(groceryItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        // Act: call the method under test
        List<GroceryItem> result = groceryItemService.getAll();

        // Assert: verify expected behavior
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Milk", result.get(0).getName());
        assertEquals("Bread", result.get(1).getName());
        verify(groceryItemRepository, times(1)).findAll();
    }

    @Test
    void create_shouldSaveAndReturnItem() {
        // Arrange: create item to save
        GroceryItem newItem = new GroceryItem();
        newItem.setName("Cheese");

        GroceryItem savedItem = new GroceryItem();
        savedItem.setId(10L);
        savedItem.setName("Cheese");

        when(groceryItemRepository.save(newItem)).thenReturn(savedItem);

        // Act: call the service method
        GroceryItem result = groceryItemService.create(newItem);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Cheese", result.getName());
        verify(groceryItemRepository, times(1)).save(newItem);
    }
}
