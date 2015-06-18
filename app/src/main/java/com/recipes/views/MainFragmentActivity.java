package com.recipes.views;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.recipes.R;
import com.recipes.fragment.MenuFragment;
import com.recipes.fragment.RecipeDescriptionFragment;
import com.recipes.models.RecipeData;

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
            createDescriptionFragment(R.id.fragment_containerLarge, new RecipeData());
        }

    }
    private void createMenuLayout(){
        MenuFragment menu = new MenuFragment();
        menu.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, menu).commit();
    }

    @Override
    public void onMenuSelected(RecipeData recipeData) {
        if (isItSmallDevice()){
            createDescriptionFragment(R.id.fragment_container, recipeData);
        }else {
            createDescriptionFragment(R.id.fragment_containerLarge, recipeData);
        }
    }

    private boolean isItSmallDevice(){
        return findViewById(R.id.fragment_container) != null;
    }
    private void createDescriptionFragment(int id, RecipeData recipeData){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        RecipeDescriptionFragment optionsFragment = new RecipeDescriptionFragment();
        optionsFragment.setArguments(getSendingArguments(recipeData));
        transaction.replace(id, optionsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Bundle getSendingArguments(RecipeData recipeData){
        Bundle result = new Bundle();
        result.putString(RECIPE_DESCRIPTION, recipeData.getRecipeDescription());
        //result.putString(RECIPE_TITLE, recipeData.getTitle());
        //.putString(RECIPE_SUBTITLE, recipeData.getSubtitle());
        return result;
    }
}
