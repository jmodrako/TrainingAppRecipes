package com.recipes.android.fragments;

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

import com.recipes.R;
import com.recipes.android.adapters.FragmentMenuAdapter;
import com.recipes.connection.interfaces.IRecipeApi;
import com.recipes.data.models.Recipe;
import com.recipes.data.models.RecipesListPojoSchema;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MenuFragment extends ListFragment {

    private Menu optionsMenu;
    private static final String DATA_URL = "http://192.168.56.1:5000";
    private static final String IMAGES_URL = "http://192.168.56.1";
    private static final String DB_NAME = "recipe_db.db";
    private static final String GET_ALL_RECIPES = "";
    private static final String LOG_TAG = MenuFragment.class.getSimpleName();
    private static final long REFRESH_ICON_ACTION_DELAY = 1000;

    private Realm databaseManager;
    private MenuInterface listener;

    private FragmentMenuAdapter adapter;
    private List<Recipe> recipesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FragmentMenuAdapter(getActivity(),R.layout.adapter_recipes_list);
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
        databaseManager = Realm.getInstance(view.getContext(), DB_NAME);
        downloadDataAndFillDB();
        super.onViewCreated(view, savedInstanceState);

    }

    public interface MenuInterface {
        public void onMenuSelected(Recipe recipe);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listener.onMenuSelected(recipesList.get(position));
        getListView().setItemChecked(position, true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        optionsMenu=menu;
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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(DATA_URL)
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
            }
        });
    }

    private void fillDB(RecipesListPojoSchema recipesListPojoSchema) {
        Recipe recipeData;
        List<Recipe> tempRecipesList = new ArrayList<Recipe>();
        for (int i = 0; i < recipesListPojoSchema.getRecipes().size(); i++) {
            recipeData = new Recipe();
            recipeData.setRecipeId(recipesListPojoSchema
                    .getRecipes().get(i).getId());
            recipeData.setRecipeTitle(recipesListPojoSchema
                    .getRecipes().get(i).getTitle());
            recipeData.setRecipeDescription(recipesListPojoSchema.getRecipes()
                    .get(i).getDescription());
            recipeData.setRecipeSubtitle(recipesListPojoSchema.getRecipes()
                    .get(i).getSubtitle());
            recipeData.setRecipeImageUrl(IMAGES_URL
                    + recipesListPojoSchema.getRecipes().get(i).getImageUrl());
            tempRecipesList.add(i, recipeData);
        }

        databaseManager.beginTransaction();
        databaseManager.copyToRealmOrUpdate(tempRecipesList);
        databaseManager.commitTransaction();
    }

    private void updateRecipesListView() {
        List<Recipe> tempRecipesList = databaseManager.allObjects(Recipe.class);
        adapter.setData(tempRecipesList);
        setListAdapter(adapter);
        recipesList=tempRecipesList;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshActionButtonState(false);
            }
        }, REFRESH_ICON_ACTION_DELAY);
    }

}
