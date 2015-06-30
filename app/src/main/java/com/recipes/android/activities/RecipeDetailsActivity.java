package com.recipes.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.recipes.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class RecipeDetailsActivity extends Activity {

    public static final String EXTRA_RECIPE_DESCRIPTION = "extra_recipe_description";
    public static final String EXTRA_RECIPE_TITLE = "extra_recipe_title";
    public static final String EXTRA_RECIPE_SUBTITLE = "extra_recipe_subtitle";
    public static final String EXTRA_RECIPE_IMAGE_URL = "extra_recipe_image_url";

    @InjectView(R.id.activity_recipe_details_tv_description)
    TextView tvDescription;

    @InjectView(R.id.activity_recipe_details_tv_title)
    TextView tvTitle;

    @InjectView(R.id.activity_recipe_details_tv_subtitle)
    TextView tvSubtitle;

    @InjectView(R.id.activity_recipe_details_iv_image)
    ImageView ivImage;

    private Intent shareIntent;

    /*In AA:
    @AfterViews
    void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowHomeEnabled(true);
        }
        setTextViewsFonts();
        setTextViewsTexts();
        setShareButton(tvTitle.getText().toString());
        Picasso.with(this)
                .load(extraRecipeImageUrl)
                .into(ivImage);
    }
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.inject(this);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setTextViewsTexts(extras.getString(EXTRA_RECIPE_TITLE),
                    extras.getString(EXTRA_RECIPE_SUBTITLE),
                    extras.getString(EXTRA_RECIPE_DESCRIPTION));
            setShareButton(extras.getString(EXTRA_RECIPE_TITLE));
            Picasso.with(this)
                    .load(extras.getString(EXTRA_RECIPE_IMAGE_URL))
                    .into(ivImage);
        }
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
                Toast.makeText(getApplicationContext(),
                        getString(R.string.settings_clicked_message), Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void setShareButton(String messageToShare) {
        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, messageToShare);
    }

    private void setTextViewsTexts(String title, String subtitle, String description) {
        tvDescription.setText(description);
        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);
    }
}
