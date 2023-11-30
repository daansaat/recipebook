package com.recipebook.dto;

import java.util.Set;

public class RecipeDto {

	private String name;
	private Boolean vegetarian;
	private Integer servings;
	private String instructions;
	private Set<String> ingredients;

	public RecipeDto() {}

	public RecipeDto(String name, Boolean vegetarian, Integer servings, String instructions, Set<String> ingredients) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.servings = servings;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}
	
	// Getters
	public String getName() {
		return name;
	}

	public Boolean getVegetarian() {
		return vegetarian;
	}

	public Integer getServings() {
		return servings;
	}

	public String getInstructions() {
		return instructions;
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setVegetarian(Boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}
}
