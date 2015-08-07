package com.recipes.android.activity;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.recipes.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation tests for {@link com.recipes.android.activity.MainActivity}
 * Created by Michal Radtke on 2015-06-30.
 */
@SmallTest
public class MainActivityEspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Context context;

    public MainActivityEspressoTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
        this.context = getInstrumentation().getTargetContext();
    }

    public void testListViewContent() {
        onView(withId(R.layout.activity_recipe_details)).check(doesNotExist());
        onData(anything()).inAdapterView(withId(R.id.activity_main_lv_recipes_list)).atPosition(0)
                .perform(click());
    }

    public void testActionBar_settingOption() {
        openActionBarOverflowOrOptionsMenu(context);
        onView(withText(context.getString(R.string.action_settings))).perform(click());
        onView(withText(context.getString(R.string.settings_clicked_message))).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));
    }

    public void testActionBar_refreshOption() {
        onView(withId(R.id.action_refresh)).perform(click());
    }
}
