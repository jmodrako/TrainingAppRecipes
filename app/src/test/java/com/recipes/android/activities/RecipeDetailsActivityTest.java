package com.recipes.android.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipes.BuildConfig;
import com.recipes.R;
import com.recipes.RecipeApplicationTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Michal Radtke on 2015-06-29.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = RecipeApplicationTest.class, constants = BuildConfig.class,
        emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class RecipeDetailsActivityTest {

    private RecipeDetailsActivity sut;

    @Before
    public void setup() throws Exception {
        sut = Robolectric.buildActivity(RecipeDetailsActivity.class)
                .withIntent(prepareIntentForActivity()).create().get();
    }

    @Test
    public void checkLayout() {
        TextView tvTitle = (TextView) sut.findViewById(R.id.activity_recipe_details_tv_title);
        assertNotNull(tvTitle);
        assertEquals(tvTitle.getText().toString(), RecipeApplicationTest.EXAMPLE_TITLE);

        TextView tvSubtitle = (TextView) sut.findViewById(R.id.activity_recipe_details_tv_subtitle);
        assertNotNull(tvSubtitle);
        assertEquals(tvSubtitle.getText().toString(), RecipeApplicationTest.EXAMPLE_SUBTITLE);

        TextView tvDescription =
                (TextView) sut.findViewById(R.id.activity_recipe_details_tv_description);
        assertNotNull(tvDescription);
        assertEquals(tvDescription.getText().toString(), RecipeApplicationTest.EXAMPLE_DESCRIPTION);

        ImageView ivImage = (ImageView) sut.findViewById(R.id.activity_recipe_details_iv_image);
        assertNotNull(ivImage);
    }

    @Test
    public void testActionBar() {
        ActionBar actionBar = sut.getActionBar();
        assertNotNull(actionBar);
        assertNull(actionBar.getTitle());
        final int displayOptions = actionBar.getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
        assertTrue((displayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0);
        assertFalse((displayOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0);
    }

    private Intent prepareIntentForActivity() {
        return new Intent()
                .putExtra(MainActivityTest.EXTRA_RECIPE_TITLE, RecipeApplicationTest.EXAMPLE_TITLE)
                .putExtra(MainActivityTest.EXTRA_RECIPE_DESCRIPTION,
                        RecipeApplicationTest.EXAMPLE_DESCRIPTION)
                .putExtra(MainActivityTest.EXTRA_RECIPE_SUBTITLE,
                        RecipeApplicationTest.EXAMPLE_SUBTITLE)
                .putExtra(MainActivityTest.EXTRA_RECIPE_IMAGE_URL,
                        RecipeApplicationTest.EXAMPLE_IMAGE_URL);
    }
}
