package com.alephalpha.shopping_list_manager.controller;

import com.alephalpha.shopping_list_manager.dto.GroceryItemDto;
import com.alephalpha.shopping_list_manager.entity.GroceryItem;
import com.alephalpha.shopping_list_manager.mapper.GroceryItemMapper;
import com.alephalpha.shopping_list_manager.service.GroceryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class GroceryItemController {

    private final GroceryItemService groceryItemService;

    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @Operation(summary = "Get grocery item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllItems() {
        return ResponseEntity.ok(groceryItemService.getAll());
    }

    @Operation(summary = "Create a new grocery item")
    @ApiResponse(responseCode = "201", description = "Item created successfully")
    @PostMapping
    public ResponseEntity<GroceryItemDto> createItem(@Valid @RequestBody GroceryItemDto itemDto) {
        GroceryItem item = groceryItemService.create(GroceryItemMapper.toEntity(itemDto));
        return new ResponseEntity<>(GroceryItemMapper.toDto(item), HttpStatus.CREATED);
    }
}
