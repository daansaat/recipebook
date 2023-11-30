package com.recipebook.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipebook.dto.RecipeDto;
import com.recipebook.entity.Ingredient;
import com.recipebook.entity.Recipe;
import com.recipebook.repository.IngredientRepository;
import com.recipebook.repository.RecipeRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	
	@Autowired
	public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
		this.recipeRepository = recipeRepository;
		this.ingredientRepository = ingredientRepository;
	}
	
	public List<Ingredient> findAllIngredients() {
		return ingredientRepository.findAll();
	}

	public List<Recipe> findAllRecipe() {
		return recipeRepository.findAll();
	}

	@Transactional
	public Set<Recipe> findRecipeByIngredient(String ingredientName) {
		Ingredient ingredient = ingredientRepository.findByName(ingredientName);
		if (ingredient == null) {
			throw new EntityNotFoundException("Ingredient: " + ingredientName + " not found");
		}

		Hibernate.initialize(ingredient.getRecipes());

		return ingredient.getRecipes();
	}

	private Set<Ingredient> addIngredients(Set<String> recipeIngredients) {
		Set<Ingredient> ingredients = new HashSet<>();

		for (String name : recipeIngredients) {
			Ingredient ingredient = ingredientRepository.findByName(name);
			if (ingredient == null) {
				ingredient = ingredientRepository.save(new Ingredient(name));
			}
			ingredients.add(ingredient);
		}

		return ingredients;
	}

	public Recipe addRecipe(RecipeDto newRecipe) { 
		Recipe recipe = new Recipe(
			newRecipe.getName(),
			newRecipe.getVegetarian(),
			newRecipe.getServings(),
			newRecipe.getInstructions(),
			addIngredients(newRecipe.getIngredients())
			);

		return recipeRepository.save(recipe);
	}

	public Recipe updateRecipe(Long recipeId, RecipeDto updatedRecipe) {
		Recipe existingRecipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new EntityNotFoundException("Couldn't update. Recipe not found with ID: " + recipeId));

		if (updatedRecipe.getName() != null) {
			existingRecipe.setName(updatedRecipe.getName());
		}
		if (updatedRecipe.getVegetarian() != null) {
			existingRecipe.setVegetarian(updatedRecipe.getVegetarian());
		}
		if (updatedRecipe.getServings() != null) {
			existingRecipe.setServings(updatedRecipe.getServings());
		}
		if (updatedRecipe.getInstructions() != null) {
			existingRecipe.setInstructions(updatedRecipe.getInstructions());
		}
		if (updatedRecipe.getIngredients() != null) {
			Set<Ingredient> updatedIngredients = addIngredients(updatedRecipe.getIngredients());
			existingRecipe.setIngredients(updatedIngredients);
		}

		return recipeRepository.save(existingRecipe);
	}

	public void removeRecipe(Long recipeId) {
		Recipe existingRecipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new EntityNotFoundException("Couldn't remove. Recipe not found with ID: " + recipeId));
		recipeRepository.delete(existingRecipe);
	}

	public List<Recipe> filterRecipe(
		Boolean vegetarian, 
		Integer servings, 
		Set<String> includedIngredients, 
		Set<String> excludedIngredients, 
		String searchText) {

		Specification<Recipe> spec = Specification.where(RecipeSpecification.isVegetarian(vegetarian))
			.and(RecipeSpecification.hasServings(servings))
			.and(RecipeSpecification.hasText(searchText))
			.and(RecipeSpecification.hasIngredients(includedIngredients))
			.and(RecipeSpecification.doesNotHaveIngredients(excludedIngredients));

		return recipeRepository.findAll(spec);
	}
}
