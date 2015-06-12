package com.recipes.activity;

import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.recipes.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import com.recipes.operations.MenuAdapter;
import com.recipes.operations.Recipe;


@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @ViewById
    ListView recipesList;

    @Bean
    MenuAdapter adapter;

    @AfterViews
    void bindAdapter() {
        recipesList.setAdapter(adapter);
    }

    @ItemClick
    void recipesListItemClicked(Recipe recipe) {
        RecipeDescriptionActivity_ recipeDescriptionActivity = new RecipeDescriptionActivity_();
        recipeDescriptionActivity.intent(this).description(recipe.getRecipeDescription());
        recipeDescriptionActivity.intent(this).title(recipe.getTitle());
        recipeDescriptionActivity.intent(this).subTitle(recipe.getSubTitle());
        recipeDescriptionActivity.intent(this).start();
    }
}