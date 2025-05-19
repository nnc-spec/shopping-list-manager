package com.alephalpha.shopping_list_manager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommenderServiceTest {

    private RecommenderService recommenderService;

    @BeforeEach
    void setUp() {
        recommenderService = new RecommenderService();
    }

    @Test
    void suggestAutocomplete_shouldReturnMatchingItems() {
        List<String> result = recommenderService.suggestAutocomplete("che");
        assertTrue(result.contains("cheese"));

        result = recommenderService.suggestAutocomplete("tom");
        assertEquals(List.of("tomato sauce"), result);
    }

    @Test
    void suggestAutocomplete_shouldReturnEmptyListForShortOrNullInput() {
        assertEquals(List.of(), recommenderService.suggestAutocomplete("to"));
        assertEquals(List.of(), recommenderService.suggestAutocomplete(null));
    }

    @Test
    void suggestComplements_shouldSuggestItemsNotInList() {
        List<String> input = List.of("chicken");
        List<String> result = recommenderService.suggestComplements(input);

        assertTrue(result.contains("rice"));
        assertTrue(result.contains("broccoli"));
        assertTrue(result.contains("red curry"));
        assertFalse(result.contains("chicken")); // Should not suggest the same item
    }

    @Test
    void suggestComplements_shouldExcludeAlreadyPresentComplements() {
        List<String> input = List.of("chicken", "rice", "broccoli");
        List<String> result = recommenderService.suggestComplements(input);

        assertEquals(List.of("red curry"), result);
    }

    @Test
    void suggestRecipes_shouldReturnMatchingRecipes() {
        List<String> input1 = List.of("chicken", "red curry");
        List<String> input2 = List.of("pasta", "tomato sauce");

        List<String> result1 = recommenderService.suggestRecipes(input1);
        assertEquals(List.of("Chicken Curry with Rice"), result1);

        List<String> result2 = recommenderService.suggestRecipes(input2);
        assertEquals(List.of("Simple Pasta with Sauce"), result2);
    }

    @Test
    void suggestRecipes_shouldIgnoreCaseAndOrder() {
        List<String> input = List.of("RED CURRY", "Chicken");
        List<String> result = recommenderService.suggestRecipes(input);

        assertEquals(List.of("Chicken Curry with Rice"), result);
    }

    @Test
    void suggestRecipes_shouldReturnEmptyIfNoMatch() {
        List<String> input = List.of("butter", "jam");
        List<String> result = recommenderService.suggestRecipes(input);

        assertTrue(result.isEmpty());
    }
}
