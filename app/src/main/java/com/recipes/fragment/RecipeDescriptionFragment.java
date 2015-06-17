package com.recipes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recipes.R;
import com.recipes.views.MainFragmentActivity;

import butterknife.ButterKnife;

public class RecipeDescriptionFragment extends Fragment {

    //@InjectView(R.id.descriptionText)
    TextView descriptionText;

    //@InjectView(R.id.titleText)
    TextView titleText;

    //@InjectView(R.id.subTitleText)
    TextView subTitleText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            updateDescription(savedInstanceState.getString(MainFragmentActivity.RECIPE_TITLE),
                    savedInstanceState.getString(MainFragmentActivity.RECIPE_SUBTITLE),
                    savedInstanceState.getString(MainFragmentActivity.RECIPE_DESCRIPTION));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            updateDescription(args.getString(MainFragmentActivity.RECIPE_TITLE),
                    args.getString(MainFragmentActivity.RECIPE_SUBTITLE),
                    args.getString(MainFragmentActivity.RECIPE_DESCRIPTION));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        ButterKnife.inject(this,view);
        return view;
    }

    private void updateDescription(String title,String subTitle,String description){
        titleText.setText(title);
        subTitleText.setText(subTitle);
        descriptionText.setText(description);
    }
}
