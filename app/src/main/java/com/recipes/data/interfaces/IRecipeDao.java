package com.recipes.data.interfaces;

import com.recipes.data.models.Recipe;

import java.util.List;

/**
 * Created by Michal Radtke on 2015-06-24.
 */

public interface IRecipeDao<T> {

    public List<Recipe> getAllRecipes();

    public Recipe getRecipe(int recipeId);

    public void updateRecipe(T recipe);

    public void deleteRecipe(int recipeId);

    public void insertRecipe(T recipe);

    public void insertAllRecipes(List<T> recipes);

    public long getCount();
}
