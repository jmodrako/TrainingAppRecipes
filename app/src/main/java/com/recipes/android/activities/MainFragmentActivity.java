package com.recipes.android.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.recipes.R;
import com.recipes.android.fragments.MenuFragment;
import com.recipes.android.fragments.RecipeDescriptionFragment;
import com.recipes.data.models.Recipe;

public class MainFragmentActivity extends Activity implements MenuFragment.MenuInterface {

    public final static String RECIPE_DESCRIPTION = "com.recipe.activity.RECIPE_DESCRIPTION";
    public final static String RECIPE_TITLE = "com.recipe.activity.RECIPE_TITLE";
    public final static String RECIPE_SUBTITLE = "com.recipe.activity.RECIPE_SUBTITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        if (isItSmallDevice()) {
            createMenuLayout();
        }else{
            createDescriptionFragment(R.id.fragment_containerLarge, new Recipe());
        }

    }
    private void createMenuLayout(){
        MenuFragment menu = new MenuFragment();
        menu.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, menu).commit();
    }

    @Override
    public void onMenuSelected(Recipe recipe) {
        if (isItSmallDevice()){
            createDescriptionFragment(R.id.fragment_container, recipe);
        }else {
            createDescriptionFragment(R.id.fragment_containerLarge, recipe);
        }
    }

    private boolean isItSmallDevice(){
        return findViewById(R.id.fragment_container) != null;
    }
    private void createDescriptionFragment(int id, Recipe recipe){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        RecipeDescriptionFragment optionsFragment = new RecipeDescriptionFragment();
        optionsFragment.setArguments(getSendingArguments(recipe));
        transaction.replace(id, optionsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Bundle getSendingArguments(Recipe recipe){
        Bundle result = new Bundle();
        result.putString(RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        //result.putString(RECIPE_TITLE, recipe.getTitle());
        //.putString(RECIPE_SUBTITLE, recipe.getSubtitle());
        return result;
    }
}
