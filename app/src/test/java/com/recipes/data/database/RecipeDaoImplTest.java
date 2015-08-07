package com.recipes.data.database;

import android.app.Activity;

import com.recipes.BuildConfig;
import com.recipes.RecipeApplicationTest;
import com.recipes.TestUtils;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link com.recipes.data.database.RecipeDaoImpl}
 * Created by michal.radtke@mobica.com on 2015-06-30.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = RecipeApplicationTest.class, constants = BuildConfig.class,
		emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class RecipeDaoImplTest {

	private Activity activity;
	private IRecipeDao<Recipe> dao;

	@Before public void setup() throws Exception {
		activity = Robolectric.setupActivity(Activity.class);
		RecipeApplicationTest app = (RecipeApplicationTest) activity.getApplication();
		dao = app.getRecipeDao();
		dao.insertRecipe(TestUtils.getRecipeExample1());
		dao.insertRecipe(TestUtils.getRecipeExample2());
	}

	@Test public void checkIfTestApplicationIsInitialized() {
		assertTrue(activity.getApplication() instanceof RecipeApplicationTest);
	}

	@Test public void testDatabase_insertRecipe() {
		dao.insertRecipe(TestUtils.getRecipeExample2());

		assertEquals(TestUtils.EXAMPLE_2_TITLE,
				dao.getRecipe(TestUtils.EXAMPLE_2_ID).getRecipeTitle());
	}

	@Test public void testDatabase_updateRecipe() {
		assertEquals(TestUtils.EXAMPLE_2_TITLE,
				dao.getRecipe(TestUtils.EXAMPLE_2_ID).getRecipeTitle());

		dao.updateRecipe(TestUtils.getChangedRecipeExample2());

		assertEquals(TestUtils.EXAMPLE_3_TITLE,
				dao.getRecipe(TestUtils.EXAMPLE_2_ID).getRecipeTitle());
	}

	@Test(expected = IllegalArgumentException.class) public void testDatabase_deleteRecipe() {
		assertEquals(TestUtils.EXAMPLE_2_TITLE,
				dao.getRecipe(TestUtils.EXAMPLE_2_ID).getRecipeTitle());

		dao.deleteRecipe(TestUtils.EXAMPLE_2_ID);

		// Should throw exception.
		dao.getRecipe(TestUtils.EXAMPLE_2_ID);
	}
}
