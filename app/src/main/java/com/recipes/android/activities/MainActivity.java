package com.recipes.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.recipes.R;
import com.recipes.RecipeApplication;
import com.recipes.android.adapters.RecipesListAdapter;
import com.recipes.connection.interfaces.IRecipeApi;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.models.Recipe;
import com.recipes.data.models.RecipesListPojoSchema;
import com.recipes.dependency_injection.interfaces.DaggerRecipesListAdapterLayer;
import com.recipes.dependency_injection.modules.RecipesListAdapterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

//In AA: @EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    private static final String DATA_URL = "http://192.168.74.1:5000";
    private static final String IMAGES_URL = "http://192.168.74.1";
    private static final String GET_ALL_RECIPES = "";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final long REFRESH_ICON_ACTION_DELAY = 1000;

    //In AA: @ViewById(R.id.activity_main_lv_recipes_list)
    @InjectView(R.id.activity_main_lv_recipes_list)
    ListView lvRecipesList;

    @InjectView(R.id.activity_main_pb_load_recipes_list)
    private ProgressBar pbLoadRecipesList;

    //In AA: @Bean
    private RecipesListAdapter recipesListAdapter;
    //In AA: @Bean(RecipeDaoImpl.class)
    //IRecipeDao recipeDao;
    private IRecipeDao<Recipe> recipeDao;
    private Menu optionsMenu;

    //In AA: @AfterViews
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        recipeDao = getAppController().getRecipeDao();

        recipesListAdapter = DaggerRecipesListAdapterLayer.builder()
                .recipesListAdapterModule(new RecipesListAdapterModule(this)).build()
                .recipesListAdapter();
        if (getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(true);
        }
        pbLoadRecipesList.setVisibility(View.VISIBLE);
        downloadDataAndFillDB();
    }

    private RecipeApplication getAppController() {
        return (RecipeApplication) getApplication();
    }

    //In AA: @ItemClicked
    @OnItemClick(R.id.activity_main_lv_recipes_list)
    void lvRecipesList(int position) {
        /*In AA: RecipeDetailsActivity_.intent(this)
                .extraRecipeDescription(recipe.getRecipeDescription())
                .extraRecipeTitle(recipe.getRecipeTitle())
                .extraRecipeSubtitle(recipe.getRecipeSubtitle())
                .extraRecipeImageUrl(recipe.getRecipeImageUrl())
                .start();*/
        Recipe selectedRecipe = recipeDao.getRecipe(position);
        Intent recipeDetailIntent = new Intent(this, RecipeDetailsActivity.class);
        recipeDetailIntent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_DESCRIPTION,
                selectedRecipe.getRecipeDescription());
        recipeDetailIntent
                .putExtra(RecipeDetailsActivity.EXTRA_RECIPE_TITLE,
                        selectedRecipe.getRecipeTitle());
        recipeDetailIntent
                .putExtra(RecipeDetailsActivity.EXTRA_RECIPE_SUBTITLE,
                        selectedRecipe.getRecipeSubtitle());
        recipeDetailIntent
                .putExtra(RecipeDetailsActivity.EXTRA_RECIPE_IMAGE_URL,
                        selectedRecipe.getRecipeImageUrl());
        startActivity(recipeDetailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_action_bar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),
                        getString(R.string.settings_clicked_message), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_refresh:
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

    protected void downloadDataAndFillDB() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(DATA_URL)
                .build();

        IRecipeApi api = restAdapter.create(IRecipeApi.class);

        api.getRecipes(GET_ALL_RECIPES, new Callback<RecipesListPojoSchema>() {
            @Override
            public void success(RecipesListPojoSchema recipesListPojoSchema, Response response) {
                fillDB(recipesListPojoSchema);
                updateRecipesListView();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, error.getMessage());
                pbLoadRecipesList.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fillDB(RecipesListPojoSchema recipesListPojoSchema) {
        Recipe recipe;
        List<Recipe> tempRecipesList = new ArrayList<Recipe>();
        for (int i = 0; i < recipesListPojoSchema.getRecipes().size(); i++) {
            recipe = new Recipe();
            recipe.setRecipeId(recipesListPojoSchema.getRecipes().get(i).getId());
            recipe.setRecipeTitle(recipesListPojoSchema.getRecipes().get(i).getTitle());
            recipe.setRecipeDescription(recipesListPojoSchema.getRecipes().get(i).getDescription());
            recipe.setRecipeSubtitle(recipesListPojoSchema.getRecipes().get(i).getSubtitle());
            recipe.setRecipeImageUrl(
                    IMAGES_URL + recipesListPojoSchema.getRecipes().get(i).getImageUrl());
            tempRecipesList.add(i, recipe);
        }

        recipeDao.insertAllRecipes(tempRecipesList);
    }

    private void updateRecipesListView() {
        List<Recipe> tempRecipesList = recipeDao.getAllRecipes();
        recipesListAdapter.setData(tempRecipesList);
        pbLoadRecipesList.setVisibility(View.INVISIBLE);
        lvRecipesList.setAdapter(recipesListAdapter);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshActionButtonState(false);
            }
        }, REFRESH_ICON_ACTION_DELAY);
    }
}