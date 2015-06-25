package com.recipes.android.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.recipes.BuildConfig;
import com.recipes.R;
import com.recipes.RecipeApplication;
import com.recipes.RecipeApplicationTest;
import com.recipes.android.adapters.RecipesListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import butterknife.ButterKnife;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class MainActivityTest {

    private MockMainActivity activity;
    private Context context;
    ShadowActivity sut;

    @Before
    public void setup() throws Exception {
        context = ShadowApplication.getInstance().getApplicationContext();
        assertNotNull(context);
        activity = Robolectric.setupActivity(MockMainActivity.class);
        sut = Shadows.shadowOf(activity);
        assertNotNull("MainActivity is not instantiated", sut);
    }

    @Test
    public void validate() {
        ListView lv = (ListView) sut.findViewById(R.id.activity_main_lv_recipes_list);
        assertNotNull(lv);
    }
}

class MockMainActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        recipeDao = getAppController().getRecipeDao();

        recipesListAdapter = new RecipesListAdapter(this);
        if (getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(true);
        }
        pbLoadRecipesList.setVisibility(View.VISIBLE);
        downloadDataAndFillDB();
    }

    private RecipeApplicationTest getAppController() {
        return (RecipeApplicationTest) getApplication();
    }
}