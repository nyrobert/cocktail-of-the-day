package com.thecocktaildb;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class CocktailOfTheDay {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {

        HttpResponse<String> response;

        try {
            response = ApiClient.create().getCocktail();
        } catch (IOException | InterruptedException e) {
            System.out.println("API error");
            return;
        }

        JsonNode responseJsonNode;

        try {
            responseJsonNode = mapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            System.out.println("JSON parse error");
            return;
        }

        JsonNode cocktail = responseJsonNode.get("drinks").get(0);

        ArrayList<String> ingredientsList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            if (!cocktail.get("strIngredient" + i).isNull()) {
                ingredientsList.add(cocktail.get("strIngredient" + i).asText());
            }
        }

        System.out.println("Name: " + cocktail.get("strDrink").asText());
        System.out.println("Ingredients: " + ingredientsList);
        System.out.println("Instructions: " + cocktail.get("strInstructions").asText());
    }
}