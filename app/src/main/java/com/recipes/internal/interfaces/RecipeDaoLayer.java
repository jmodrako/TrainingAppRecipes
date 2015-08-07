package com.recipes.internal.interfaces;

import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;
import com.recipes.internal.module.RecipeModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by michal.radtke@mobica.com on 2015-06-25.
 */
@Singleton
@Component(modules = {RecipeModule.class})
public interface RecipeDaoLayer {
    IRecipeDao<Recipe> recipeDao();
}
