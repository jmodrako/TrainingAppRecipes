package com.recipes.dependency_injection.modules;

import android.content.Context;

import com.recipes.android.adapters.RecipesListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by michal.radtke@mobica.com on 2015-06-25.
 */
@Module
public class RecipesListAdapterModule {
    private Context context;

    public RecipesListAdapterModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    RecipesListAdapter provideRecipesListAdapter(Context context) {
        return new RecipesListAdapter(context);
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
