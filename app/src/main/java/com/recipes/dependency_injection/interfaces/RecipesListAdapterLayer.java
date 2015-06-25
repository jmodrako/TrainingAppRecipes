package com.recipes.dependency_injection.interfaces;

import com.recipes.android.adapters.RecipesListAdapter;
import com.recipes.dependency_injection.modules.RecipesListAdapterModule;

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
