package com.recipes.android.activities;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.recipes.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation tests for {@link com.recipes.android.activities.RecipeDetailsActivity}
 * Created by Michal Radtke on 2015-07-01.
 */
@SmallTest
public class RecipeDetailsActivityEspressoTest
        extends ActivityInstrumentationTestCase2<RecipeDetailsActivity> {

    private static final String EXTRA_RECIPE_DESCRIPTION = "extra_recipe_description";
    private static final String EXTRA_RECIPE_TITLE = "extra_recipe_title";
    private static final String EXTRA_RECIPE_SUBTITLE = "extra_recipe_subtitle";
    private static final String EXTRA_RECIPE_IMAGE_URL = "extra_recipe_image_url";
    private static final String EXAMPLE_TITLE = "Gazpacho";
    private static final String EXAMPLE_SUBTITLE = "Created by Misiu on 30.06.2015";
    private static final String EXAMPLE_IMAGE_URL =
            "http://www.msgdish.com/wp-content/uploads/2013/06/cold-gazpacho-with-garlic-croutons-in-glass-bowl-580x385.jpg";
    private static final String EXAMPLE_DESCRIPTION =
            "Delicious soup from tomatoes and some paprika. This dish is ideally for people who/'s suffer in a hot days.";

    private Context context;

    public RecipeDetailsActivityEspressoTest() {
        super(RecipeDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityIntent(prepareExtrasForActivity());
        getActivity();
        this.context = getInstrumentation().getTargetContext();
    }

    public void testView_textViewTitle() {
        onView(withId(R.id.activity_recipe_details_tv_title)).check(
                matches(withText(EXAMPLE_TITLE)));
    }

    public void testView_textViewSubtitle() {
        onView(withId(R.id.activity_recipe_details_tv_subtitle))
                .check(matches(withText(EXAMPLE_SUBTITLE)));
    }

    public void testView_textViewDescription() {
        onView(withId(R.id.activity_recipe_details_tv_description))
                .check(matches(withText(EXAMPLE_DESCRIPTION)));
    }

    public void testActionBar_settingOption() {
        openActionBarOverflowOrOptionsMenu(context);
        onView(withText(context.getString(R.string.action_settings))).perform(click());
        onView(withText(context.getString(R.string.settings_clicked_message))).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));
    }

    public void testActionBar_backButton() {
        pressBack();
    }

    /**
     * This method prepares extras for tested activity.
     *
     * @return prepared intent with extras.
     */
    private Intent prepareExtrasForActivity() {
        return new Intent()
                .putExtra(EXTRA_RECIPE_TITLE, EXAMPLE_TITLE)
                .putExtra(EXTRA_RECIPE_SUBTITLE, EXAMPLE_SUBTITLE)
                .putExtra(EXTRA_RECIPE_DESCRIPTION, EXAMPLE_DESCRIPTION)
                .putExtra(EXTRA_RECIPE_IMAGE_URL, EXAMPLE_IMAGE_URL);
    }
}
