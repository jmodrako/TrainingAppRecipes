package com.recipes.android.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.recipes.R;
import com.recipes.android.adapters.FragmentMenuAdapter;
import com.recipes.data.models.Recipe;

public class MenuFragment extends ListFragment {
    private MenuInterface listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentMenuAdapter adapter = new FragmentMenuAdapter(getActivity(),R.layout.adapter_recipes_list);
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
        //listener.onMenuSelected(recipeList.getRecipeData(position));
        getListView().setItemChecked(position, true);
    }

}
