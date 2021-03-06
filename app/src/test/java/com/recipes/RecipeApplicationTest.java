package com.recipes;

import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;
import com.recipes.internal.interfaces.RecipeDaoLayer;
import com.recipes.util.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is an application class for tests.
 * Created by michal.radtke@mobica.com on 2015-06-25.
 */
public class RecipeApplicationTest extends RecipeApplication {

	@Override
	protected void prepareDependencies() {
		recipeDao = new MockDB();
	}

	/**
	 * This class mocks recipe database access object interface. In tests we don not have
	 * permissions to working on real database, that we have to use list.
	 */
	private class MockDB implements IRecipeDao<Recipe> {

		private List<Recipe> data;

		public MockDB() {
			data = new ArrayList<Recipe>();
		}

		@Override
		public List<Recipe> getAllRecipes() {
			return data;
		}

		@Override
		public Recipe getRecipe(int recipeId) {
			for (Recipe recipe : data) {
				if (recipe.getRecipeId() == recipeId) {
					return recipe;
				}
			}

			throw new IllegalArgumentException("No recipe in storage with given id " + recipeId);
		}

		@Override
		public void updateRecipe(Recipe recipe) {
			Parameters.checkNotNull(recipe);
			data.set(recipe.getRecipeId(), recipe);
		}

		@Override
		public void deleteRecipe(int recipeId) {
			data.remove(recipeId);
		}

		@Override
		public void insertRecipe(Recipe recipe) {
			Parameters.checkNotNull(recipe);
			data.add(recipe.getRecipeId(), recipe);
		}

		@Override
		public void insertAllRecipes(List<Recipe> recipes) {

		}

		@Override
		public long getCount() {
			return data.size();
		}
	}
}
