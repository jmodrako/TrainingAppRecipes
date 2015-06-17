package com.recipes.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.recipes.models.RecipeData;
import com.recipes.widgets.RecipeItemView;
import com.recipes.widgets.RecipeItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class RecipesListAdapter extends BaseAdapter {

    List<RecipeData> recipesList;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {}

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
        int result = 0;
        if(recipesList != null){
            result = recipesList.size();
        }
        return result;
    }

    @Override
    public RecipeData getItem(int position) {
        RecipeData result = null;
        if(recipesList != null){
            result = recipesList.get(position);
        }
        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<RecipeData> recipesList){
        this.recipesList = recipesList;
    }
}
