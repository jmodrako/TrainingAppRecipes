package com.recipes.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


import com.recipes.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_recipe_description)
public class RecipeDescriptionActivity extends ActionBarActivity {

    @Extra
    String description;

    @Extra
    String title;

    @Extra
    String subTitle;

    @ViewById
    TextView descriptionText;

    @ViewById
    TextView titleText;

    @ViewById
    TextView subTitleText;

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @AfterViews
    void bindAdapter() {
        descriptionText.setText(description);
        titleText.setText(title);
        subTitleText.setText(subTitle);
    }
}
