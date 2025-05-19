package com.alephalpha.shopping_list_manager.controller;

import com.alephalpha.shopping_list_manager.service.RecommenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommenderController {

    private final RecommenderService recommenderService;

    public RecommenderController(RecommenderService recommenderService) {
        this.recommenderService = recommenderService;
    }

    @Operation(summary = "Autocomplete item names", description = "Suggests items based on the provided input prefix")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Suggestions retrieved successfully")
    })
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String input) {
        return ResponseEntity.ok(recommenderService.suggestAutocomplete(input));
    }

    @Operation(summary = "Suggest complementary items", description = "Suggests items that commonly go together with the provided items")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Complementary items retrieved successfully")
    })
    @GetMapping("/complements")
    public ResponseEntity<List<String>> complements(@RequestParam List<String> items) {
        return ResponseEntity.ok(recommenderService.suggestComplements(items));
    }

    @Operation(summary = "Suggest recipes", description = "Suggests recipes based on the provided items")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recipe suggestions retrieved successfully")
    })
    @GetMapping("/recipes")
    public ResponseEntity<List<String>> recipes(@RequestParam List<String> items) {
        return ResponseEntity.ok(recommenderService.suggestRecipes(items));
    }
}
