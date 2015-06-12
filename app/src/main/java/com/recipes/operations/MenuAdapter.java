package com.recipes.operations;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import com.recipes.operations.RecipeItemView_;

@EBean
public class MenuAdapter extends BaseAdapter {

    List<Recipe> recipe;

    RecipeData recipeData = new RecipeData();

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        recipe=recipeData.getRecipes();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RecipeItemView recipeItemView;
        if (convertView == null) {
            recipeItemView = RecipeItemView_.build(context);
        } else {
            recipeItemView = (RecipeItemView) convertView;
        }

        recipeItemView.bind(getItem(position));
        return recipeItemView;
    }

    @Override
    public int getCount() {
        return recipe.size();
    }

    @Override
    public Recipe getItem(int position) {
        return recipe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
