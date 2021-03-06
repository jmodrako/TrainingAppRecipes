package com.recipes.android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.recipes.R;
import com.recipes.android.activity.MainFragmentActivity;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecipeDescriptionFragment extends Fragment {

	@InjectView(R.id.activity_recipe_details_tv_description) TextView description;
	@InjectView(R.id.activity_recipe_details_tv_title) TextView title;
	@InjectView(R.id.activity_recipe_details_tv_subtitle) TextView subtitle;
	@InjectView(R.id.activity_recipe_details_iv_image) ImageView image;

	private Intent shareIntent;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState != null) {
			updateDescription(savedInstanceState.getString(MainFragmentActivity.RECIPE_TITLE),
					savedInstanceState.getString(MainFragmentActivity.RECIPE_SUBTITLE),
					savedInstanceState.getString(MainFragmentActivity.RECIPE_DESCRIPTION),
					savedInstanceState.getString(MainFragmentActivity.RECIPE_IMAGE_URL));
			setShareButton(savedInstanceState.getString(
					MainFragmentActivity.RECIPE_TITLE));
		}
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.findItem(R.id.action_share).setVisible(true);
		MenuItem item = menu.findItem(R.id.action_share);
		ShareActionProvider shareActionProvider =
				(ShareActionProvider) item.getActionProvider();
		if (shareActionProvider != null) {
			shareActionProvider.setShareIntent(shareIntent);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		if (args != null) {
			updateDescription(args.getString(MainFragmentActivity.RECIPE_TITLE),
					args.getString(MainFragmentActivity.RECIPE_SUBTITLE),
					args.getString(MainFragmentActivity.RECIPE_DESCRIPTION),
					args.getString(MainFragmentActivity.RECIPE_IMAGE_URL));
			setShareButton(args.getString(
					MainFragmentActivity.RECIPE_TITLE));
		}
	}

	@Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recipe_description,
				container, false);
		ButterKnife.inject(this, view);
		return view;
	}

	private void updateDescription(String title, String subTitle, String description, String url) {
		this.title.setText(title);
		subtitle.setText(subTitle);
		this.description.setText(description);
		Picasso.with(getActivity().getBaseContext()).load(url).into(image);
	}

	private void setShareButton(String messageToShare) {
		shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, messageToShare);
	}
}
