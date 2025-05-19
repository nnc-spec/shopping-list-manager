package com.alephalpha.shopping_list_manager.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommenderService {

    private static final Map<String, List<String>> COMPLEMENTARY_ITEMS = Map.of(
            "gin", List.of("tonic", "cucumber"),
            "chicken", List.of("rice", "broccoli", "red curry"),
            "pasta", List.of("tomato sauce", "cheese", "basil"),
            "bread", List.of("butter", "jam")
    );

    private static final Map<Set<String>, String> RECIPE_SUGGESTIONS = Map.of(
            Set.of("chicken", "red curry"), "Chicken Curry with Rice",
            Set.of("pasta", "tomato sauce"), "Simple Pasta with Sauce"
    );

    private static final Set<String> KNOWN_ITEMS = Set.of(
            "gin", "tonic", "cucumber", "chicken", "rice", "broccoli", "red curry",
            "pasta", "tomato sauce", "cheese", "basil", "bread", "butter", "jam"
    );

    public List<String> suggestAutocomplete(String prefix) {
        if (prefix == null || prefix.length() < 3) {
            return List.of();
        }

        String lowerPrefix = prefix.toLowerCase();
        return KNOWN_ITEMS.stream()
                .filter(item -> item.startsWith(lowerPrefix))
                .toList();
    }

    public List<String> suggestComplements(List<String> currentItems) {
        Set<String> lowerItems = currentItems.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> suggestions = new HashSet<>();

        for (String item : lowerItems) {
            List<String> complements = COMPLEMENTARY_ITEMS.get(item);
            if (complements != null) {
                complements.stream()
                        .filter(complement -> !lowerItems.contains(complement))
                        .forEach(suggestions::add);
            }
        }

        return new ArrayList<>(suggestions);
    }

    public List<String> suggestRecipes(List<String> currentItems) {
        Set<String> lowerItems = currentItems.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<String> results = new ArrayList<>();

        for (Map.Entry<Set<String>, String> entry : RECIPE_SUGGESTIONS.entrySet()) {
            if (lowerItems.containsAll(entry.getKey())) {
                results.add(entry.getValue());
            }
        }

        return results;
    }
}
