package com.recipes.data.interfaces;

import com.recipes.data.models.Recipe;

import java.util.List;

/**
 * Created by Michal Radtke on 2015-06-24.
 */
public interface IRecipeDao<T> {

    List<Recipe> getAllRecipes();

    Recipe getRecipe(int recipeId);

    void updateRecipe(T recipe);

    void deleteRecipe(int recipeId);

    void insertRecipe(T recipe);

    void insertAllRecipes(List<T> recipes);

    long getCount();
}
