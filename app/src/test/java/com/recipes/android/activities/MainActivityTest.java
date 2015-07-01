package com.recipes.android.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.recipes.BuildConfig;
import com.recipes.R;
import com.recipes.RecipeApplicationTest;
import com.recipes.TestUtils;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.models.Recipe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link com.recipes.android.activities.MainActivity}
 * Created by michal.radtke@mobica.com on 2015-06-29.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = RecipeApplicationTest.class, constants = BuildConfig.class,
        emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class MainActivityTest {

    private static final String EXTRA_RECIPE_DESCRIPTION = "extra_recipe_description";
    private static final String EXTRA_RECIPE_TITLE = "extra_recipe_title";
    private static final String EXTRA_RECIPE_SUBTITLE = "extra_recipe_subtitle";
    private static final String EXTRA_RECIPE_IMAGE_URL = "extra_recipe_image_url";
    private static final int FIRST_ITEM = 0;

    private MainActivity sut;
    private IRecipeDao<Recipe> dao;

    @Before
    public void setup() throws Exception {
        sut = Robolectric.setupActivity(MainActivity.class);
        RecipeApplicationTest app = (RecipeApplicationTest) sut.getApplication();
        dao = app.getRecipeDao();
        dao.insertRecipe(TestUtils.getRecipeExample1());
    }

    @Test
    public void checkIfTestApplicationIsInitialized() {
        assertTrue(sut.getApplication() instanceof RecipeApplicationTest);
    }

    @Test
    public void testRecipesListView() {
        ListView lv = (ListView) sut.findViewById(R.id.activity_main_lv_recipes_list);
        assertNotNull(lv);
        assertEquals(TestUtils.EXAMPLE_1_TITLE,
                dao.getRecipe(TestUtils.EXAMPLE_1_ID).getRecipeTitle());

        ListView mockListView = mock((ListView.class));
        mockListView.performItemClick(lv, FIRST_ITEM, 0);
        assertNotNull(dao.getRecipe(FIRST_ITEM));
    }

    @Test
    public void testRecipesListView_startRecipeDetailsActivity() {
        assertEquals(TestUtils.EXAMPLE_1_TITLE,
                dao.getRecipe(TestUtils.EXAMPLE_1_ID).getRecipeTitle());
        sut.startActivity(prepareIntentForNextActivity());

        ShadowActivity shadowActivity = Shadows.shadowOf(sut);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull(intent.getExtras());
        assertEquals(TestUtils.EXAMPLE_1_TITLE, intent.getExtras().getString(EXTRA_RECIPE_TITLE));
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

    /**
     * This method prepares extras for next activity.
     *
     * @return prepared intent with extras.
     */
    private Intent prepareIntentForNextActivity() {
        return new Intent(sut, RecipeDetailsActivity.class)
                .putExtra(EXTRA_RECIPE_TITLE, dao.getRecipe(FIRST_ITEM).getRecipeTitle())
                .putExtra(EXTRA_RECIPE_SUBTITLE, dao.getRecipe(FIRST_ITEM).getRecipeSubtitle())
                .putExtra(EXTRA_RECIPE_DESCRIPTION,
                        dao.getRecipe(FIRST_ITEM).getRecipeDescription())
                .putExtra(EXTRA_RECIPE_IMAGE_URL, dao.getRecipe(FIRST_ITEM).getRecipeImageUrl());
    }
}