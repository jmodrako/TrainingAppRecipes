package com.recipes.internal.module;

import android.content.Context;

import com.recipes.android.adapter.RecipesListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipesListAdapterModule {
	private Context context;

	public RecipesListAdapterModule(Context context) {
		this.context = context;
	}

	@Provides
	@Singleton RecipesListAdapter provideRecipesListAdapter(Context context) {
		return new RecipesListAdapter(context);
	}

	@Provides Context provideContext() {
		return context;
	}
}
