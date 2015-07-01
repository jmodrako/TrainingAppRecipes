package com.recipes.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.recipes.R;
import com.recipes.data.models.Recipe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//In AA: @EBean
public class RecipesListAdapter extends BaseAdapter {

    private List<Recipe> recipesList;
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public RecipesListAdapter(Context context) {
        this.context = context;

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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
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

    @Override
    public int getCount() {
        int result = 0;
        if (recipesList != null) {
            result = recipesList.size();
        }
        return result;
    }

    @Override
    public Recipe getItem(int position) {
        Recipe result = null;
        if (recipesList != null) {
            result = recipesList.get(position);
        }
        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    static class ViewHolder {
        @InjectView(R.id.adapter_recipes_list_tv_title)
        TextView tvTitle;

        @InjectView(R.id.adapter_recipes_list_tv_subtitle)
        TextView tvSubtitle;

        @InjectView(R.id.adapter_recipes_list_iv_thumbnail)
        ImageView ivThumbnail;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
