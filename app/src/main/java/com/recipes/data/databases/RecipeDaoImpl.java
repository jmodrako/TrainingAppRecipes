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

    Realm realm;

    @Inject
    public RecipeDaoImpl(Context context) {
        realm = Realm.getInstance(context, DB_NAME);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return realm.allObjects(Recipe.class);
    }

    @Override
    public Recipe getRecipe(int recipeId) {
        RealmQuery<Recipe> query = realm.where(Recipe.class);
        query.equalTo(COLUMN_ID,
                recipeId + 1); //we have to add one because indexes in db start from 1
        RealmResults<Recipe> result = query.findAll();

        return result.first();
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipe);
        realm.commitTransaction();
    }

    @Override
    public void deleteRecipe(int recipeId) {
        realm.beginTransaction();
        RealmResults<Recipe> recipes =
                realm.where(Recipe.class).equalTo("recipeId", recipeId).findAll();
        for (Recipe recipe : recipes) {
            recipe.removeFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public void insertRecipe(Recipe recipe) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipe);
        realm.commitTransaction();
    }

    @Override
    public void insertAllRecipes(List<Recipe> recipes) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipes);
        realm.commitTransaction();
    }

    @Override
    public long getCount() {
        return realm.allObjects(Recipe.class).size();
    }
}
