package com.recipes.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.recipes.R;
import com.recipes.fragment.MenuFragment;
import com.recipes.fragment.RecipeDescriptionFragment;
import com.recipes.operations.Recipe;

public class MainFragmentActivity extends FragmentActivity implements MenuFragment.MenuInterface {

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
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, menu).commit();
    }

    @Override
    public void onMenuSelected(Recipe recipe) {
        if (isItSmallDevice()){
            createDescriptionFragment(R.id.fragment_container,recipe);
        }else {
            createDescriptionFragment(R.id.fragment_containerLarge,recipe);
        }
    }

    private boolean isItSmallDevice(){
        return findViewById(R.id.fragment_container) != null;
    }
    private void createDescriptionFragment(int id, Recipe recipe){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        RecipeDescriptionFragment optionsFragment = new RecipeDescriptionFragment();
        optionsFragment.setArguments(getSendingArguments(recipe));
        transaction.replace(id, optionsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Bundle getSendingArguments(Recipe recipe){
        Bundle result = new Bundle();
        result.putString(RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        result.putString(RECIPE_TITLE, recipe.getTitle());
        result.putString(RECIPE_SUBTITLE, recipe.getSubTitle());
        return result;
    }
}
