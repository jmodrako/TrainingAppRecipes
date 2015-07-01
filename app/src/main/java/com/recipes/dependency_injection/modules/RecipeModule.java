package com.recipes.dependency_injection.modules;

import android.content.Context;

import com.recipes.data.databases.RecipeDaoImpl;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.models.Recipe;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by michal.radtke@mobica.com on 2015-06-25.
 */
@Module
public class RecipeModule {

    private Context context;

    public RecipeModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    IRecipeDao<Recipe> provideRecipeDao(Context context) {
        return new RecipeDaoImpl(context);
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
