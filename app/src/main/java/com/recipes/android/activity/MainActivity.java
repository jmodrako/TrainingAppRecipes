package com.recipes.android.activity;

import com.recipes.BuildConfig;
import com.recipes.R;
import com.recipes.RecipeApplication;
import com.recipes.android.adapter.RecipesListAdapter;
import com.recipes.connection.Connection;
import com.recipes.connection.interfaces.IRecipeApi;
import com.recipes.connection.model.RecipesList;
import com.recipes.data.Settings;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;
import com.recipes.internal.interfaces.DaggerRecipesListAdapterLayer;
import com.recipes.internal.module.RecipesListAdapterModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This is a main class for this app.
 * Created by michal.radtke@mobica.com on 2015-06-29.
 */
//In AA: @EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	private static final String GET_ALL_RECIPES = "";
	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	private static final long REFRESH_ICON_ACTION_DELAY = 1000;

	//In AA: @ViewById(R.id.activity_main_lv_recipes_list)
	@InjectView(R.id.activity_main_lv_recipes_list) ListView lvRecipesList;
	@InjectView(R.id.activity_main_pb_load_recipes_list) ProgressBar pbLoadRecipesList;
	@InjectView(R.id.testLabel) TextView testLabel;

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

		testLabel.setText(BuildConfig.TEST_LABEL);

		recipeDao = getRecipeApplication().getRecipeDao();

		recipesListAdapter = DaggerRecipesListAdapterLayer.builder()
				.recipesListAdapterModule(new RecipesListAdapterModule(this)).build()
				.recipesListAdapter();

		if (getActionBar() != null) {
			getActionBar().setDisplayShowHomeEnabled(true);
		}

		pbLoadRecipesList.setVisibility(View.VISIBLE);
		downloadDataAndFillDB();
	}

	//In AA: @ItemClicked
	@OnItemClick(R.id.activity_main_lv_recipes_list)
	void lvRecipesList(int position, View clickedView) {
		/*In AA: RecipeDetailsActivity_.intent(this)
				.extraRecipeDescription(recipe.getRecipeDescription())
                .extraRecipeTitle(recipe.getRecipeTitle())
                .extraRecipeSubtitle(recipe.getRecipeSubtitle())
                .extraRecipeImageUrl(recipe.getRecipeImageUrl())
                .start();*/
		Recipe selectedRecipe = recipeDao.getRecipe(position);
		startDetailsActivity(selectedRecipe, clickedView);
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
			case R.id.action_animations:
				startActivity(new Intent(this, AnimationsActivity.class));
				return true;
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				return true;
			case R.id.action_refresh:
				setRefreshActionButtonState(true);
				downloadDataAndFillDB();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private RecipeApplication getRecipeApplication() {
		return (RecipeApplication) getApplication();
	}

	private void startDetailsActivity(Recipe selectedRecipe, View clickedView) {
		Intent result = new Intent(this, RecipeDetailsActivity.class);
		result.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_DESCRIPTION,
				selectedRecipe.getRecipeDescription());
		result.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_TITLE,
				selectedRecipe.getRecipeTitle());
		result.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_SUBTITLE,
				selectedRecipe.getRecipeSubtitle());
		result.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_IMAGE_URL,
				selectedRecipe.getRecipeImageUrl());

		//https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition
//		ActivityOptionsCompat options = ActivityOptionsCompat.
//				makeSceneTransitionAnimation(
//						this, clickedView.findViewById(R.id.adapter_recipes_list_iv_thumbnail), "image");

//		startActivity(result, options.toBundle());
		startActivity(result);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

	private void downloadDataAndFillDB() {
		IRecipeApi api = Connection.createApi(this);

		api.getRecipes(GET_ALL_RECIPES, new Callback<RecipesList>() {
			@Override
			public void success(RecipesList recipesList, Response response) {
				fillDbWithDownloadedData(recipesList);
				updateRecipesListView();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(LOG_TAG, error.getMessage());
				pbLoadRecipesList.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void fillDbWithDownloadedData(RecipesList recipesList) {
		Recipe recipe;
		List<Recipe> tempRecipesList = new ArrayList<Recipe>();
		for (int i = 0; i < recipesList.getRecipes().size(); i++) {
			recipe = new Recipe();
			recipe.setRecipeId(recipesList.getRecipes().get(i).getId());
			recipe.setRecipeTitle(recipesList.getRecipes().get(i).getTitle());
			recipe.setRecipeDescription(recipesList.getRecipes().get(i).getDescription());
			recipe.setRecipeSubtitle(recipesList.getRecipes().get(i).getSubtitle());
			recipe.setRecipeImageUrl(Settings.getEndpointAddress(this) +
					recipesList.getRecipes().get(i).getImageUrl());
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