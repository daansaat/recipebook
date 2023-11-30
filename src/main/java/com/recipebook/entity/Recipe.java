package com.recipebook.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Boolean vegetarian;
	private Integer servings;
	private String instructions;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
		name = "recipe_ingredient", 
		joinColumns = @JoinColumn(name = "recipe_id"), 
		inverseJoinColumns = @JoinColumn(name = "ingredient_id")
	)
	private Set<Ingredient> ingredients;

	protected Recipe() {}

	public Recipe(String name, Boolean vegetarian, Integer servings, String instructions, Set<Ingredient> ingredients) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.servings = servings;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		String ingredientNames = ingredients.stream()
			.map(Ingredient::getName)
			.collect(Collectors.joining(", "));
				
		return String.format(
			"Recipe[id=%d, name='%s', vegetarian=%b, servings=%d, instructions='%s', ingredients=[%s]]",
			id, name, vegetarian, servings, instructions, ingredientNames);
	}

	public Long getId() {
		return id;
	}

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

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

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

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
}
