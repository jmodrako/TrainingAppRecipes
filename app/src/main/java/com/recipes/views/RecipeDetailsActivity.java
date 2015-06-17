package com.recipes.views;

import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipes.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_recipe_details)
public class RecipeDetailsActivity extends ActionBarActivity {

    @Extra
    String extraRecipeDescription;

    @Extra
    String extraRecipeTitle;

    @Extra
    String extraRecipeSubtitle;

    @Extra
    String extraRecipeImageUrl;

    @ViewById
    TextView activityRecipeDetailsTvDescription;

    @ViewById
    TextView activityRecipeDetailsTvTitle;

    @ViewById
    TextView activityRecipeDetailsTvSubtitle;

    @ViewById
    ImageView activityRecipeDetailsIvImage;

    @AfterViews
    void init(){
        activityRecipeDetailsTvDescription.setText(extraRecipeDescription);
        activityRecipeDetailsTvTitle.setText(extraRecipeTitle);
        activityRecipeDetailsTvSubtitle.setText(extraRecipeSubtitle);
        Picasso.with(this)
                .load(extraRecipeImageUrl)
                .into(activityRecipeDetailsIvImage);
    }
}
