package com.recipes.android.adapter;

import android.widget.ListView;

import com.recipes.BuildConfig;
import com.recipes.RecipeApplicationTest;
import com.recipes.TestUtils;
import com.recipes.data.model.Recipe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by Michal Radtke on 2015-06-29.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = RecipeApplicationTest.class, constants = BuildConfig.class,
        emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class RecipesListAdapterTest {

    private RecipesListAdapter sut;

    @Before
    public void setup() throws Exception {
        sut = new RecipesListAdapter(ShadowApplication.getInstance().getApplicationContext());
    }

    @Test
    public void testGetCount() {
        sut.setData(prepareRecipesList());
        assertEquals(prepareRecipesList().size(), sut.getCount());
    }

    @Test
    public void testGetItemId() {
        ListView mockListView = mock(ListView.class);
        mockListView.setAdapter(sut);

        mockListView.performItemClick(mockListView, 0, 0);
        assertEquals(mockListView.getItemIdAtPosition(0), sut.getItemId(0));
    }

    @Test
    public void testGetItem() {
        ListView mockListView = mock(ListView.class);
        mockListView.setAdapter(sut);

        mockListView.performItemClick(mockListView, 0, 0);
        Recipe expectedRecipeAtPosition = sut.getItem(0);
        Recipe actualRecipeAtPosition = (Recipe) mockListView.getItemAtPosition(0);
        assertEquals(expectedRecipeAtPosition, actualRecipeAtPosition);
    }

    /**
     * This method prepares a recipes list for tested adapter.
     *
     * @return prepared recipes list.
     */
    private List<Recipe> prepareRecipesList() {
        List<Recipe> recipesList = new ArrayList<Recipe>();
        recipesList.add(TestUtils.getRecipeExample1());
        recipesList.add(TestUtils.getRecipeExample2());
        return recipesList;
    }
}
