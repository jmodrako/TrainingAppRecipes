package com.recipes.android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.recipes.data.models.Recipe;
import com.recipes.android.views.RecipeItemView;

import java.util.List;


public class FragmentMenuAdapter extends BaseAdapter {
    Context context;
    int layoutResourceId;
    List<Recipe> data;
    public FragmentMenuAdapter(Context context, int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
//        this.data = data.getRecipeList();
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
        RecipeItemView recipeItemView = null;
        if (convertView == null) {
            //recipeItemView = RecipeItemView_.build(context);
        } else {
            //recipeItemView = (RecipeItemView) convertView;
        }

        //recipeItemView.bind(getItem(position));

        return recipeItemView;
    }
}
