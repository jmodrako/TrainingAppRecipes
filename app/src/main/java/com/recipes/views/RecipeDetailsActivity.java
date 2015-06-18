package com.recipes.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.recipes.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity(R.layout.activity_recipe_details)
public class RecipeDetailsActivity extends Activity {

    @Extra
    String extraRecipeDescription;

    @Extra
    String extraRecipeTitle;

    @Extra
    String extraRecipeSubtitle;

    @Extra
    String extraRecipeImageUrl;

    @ViewById
    TextView activityRecipeDetailsTvDescription;

    @ViewById
    TextView activityRecipeDetailsTvTitle;

    @ViewById
    TextView activityRecipeDetailsTvSubtitle;

    @ViewById
    ImageView activityRecipeDetailsIvImage;

    @StringRes
    String settingClickedMessage;

    private Intent shareIntent;

    @AfterViews
    void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowHomeEnabled(true);
        }
        setTextViewsFonts();
        setTextViewsTexts();
        setShareButton(activityRecipeDetailsTvTitle.getText().toString());
        Picasso.with(this)
                .load(extraRecipeImageUrl)
                .into(activityRecipeDetailsIvImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar_recipe_details, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) item.getActionProvider();
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), settingClickedMessage, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTextViewsFonts() {
        Typeface typefaceTitle = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        activityRecipeDetailsTvTitle.setTypeface(typefaceTitle);

        Typeface typefaceSubtitle = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
        activityRecipeDetailsTvSubtitle.setTypeface(typefaceSubtitle);

        Typeface typefaceDescription = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        activityRecipeDetailsTvDescription.setTypeface(typefaceDescription);
    }

    private void setShareButton(String messageToShare) {
        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, messageToShare);
    }

    private void setTextViewsTexts() {
        activityRecipeDetailsTvDescription.setText(extraRecipeDescription);
        activityRecipeDetailsTvTitle.setText(extraRecipeTitle);
        activityRecipeDetailsTvSubtitle.setText(extraRecipeSubtitle);
    }
}
