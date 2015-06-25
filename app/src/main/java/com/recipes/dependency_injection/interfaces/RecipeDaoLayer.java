package com.recipes.dependency_injection.interfaces;

import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.dependency_injection.modules.RecipeModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Michal Radtke on 2015-06-25.
 */
@Singleton
@Component(modules = {RecipeModule.class})
public interface RecipeDaoLayer {
    IRecipeDao recipeDao();
}
