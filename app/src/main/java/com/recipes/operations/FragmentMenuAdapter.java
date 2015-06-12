package com.recipes.operations;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import com.recipes.operations.RecipeItemView_;


public class FragmentMenuAdapter extends BaseAdapter {
    Context context;
    int layoutResourceId;
    List<Recipe> data;
    public FragmentMenuAdapter(Context context, int layoutResourceId, RecipeData data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data.getRecipes();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Recipe getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        RecipeItemView recipeItemView;
        if (convertView == null) {
            recipeItemView = RecipeItemView_.build(context);
        } else {
            recipeItemView = (RecipeItemView) convertView;
        }

        recipeItemView.bind(getItem(position));

        return recipeItemView;
    }
}
