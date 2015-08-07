package com.recipes.data.interfaces;

import com.recipes.data.model.Recipe;

import java.util.List;

public interface IRecipeDao<T> {

	List<Recipe> getAllRecipes();

	Recipe getRecipe(int recipeId);

	void updateRecipe(T recipe);

	void deleteRecipe(int recipeId);

	void insertRecipe(T recipe);

	void insertAllRecipes(List<T> recipes);

	long getCount();
}
