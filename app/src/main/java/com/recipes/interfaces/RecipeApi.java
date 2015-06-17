package com.recipes.interfaces;

import com.recipes.models.RecipesListPojoSchema;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by michal.radtke@gmail.com on 2015-06-15.
 */
public interface RecipeApi {
    @GET("/cookbook/api/v1.0/recipes{id}")
    public void getRecipes(@Path("id") String recipe, Callback<RecipesListPojoSchema> response);

    @GET("/cookbook/api/v1.0/recipes/{id}")
    public void getRecipe(@Path("id") String recipe, Callback<RecipesListPojoSchema> response);
}
