package com.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipebook.entity.Ingredient;


public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	
	Ingredient findByName(String name);
	
	Ingredient findById(long id);
}
