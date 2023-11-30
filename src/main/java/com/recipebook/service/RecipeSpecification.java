package com.recipebook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import com.recipebook.entity.Ingredient;
import com.recipebook.entity.Recipe;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class RecipeSpecification {

	public static Specification<Recipe> isVegetarian(Boolean vegetarian) {
		return (root, query, criteriaBuilder) -> {
			if (vegetarian == null) {
					return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("vegetarian"), vegetarian);
		};
	}

	public static Specification<Recipe> hasServings(Integer servings) {
		return (root, query, criteriaBuilder) -> {
			if (servings == null) {
					return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.greaterThanOrEqualTo(root.get("servings"), servings);
		};
	}

	public static Specification<Recipe> hasText(String searchText) {
		return (root, query, criteriaBuilder) -> {
			if (searchText == null) {
					return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.like(root.get("instructions"), "%" + searchText + "%");
		};
	}

	public static Specification<Recipe> hasIngredients(Set<String> ingredientNames) {
		return (root, query, criteriaBuilder) -> {
			if (ingredientNames == null || ingredientNames.isEmpty()) {
				return criteriaBuilder.conjunction();
			}
			List<Predicate> ingredientPredicates = new ArrayList<>();

			for (String ingredientName : ingredientNames) {
				Join<Recipe, Ingredient> ingredients = root.join("ingredients");
				Predicate ingredientPredicate = criteriaBuilder.equal(ingredients.get("name"), ingredientName);
				ingredientPredicates.add(ingredientPredicate);
			}
			return criteriaBuilder.and(ingredientPredicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<Recipe> doesNotHaveIngredients(Set<String> excludedIngredients) {
		return (root, query, criteriaBuilder) -> {
			if (excludedIngredients == null || excludedIngredients.isEmpty()) {
				return criteriaBuilder.conjunction();
			}
			Subquery<Long> subquery = query.subquery(Long.class);
			Root<Recipe> subRoot = subquery.from(Recipe.class);
			Join<Recipe, Ingredient> subIngredients = subRoot.join("ingredients");
			Predicate excludedPredicate = subIngredients.get("name").in(excludedIngredients);
			subquery.select(subRoot.get("id")).where(excludedPredicate);

			return criteriaBuilder.not(root.get("id").in(subquery));
		};
	}
}
