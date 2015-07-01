package com.recipes.android.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipes.BuildConfig;
import com.recipes.R;
import com.recipes.RecipeApplicationTest;
import com.recipes.TestUtils;

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
 * Tests for {@link com.recipes.android.activities.RecipeDetailsActivity}
 * Created by michal.radtke@mobica.com on 2015-06-29.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = RecipeApplicationTest.class, constants = BuildConfig.class,
        emulateSdk = 21, manifest = "com/recipes/app/src/main/AndroidManifest.xml")
public class RecipeDetailsActivityTest {

    private static final String EXTRA_RECIPE_DESCRIPTION = "extra_recipe_description";
    private static final String EXTRA_RECIPE_TITLE = "extra_recipe_title";
    private static final String EXTRA_RECIPE_SUBTITLE = "extra_recipe_subtitle";
    private static final String EXTRA_RECIPE_IMAGE_URL = "extra_recipe_image_url";

    private RecipeDetailsActivity sut;

    @Before
    public void setup() throws Exception {
        sut = Robolectric.buildActivity(RecipeDetailsActivity.class).withIntent(
                prepareIntentForActivity()).create().get();
    }

    @Test
    public void checkLayout() {
        TextView tvTitle = (TextView) sut.findViewById(R.id.activity_recipe_details_tv_title);
        assertNotNull(tvTitle);
        assertEquals(tvTitle.getText().toString(), TestUtils.EXAMPLE_1_TITLE);

        TextView tvSubtitle = (TextView) sut.findViewById(R.id.activity_recipe_details_tv_subtitle);
        assertNotNull(tvSubtitle);
        assertEquals(tvSubtitle.getText().toString(), TestUtils.EXAMPLE_1_SUBTITLE);

        TextView tvDescription =
                (TextView) sut.findViewById(R.id.activity_recipe_details_tv_description);
        assertNotNull(tvDescription);
        assertEquals(tvDescription.getText().toString(), TestUtils.EXAMPLE_1_DESCRIPTION);

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

    /**
     * This method prepares extras for tested activity.
     *
     * @return preapred intent with extras.
     */
    private Intent prepareIntentForActivity() {
        return new Intent()
                .putExtra(EXTRA_RECIPE_TITLE, TestUtils.EXAMPLE_1_TITLE)
                .putExtra(EXTRA_RECIPE_DESCRIPTION, TestUtils.EXAMPLE_1_DESCRIPTION)
                .putExtra(EXTRA_RECIPE_SUBTITLE, TestUtils.EXAMPLE_1_SUBTITLE)
                .putExtra(EXTRA_RECIPE_IMAGE_URL, TestUtils.EXAMPLE_1_IMAGE_URL);
    }
}
