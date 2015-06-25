package com.recipes;

import com.recipes.dependency_injection.interfaces.DaggerRecipeDaoLayer;
import com.recipes.dependency_injection.modules.RecipeModule;

import static org.mockito.Mockito.mock;

/**
 * Created by Michal Radtke on 2015-06-25.
 */
public class RecipeApplicationTest extends RecipeApplication {

    @Override
    protected void prepareDependencies() {
        recipeDaoLayer =
                mock(DaggerRecipeDaoLayer.builder().recipeModule(new RecipeModule(this)).build()
                        .getClass());
    }
}
