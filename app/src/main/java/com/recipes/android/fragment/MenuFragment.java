package com.recipes.android.fragment;

import com.recipes.R;
import com.recipes.RecipeApplication;
import com.recipes.android.adapter.RecipesListAdapter;
import com.recipes.connection.Connection;
import com.recipes.connection.interfaces.IRecipeApi;
import com.recipes.connection.model.RecipesList;
import com.recipes.data.Settings;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MenuFragment extends ListFragment {

	private static final String GET_ALL_RECIPES = "";
	private static final String LOG_TAG = MenuFragment.class.getSimpleName();
	private static final long REFRESH_ICON_ACTION_DELAY = 1000;

	private MenuInterface listener;
	private Menu optionsMenu;

	private RecipesListAdapter adapter;
	private List<Recipe> recipesList;
	private IRecipeDao<Recipe> recipeDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new RecipesListAdapter(getActivity());
		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (MenuInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		recipeDao = getRecipeApplication().getRecipeDao();
		downloadDataAndFillDB();
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		listener.onMenuSelected(recipesList.get(position));
		getListView().setItemChecked(position, true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		optionsMenu = menu;
		if (optionsMenu != null) {
			optionsMenu.findItem(R.id.action_refresh).setVisible(true);
		}
		super.onCreateOptionsMenu(optionsMenu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_refresh:
				setRefreshActionButtonState(true);
				downloadDataAndFillDB();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private RecipeApplication getRecipeApplication() {
		return (RecipeApplication) getActivity().getApplication();
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
		IRecipeApi api = Connection.createApi(getActivity());

		api.getRecipes(GET_ALL_RECIPES, new Callback<RecipesList>() {
			@Override
			public void success(RecipesList recipesList, Response response) {
				fillDbWithDownloadedData(recipesList);
				updateRecipesListView();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(LOG_TAG, error.getMessage());
			}
		});
	}

	private void fillDbWithDownloadedData(RecipesList recipesList) {
		Recipe recipeData;
		List<Recipe> tempRecipesList = new ArrayList<Recipe>();
		for (int i = 0; i < recipesList.getRecipes().size(); i++) {
			recipeData = new Recipe();
			recipeData.setRecipeId(recipesList
					.getRecipes().get(i).getId());
			recipeData.setRecipeTitle(recipesList
					.getRecipes().get(i).getTitle());
			recipeData.setRecipeDescription(recipesList.getRecipes()
					.get(i).getDescription());
			recipeData.setRecipeSubtitle(recipesList.getRecipes()
					.get(i).getSubtitle());
			recipeData.setRecipeImageUrl(Settings.getEndpointAddress(getActivity())
					+ recipesList.getRecipes().get(i).getImageUrl());
			tempRecipesList.add(i, recipeData);
		}

		recipeDao.insertAllRecipes(tempRecipesList);
	}

	private void updateRecipesListView() {
		List<Recipe> tempRecipesList = recipeDao.getAllRecipes();
		adapter.setData(tempRecipesList);
		setListAdapter(adapter);
		recipesList = tempRecipesList;
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			@Override
			public void run() {
				setRefreshActionButtonState(false);
			}
		}, REFRESH_ICON_ACTION_DELAY);
	}

	public interface MenuInterface {
		void onMenuSelected(Recipe recipe);
	}
}
