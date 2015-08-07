package com.recipes.internal.interfaces;

import com.recipes.android.adapter.RecipesListAdapter;
import com.recipes.internal.module.RecipesListAdapterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Michal Radtke on 2015-06-25.
 */
@Singleton
@Component(modules = {RecipesListAdapterModule.class})
public interface RecipesListAdapterLayer {
    RecipesListAdapter recipesListAdapter();
}
