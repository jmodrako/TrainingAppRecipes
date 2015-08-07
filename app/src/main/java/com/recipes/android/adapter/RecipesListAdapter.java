package com.recipes.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.recipes.R;
import com.recipes.data.model.Recipe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//In AA: @EBean
public class RecipesListAdapter extends BaseAdapter {

	private List<Recipe> recipesList;
	private LayoutInflater inflater;
	private DisplayImageOptions options;

	public RecipesListAdapter(Context context) {
		options = new DisplayImageOptions.Builder()
				//.showImageOnLoading(R.drawable.loading)
				//.showImageForEmptyUri(R.drawable.no_image)
				//.showImageOnFail(R.drawable.error)
				.resetViewBeforeLoading(true)
				.cacheOnDisk(true)
				.build();

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override public View getView(int position, View view, @NonNull ViewGroup parent) {
		ViewHolder viewHolder;
		if (view != null) {
			viewHolder = (ViewHolder) view.getTag();
		} else {
			view = inflater.inflate(R.layout.adapter_recipes_list, parent, false);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}

		viewHolder.tvTitle.setText(recipesList.get(position).getRecipeTitle());
		viewHolder.tvSubtitle.setText(recipesList.get(position).getRecipeSubtitle());
		ImageLoader.getInstance()
				.displayImage(recipesList.get(position).getRecipeImageUrl(), viewHolder.ivThumbnail,
						options);

		return view;
	}

	@Override public int getCount() {
		return recipesList != null ? recipesList.size() : 0;
	}

	@Override public Recipe getItem(int position) {
		return recipesList != null ? recipesList.get(position) : null;
	}

	@Override public long getItemId(int position) {
		return position;
	}

	public void setData(List<Recipe> recipesList) {
		this.recipesList = recipesList;
	}

	static class ViewHolder {
		@InjectView(R.id.adapter_recipes_list_tv_title) TextView tvTitle;
		@InjectView(R.id.adapter_recipes_list_tv_subtitle) TextView tvSubtitle;
		@InjectView(R.id.adapter_recipes_list_iv_thumbnail) ImageView ivThumbnail;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}
}
