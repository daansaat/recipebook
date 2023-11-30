package com.recipebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.recipebook.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
	
	List<Recipe> findAll(Specification<Recipe> specification);
	
	List<Recipe> findByName(String name);
	
	Optional<Recipe> findById(long id);
}
