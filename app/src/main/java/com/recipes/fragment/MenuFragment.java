package com.recipes.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.recipes.R;


import com.recipes.operations.FragmentMenuAdapter;
import com.recipes.operations.Recipe;
import com.recipes.operations.RecipeData;

public class MenuFragment extends ListFragment {
    private MenuInterface listener;
    private RecipeData recipeData = new RecipeData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentMenuAdapter adapter = new FragmentMenuAdapter(getActivity(),R.layout.menu_row,
                recipeData);
        setListAdapter(adapter);
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
        super.onViewCreated(view, savedInstanceState);

    }

    public interface MenuInterface {
        public void onMenuSelected(Recipe recipe);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listener.onMenuSelected(recipeData.getRecipeData(position));
        getListView().setItemChecked(position, true);
    }

}
