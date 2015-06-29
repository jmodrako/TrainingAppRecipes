package com.recipes;

import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.models.Recipe;
import com.recipes.dependency_injection.interfaces.RecipeDaoLayer;

import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Michal Radtke on 2015-06-25.
 */
public class RecipeApplicationTest extends RecipeApplication {

    public static final int EXAMPLE_ID = 1;
    public static final String EXAMPLE_TITLE = "Deep fry Fish";
    public static final String EXAMPLE_SUBTITLE = "Created by Misiu on 29.06.2015";
    public static final String EXAMPLE_IMAGE_URL = "http://img.com";
    public static final String EXAMPLE_DESCRIPTION = "The Fish deep fried in sunflower oil";

    IRecipeDao recipeDao;

    @Override
    protected void prepareDependencies() {
        recipeDaoLayer = mock(RecipeDaoLayer.class);
        recipeDao = mock(IRecipeDao.class);

        when(recipeDao.getRecipe(Mockito.anyInt())).thenReturn(prepareRecipe());
        when(recipeDaoLayer.recipeDao()).thenReturn(recipeDao);
    }

    private Recipe prepareRecipe(){
        Recipe recipe = new Recipe();
        recipe.setRecipeId(EXAMPLE_ID);
        recipe.setRecipeTitle(EXAMPLE_TITLE);
        recipe.setRecipeDescription(EXAMPLE_DESCRIPTION);
        recipe.setRecipeImageUrl(EXAMPLE_IMAGE_URL);
        recipe.setRecipeSubtitle(EXAMPLE_SUBTITLE);

        return recipe;
    }
}
