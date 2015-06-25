package com.recipes.data.databases;

import android.content.Context;

import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.models.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Michal Radtke on 2015-06-24.
 */

public class RecipeDaoImpl implements IRecipeDao<Recipe> {

    private static final String DB_NAME = "recipe_db.db";
    private static final String COLUMN_ID = "recipeId";

    Realm databaseManager;

    @Inject
    public RecipeDaoImpl(Context context) {
        databaseManager = Realm.getInstance(context, DB_NAME);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return databaseManager.allObjects(Recipe.class);
    }

    @Override
    public Recipe getRecipe(int recipeId) {
        RealmQuery<Recipe> query = databaseManager.where(Recipe.class);
        query.equalTo(COLUMN_ID,
                recipeId + 1); //we have to plus one because indexes in db start from 1
        RealmResults<Recipe> result = query.findAll();

        return result.first();
    }

    @Override
    public void updateRecipe(Recipe recipe) {

    }

    @Override
    public void deleteRecipe(Recipe recipe) {

    }

    @Override
    public void insertRecipe(Recipe recipe) {
        databaseManager.beginTransaction();
        databaseManager.copyToRealmOrUpdate(recipe);
        databaseManager.commitTransaction();
    }

    @Override
    public void insertAllRecipes(List<Recipe> recipes) {
        databaseManager.beginTransaction();
        databaseManager.copyToRealmOrUpdate(recipes);
        databaseManager.commitTransaction();
    }
}
