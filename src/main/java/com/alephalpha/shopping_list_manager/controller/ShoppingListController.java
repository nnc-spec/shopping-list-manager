package com.alephalpha.shopping_list_manager.controller;

import com.alephalpha.shopping_list_manager.dto.ShoppingListDto;
import com.alephalpha.shopping_list_manager.service.ShoppingListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @Operation(summary = "Create a new shopping list")
    @ApiResponse(responseCode = "201", description = "List created successfully")
    @PostMapping
    public ResponseEntity<ShoppingListDto> createList(@RequestParam String name, @RequestParam UUID userId) {
        ShoppingListDto createdList = shoppingListService.createList(name, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdList);
    }

    @Operation(summary = "Get a shopping list by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List found"),
            @ApiResponse(responseCode = "404", description = "List not found")
    })
    @GetMapping("/{listId}")
    public ResponseEntity<ShoppingListDto> getList(@PathVariable UUID listId) {
        return ResponseEntity.ok(shoppingListService.getList(listId));
    }

    @Operation(summary = "Get all shopping lists for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingListDto>> getListsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(shoppingListService.getListsByUser(userId));
    }

    @Operation(summary = "Add item to a shopping list")
    @ApiResponse(responseCode = "204", description = "Item added successfully")
    @PostMapping("/{listId}/add")
    public ResponseEntity<Void> addItemToList(@PathVariable UUID listId, @RequestParam Long itemId) {
        shoppingListService.addItemToList(listId, itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark item as bought")
    @ApiResponse(responseCode = "204", description = "Item marked as bought")
    @PostMapping("/item/{itemId}/markBought")
    public ResponseEntity<Void> markItemAsBought(@PathVariable Long itemId) {
        shoppingListService.markItemAsBought(itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove item from shopping list")
    @ApiResponse(responseCode = "204", description = "Item removed")
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        shoppingListService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
