package com.recipebook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;

	@ManyToMany(mappedBy = "ingredients")
	private Set<Recipe> recipes;

	protected Ingredient() {}

	public Ingredient(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format(
			"Ingredient[id=%d, name='%s']",
			id, name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Recipe> getRecipes() {
		return recipes;
	}
}