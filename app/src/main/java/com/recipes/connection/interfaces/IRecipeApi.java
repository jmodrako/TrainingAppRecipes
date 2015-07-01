package com.recipes.connection.interfaces;


import com.recipes.connection.schemas.RecipesListPojoSchema;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * This interface belong to Retrofit instance.
 * Created by michal.radtke@gmail.com on 2015-06-15.
 */
public interface IRecipeApi {
    @GET("/cookbook/api/v1.0/recipes{id}")
    void getRecipes(@Path("id") String recipe, Callback<RecipesListPojoSchema> response);

    @GET("/cookbook/api/v1.0/recipes/{id}")
    void getRecipe(@Path("id") String recipe, Callback<RecipesListPojoSchema> response);
}
