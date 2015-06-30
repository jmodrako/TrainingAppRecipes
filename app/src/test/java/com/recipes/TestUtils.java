package com.recipes;

import com.recipes.data.models.Recipe;

/**
 * Created by Michal Radtke on 2015-06-30.
 */
public class TestUtils {

    private TestUtils() {
    }

    public static final int EXAMPLE_1_ID = 0;
    public static final String EXAMPLE_1_TITLE = "Deep fry Fish";
    public static final String EXAMPLE_1_SUBTITLE = "Created by Misiu on 29.06.2015";
    public static final String EXAMPLE_1_IMAGE_URL = "http://img.com";
    public static final String EXAMPLE_1_DESCRIPTION = "The Fish deep fried in sunflower oil";

    public static final int EXAMPLE_2_ID = 1;
    public static final String EXAMPLE_2_TITLE = "Gaspaccio";
    public static final String EXAMPLE_2_SUBTITLE = "Created by Misiu on 30.06.2015";
    public static final String EXAMPLE_2_IMAGE_URL = "http://img.com/Gaspaccio";
    public static final String EXAMPLE_2_DESCRIPTION = "Nice dish";

    public static final String EXAMPLE_3_TITLE = "Chips and Fish";

    public static Recipe getRecipeExample1() {
        return prepareExampleRecipe(EXAMPLE_1_ID, EXAMPLE_1_TITLE, EXAMPLE_1_SUBTITLE,
                EXAMPLE_1_DESCRIPTION, EXAMPLE_1_IMAGE_URL);
    }

    public static Recipe getChangedRecipeExample2() {
        return prepareExampleRecipe(EXAMPLE_2_ID, EXAMPLE_3_TITLE, EXAMPLE_2_SUBTITLE,
                EXAMPLE_2_DESCRIPTION, EXAMPLE_2_IMAGE_URL);
    }

    public static Recipe getRecipeExample2() {
        return prepareExampleRecipe(EXAMPLE_2_ID, EXAMPLE_2_TITLE, EXAMPLE_2_SUBTITLE,
                EXAMPLE_2_DESCRIPTION, EXAMPLE_2_IMAGE_URL);
    }

    private static Recipe prepareExampleRecipe(int id, String title, String subtitle,
                                               String description, String imageUrl) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(id);
        recipe.setRecipeTitle(title);
        recipe.setRecipeDescription(subtitle);
        recipe.setRecipeImageUrl(description);
        recipe.setRecipeSubtitle(imageUrl);

        return recipe;
    }
}
