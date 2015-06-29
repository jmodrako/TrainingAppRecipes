package com.recipes.android.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.recipes.BuildConfig;
import com.recipes.R;
import com.recipes.RecipeApplication;
import com.recipes.RecipeApplicationTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(application = RecipeApplicationTest.class, constants = BuildConfig.class,
        emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class MainActivityTest {

    public static final String EXTRA_RECIPE_DESCRIPTION = "extra_recipe_description";
    public static final String EXTRA_RECIPE_TITLE = "extra_recipe_title";
    public static final String EXTRA_RECIPE_SUBTITLE = "extra_recipe_subtitle";
    public static final String EXTRA_RECIPE_IMAGE_URL = "extra_recipe_image_url";

    private MainActivity sut;
    private RecipeApplication app;

    @Before
    public void setup() throws Exception {
        sut = Robolectric.setupActivity(MainActivity.class);
        app = (RecipeApplication) sut.getApplication();
    }

    @Test
    public void checkIfTestApplicationIsInitialized() {
        assertTrue(sut.getApplication() instanceof RecipeApplicationTest);
    }

    @Test
    public void testDatabaseContent() {
        final String title = app.getRecipeDao().getRecipe(Matchers.anyInt()).getRecipeTitle();
        assertEquals(title, RecipeApplicationTest.EXAMPLE_TITLE);
        System.out.println("Dao class: " + app.getRecipeDao().getRecipe(324).getRecipeTitle());
    }

    @Test
    public void testRecipesListView() {
        ListView lv = (ListView) sut.findViewById(R.id.activity_main_lv_recipes_list);
        assertNotNull(lv);
        assertTrue(lv.getCount() == app.getRecipeDao().getAllRecipes().size());

        ListView mockListView = mock((ListView.class));
        when(mockListView.performItemClick(lv, 0, 0)).thenReturn(true);
        assertNotNull(app.getRecipeDao().getRecipe(0));
    }

    @Test
    public void testRecipesListView_startRecipeDetailsActivity() {
        sut.startActivity(prepareIntentForNextActivity());

        ShadowActivity shadowActivity = Shadows.shadowOf(sut);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull(intent.getExtras());
        String expected = RecipeApplicationTest.EXAMPLE_TITLE;
        String result = intent.getExtras().getString(EXTRA_RECIPE_TITLE);
        assertEquals(expected, result);
    }

    @Test
    public void checkLayout() {
        ProgressBar progressBar =
                (ProgressBar) sut.findViewById(R.id.activity_main_pb_load_recipes_list);
        assertNotNull(progressBar);
    }

    @Test
    public void testActionBar() {
        ActionBar actionBar = sut.getActionBar();
        assertNotNull(actionBar);
        assertEquals(actionBar.getTitle(), sut.getString(R.string.app_name));
        final int displayOptions = actionBar.getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
    }

    private Intent prepareIntentForNextActivity() {
        return new Intent(sut, RecipeDetailsActivity.class)
                .putExtra(EXTRA_RECIPE_TITLE, app.getRecipeDao().getRecipe(0).getRecipeTitle())
                .putExtra(EXTRA_RECIPE_SUBTITLE,
                        app.getRecipeDao().getRecipe(0).getRecipeSubtitle())
                .putExtra(EXTRA_RECIPE_DESCRIPTION,
                        app.getRecipeDao().getRecipe(0).getRecipeDescription())
                .putExtra(EXTRA_RECIPE_IMAGE_URL,
                        app.getRecipeDao().getRecipe(0).getRecipeImageUrl());
    }
}