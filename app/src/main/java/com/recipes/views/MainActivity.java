package com.recipes.views;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.recipes.R;
import com.recipes.adapters.RecipesListAdapter;
import com.recipes.interfaces.RecipeApi;
import com.recipes.models.RecipeData;
import com.recipes.models.RecipesListPojoSchema;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    private static final String DATA_URL = "http://192.168.74.1:5000";
    private static final String IMAGES_URL = "http://192.168.74.1";
    private static final String DB_NAME = "recipe_db.db";
    private static final String GET_ALL_RECIPES = "";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final long REFRESH_ICON_ACTION_DELAY = 1000;

    private Realm databaseManager;
    private Menu optionsMenu;

    @ViewById
    ListView activityMainLvRecipesList;

    @Bean
    RecipesListAdapter recipesListAdapter;

    @ViewById
    ProgressBar activityMainProgressBar;

    @AfterViews
    void init() {
        if(getActionBar() != null){
            getActionBar().setDisplayShowHomeEnabled(true);
        }
        activityMainProgressBar.setVisibility(View.VISIBLE);
        databaseManager = Realm.getInstance(this, DB_NAME);
        downloadDataAndFillDB();
    }

    @ItemClick
    void activityMainLvRecipesListItemClicked(RecipeData recipeData) {
        RecipeDetailsActivity_.intent(this)
                .extraRecipeDescription(recipeData.getRecipeDescription())
                .extraRecipeTitle(recipeData.getRecipeTitle())
                .extraRecipeSubtitle(recipeData.getRecipeSubtitle())
                .extraRecipeImageUrl(recipeData.getRecipeImageUrl())
                .start();
    }

    private void downloadDataAndFillDB() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(DATA_URL)
                .build();

        RecipeApi api = restAdapter.create(RecipeApi.class);

        api.getRecipes(GET_ALL_RECIPES, new Callback<RecipesListPojoSchema>() {
            @Override
            public void success(RecipesListPojoSchema recipesListPojoSchema, Response response) {
                fillDB(recipesListPojoSchema);
                List<RecipeData> temp = databaseManager.allObjects(RecipeData.class);
                recipesListAdapter.setData(temp);
                activityMainProgressBar.setVisibility(View.INVISIBLE);
                activityMainLvRecipesList.setAdapter(recipesListAdapter);
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                    }
                }, REFRESH_ICON_ACTION_DELAY);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, error.getMessage());
                activityMainProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fillDB(RecipesListPojoSchema recipesListPojoSchema) {
        RecipeData recipeData;
        List<RecipeData> tempRecipesList = new ArrayList<RecipeData>();
        for (int i = 0; i < recipesListPojoSchema.getRecipes().size(); i++) {
            recipeData = new RecipeData();
            recipeData.setRecipeId(recipesListPojoSchema.getRecipes().get(i).getId());
            recipeData.setRecipeTitle(recipesListPojoSchema.getRecipes().get(i).getTitle());
            recipeData.setRecipeDescription(recipesListPojoSchema.getRecipes().get(i).getDescription());
            recipeData.setRecipeSubtitle(recipesListPojoSchema.getRecipes().get(i).getSubtitle());
            recipeData.setRecipeImageUrl(IMAGES_URL + recipesListPojoSchema.getRecipes().get(i).getImageUrl());
            tempRecipesList.add(i, recipeData);
        }

        databaseManager.beginTransaction();
        databaseManager.copyToRealmOrUpdate(tempRecipesList);
        databaseManager.commitTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.i(LOG_TAG, "settings");
                return true;
            case R.id.action_refresh:
                Log.i(LOG_TAG, "refresh");
                setRefreshActionButtonState(true);
                downloadDataAndFillDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRefreshActionButtonState(boolean doRefresh) {
        if (optionsMenu != null) {
            MenuItem refreshItem = optionsMenu.findItem(R.id.action_refresh);
            if (refreshItem != null) {
                if (doRefresh) {
                    refreshItem.setActionView(R.layout.action_bar_progress_bar);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }
}