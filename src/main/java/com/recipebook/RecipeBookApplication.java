package com.recipebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.recipebook.dto.RecipeDto;
import com.recipebook.service.RecipeService;

import java.util.Set;

@SpringBootApplication
public class RecipeBookApplication {

	private static final Logger log = LoggerFactory.getLogger(RecipeBookApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RecipeBookApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(RecipeService recipeService) {
		return (args) -> {
			// save a few recipes
			recipeService.addRecipe(new RecipeDto(
				"Cake",
				true,
				10,
				"Mix ingredients and bake at 200c.",
				Set.of("milk", "eggs", "flour", "sugar")
			));
			recipeService.addRecipe(new RecipeDto(
				"Muffins",
				true,
				6,
				"Mix all ingredients and bake for 20min.",
				Set.of("milk", "eggs", "flour", "sugar", "cocoa")
			));
			recipeService.addRecipe(new RecipeDto(
				"Fried Rice",
				true,
				4,
				"Heat oil and fry ingredients over medium high heat.",
				Set.of("rice", "broccoli", "carrots", "soy sauce", "eggs")
			));
			recipeService.addRecipe(new RecipeDto(
				"Spaghetti Bolognese",
				false,
				4,
				"Make tomato saus with meat. Cook the spaghetti. Combine",
				Set.of("spaghetti", "tomato", "beef")
			));

			// update a recipe
			RecipeDto updatedRecipe = new RecipeDto();
			updatedRecipe.setName("Fried Rice with Chicken");
			updatedRecipe.setVegetarian(null);
			updatedRecipe.setServings(null);
			updatedRecipe.setInstructions(null);
			updatedRecipe.setIngredients(Set.of("chicken","rice", "broccoli", "carrots", "soy sauce"));
			recipeService.updateRecipe(3L, updatedRecipe);

			// filter recipes
			log.info("recipes found with filterRecipe():");
			log.info("-------------------------------");
			recipeService.filterRecipe(
				true, 
				null,
				null,
				null, 
				null
			).forEach(recipe -> {
				log.info(recipe.toString());
			});
			log.info("");
			
			// remove a recipe
			try {
				recipeService.removeRecipe(2L);
				recipeService.removeRecipe(99L);
			} catch (RuntimeException e) {
				System.err.println("Exception occured: " + e.getMessage());
			}

			// fetch recipes by ingredient
			log.info("recipes found with findRecipeByIngredient():");
			log.info("-------------------------------");
			recipeService.findRecipeByIngredient("eggs").forEach(recipe -> {
				log.info(recipe.toString());
			});
			log.info("");

			// fetch all ingredients
			log.info("ingredients found with findAllIngredients():");
			log.info("-------------------------------");
			recipeService.findAllIngredients().forEach(ingredient -> {
				log.info(ingredient.toString());
			});
			log.info("");

			// fetch all recipes
			log.info("recipes found with findAllRecipes():");
			log.info("-------------------------------");
			recipeService.findAllRecipe().forEach(recipe -> {
				log.info(recipe.toString());
			});
			log.info("");
		};
	}
}
