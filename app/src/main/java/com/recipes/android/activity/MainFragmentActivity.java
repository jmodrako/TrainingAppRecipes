package com.recipes.android.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.recipes.R;
import com.recipes.android.fragment.MenuFragment;
import com.recipes.android.fragment.MenuFragment.MenuInterface;
import com.recipes.android.fragment.RecipeDescriptionFragment;
import com.recipes.data.model.Recipe;

public class MainFragmentActivity extends Activity implements MenuInterface {

	public final static String RECIPE_DESCRIPTION = "com.recipe.activity.RECIPE_DESCRIPTION";
	public final static String RECIPE_TITLE = "com.recipe.activity.RECIPE_TITLE";
	public final static String RECIPE_SUBTITLE = "com.recipe.activity.RECIPE_SUBTITLE";
	public static final String RECIPE_IMAGE_URL = "com.recipe.activity.RECIPE_IMAGE_URL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		if (isItSmallDevice()) {
			createMenuLayout(R.id.fragment_container);
		} else {
			createMenuLayout(R.id.fragmentList);
			createDescriptionFragment(R.id.fragment_containerLarge, new Recipe());
		}
	}

	@Override
	public void onMenuSelected(Recipe recipe) {
		if (isItSmallDevice()) {
			createDescriptionFragment(R.id.fragment_container, recipe);
		} else {
			createDescriptionFragment(R.id.fragment_containerLarge, recipe);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (getActionBar() != null) {
			getActionBar().setDisplayShowHomeEnabled(true);
		}
		getMenuInflater().inflate(R.menu.menu_action_bar_main_fragment, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				Toast.makeText(getApplicationContext(), getString(
						R.string.action_settings), Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		Fragment f = getFragmentManager().findFragmentById(R.id.fragment_container);
		if (f instanceof RecipeDescriptionFragment) {
			createMenuLayout(R.id.fragment_container);
		} else {
			finish();
		}
	}

	private void createMenuLayout(int id) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);

		MenuFragment optionsFragment = new MenuFragment();
		transaction.replace(id, optionsFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private boolean isItSmallDevice() {
		return findViewById(R.id.fragment_container) != null;
	}

	private void createDescriptionFragment(int id, Recipe recipe) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
		RecipeDescriptionFragment optionsFragment = new RecipeDescriptionFragment();
		optionsFragment.setArguments(getSendingArguments(recipe));
		transaction.replace(id, optionsFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private Bundle getSendingArguments(Recipe recipe) {
		Bundle result = new Bundle();
		result.putString(RECIPE_DESCRIPTION, recipe.getRecipeDescription());
		result.putString(RECIPE_TITLE, recipe.getRecipeTitle());
		result.putString(RECIPE_SUBTITLE, recipe.getRecipeSubtitle());
		result.putString(RECIPE_IMAGE_URL, recipe.getRecipeImageUrl());
		return result;
	}
}
