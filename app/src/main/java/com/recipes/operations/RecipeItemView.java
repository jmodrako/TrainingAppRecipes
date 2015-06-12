package com.recipes.operations;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.recipes.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.menu_row)
public class RecipeItemView extends LinearLayout{

    @ViewById
    TextView title;

    @ViewById
    TextView subTitle;

    public RecipeItemView(Context context) {
        super(context);
    }

    public void bind(Recipe recipe) {
        title.setText(recipe.getTitle());
        subTitle.setText(recipe.getSubTitle());
    }
}
