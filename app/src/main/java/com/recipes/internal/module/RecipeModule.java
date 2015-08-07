package com.recipes.internal.module;

import android.content.Context;

import com.recipes.data.database.RecipeDaoImpl;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeModule {

	private Context context;

	public RecipeModule(Context context) {
		this.context = context;
	}

	@Provides
	@Singleton IRecipeDao<Recipe> provideRecipeDao(Context context) {
		return new RecipeDaoImpl(context);
	}

	@Provides Context provideContext() {
		return context;
	}
}
