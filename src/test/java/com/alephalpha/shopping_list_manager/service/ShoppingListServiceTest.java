package com.alephalpha.shopping_list_manager.service;

import com.alephalpha.shopping_list_manager.dto.ShoppingListDto;
import com.alephalpha.shopping_list_manager.entity.*;
import com.alephalpha.shopping_list_manager.mapper.ShoppingListMapper;
import com.alephalpha.shopping_list_manager.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingListServiceTest {

    private ShoppingListRepository shoppingListRepository;
    private UserRepository userRepository;
    private GroceryItemRepository groceryItemRepository;
    private ShoppingListItemRepository shoppingListItemRepository;
    private ShoppingListMapper shoppingListMapper;

    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        shoppingListRepository = mock(ShoppingListRepository.class);
        userRepository = mock(UserRepository.class);
        groceryItemRepository = mock(GroceryItemRepository.class);
        shoppingListItemRepository = mock(ShoppingListItemRepository.class);
        shoppingListMapper = mock(ShoppingListMapper.class);

        shoppingListService = new ShoppingListService(
                shoppingListRepository,
                userRepository,
                groceryItemRepository,
                shoppingListItemRepository,
                shoppingListMapper
        );
    }

    @Test
    void createList_shouldCreateAndReturnDto() {
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ShoppingList list = ShoppingList.builder().id(UUID.randomUUID()).name("Groceries").user(user).items(new ArrayList<>()).build();
        ShoppingListDto dto = new ShoppingListDto(); // Assume has default constructor

        when(shoppingListRepository.save(any())).thenReturn(list);
        when(shoppingListMapper.toDto(list)).thenReturn(dto);

        ShoppingListDto result = shoppingListService.createList("Groceries", userId);

        assertNotNull(result);
        verify(shoppingListRepository, times(1)).save(any());
    }

    @Test
    void createList_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.createList("Groceries", userId));
        assertEquals("User not found", ex.getReason());
    }

    @Test
    void getList_shouldReturnDtoIfExists() {
        UUID listId = UUID.randomUUID();
        ShoppingList list = ShoppingList.builder().id(listId).build();
        ShoppingListDto dto = new ShoppingListDto();

        when(shoppingListRepository.findById(listId)).thenReturn(Optional.of(list));
        when(shoppingListMapper.toDto(list)).thenReturn(dto);

        ShoppingListDto result = shoppingListService.getList(listId);
        assertNotNull(result);
    }

    @Test
    void getList_shouldThrowIfListNotFound() {
        UUID listId = UUID.randomUUID();
        when(shoppingListRepository.findById(listId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.getList(listId));
        assertEquals("Shopping list not found", ex.getReason());
    }

    @Test
    void getListsByUser_shouldReturnUserLists() {
        UUID userId = UUID.randomUUID();

        User user = User.builder().id(userId).build();
        ShoppingList list1 = ShoppingList.builder().id(UUID.randomUUID()).user(user).build();
        ShoppingList list2 = ShoppingList.builder().id(UUID.randomUUID()).user(user).build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(shoppingListRepository.findAll()).thenReturn(List.of(list1, list2));
        when(shoppingListMapper.toDto(any())).thenReturn(new ShoppingListDto());

        List<ShoppingListDto> result = shoppingListService.getListsByUser(userId);
        assertEquals(2, result.size());
    }

    @Test
    void getListsByUser_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.getListsByUser(userId));
        assertEquals("User not found", ex.getReason());
    }

    @Test
    void addItemToList_shouldAddItem() {
        UUID listId = UUID.randomUUID();
        Long itemId = 1L;

        ShoppingList list = ShoppingList.builder().id(listId).items(new ArrayList<>()).build();
        GroceryItem item = new GroceryItem();
        item.setId(itemId);

        when(shoppingListRepository.findById(listId)).thenReturn(Optional.of(list));
        when(groceryItemRepository.findById(itemId)).thenReturn(Optional.of(item));

        shoppingListService.addItemToList(listId, itemId);

        assertEquals(1, list.getItems().size());
        verify(shoppingListItemRepository, times(1)).save(any());
    }

    @Test
    void addItemToList_shouldThrowIfListNotFound() {
        when(shoppingListRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.addItemToList(UUID.randomUUID(), 1L));
        assertEquals("Shopping list not found", ex.getReason());
    }

    @Test
    void addItemToList_shouldThrowIfItemNotFound() {
        UUID listId = UUID.randomUUID();
        when(shoppingListRepository.findById(listId)).thenReturn(Optional.of(ShoppingList.builder().items(new ArrayList<>()).build()));
        when(groceryItemRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.addItemToList(listId, 1L));
        assertEquals("Grocery item not found", ex.getReason());
    }

    @Test
    void markItemAsBought_shouldUpdateStatus() {
        Long itemId = 1L;
        ShoppingListItem sli = ShoppingListItem.builder()
                .id(itemId)
                .status(ShoppingListItem.Status.ADDED)
                .build();

        when(shoppingListItemRepository.findById(itemId)).thenReturn(Optional.of(sli));

        shoppingListService.markItemAsBought(itemId);

        assertEquals(ShoppingListItem.Status.BOUGHT, sli.getStatus());
        verify(shoppingListItemRepository).save(sli);
    }

    @Test
    void markItemAsBought_shouldThrowIfNotFound() {
        when(shoppingListItemRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.markItemAsBought(1L));
        assertEquals("List item not found", ex.getReason());
    }

    @Test
    void removeItem_shouldDeleteIfExists() {
        Long itemId = 1L;
        when(shoppingListItemRepository.existsById(itemId)).thenReturn(true);

        shoppingListService.removeItem(itemId);

        verify(shoppingListItemRepository).deleteById(itemId);
    }

    @Test
    void removeItem_shouldThrowIfNotExists() {
        when(shoppingListItemRepository.existsById(any())).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> shoppingListService.removeItem(1L));
        assertEquals("List item not found", ex.getReason());
    }
}
