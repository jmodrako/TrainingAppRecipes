package com.recipes.android.adapters;

import android.content.Context;
import android.graphics.Typeface;
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


public class FragmentMenuAdapter extends BaseAdapter {
    Context context;
    int layoutResourceId;
    private List<Recipe> recipesList;
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public FragmentMenuAdapter(Context context, int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .build();

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        int result = 0;
        if(recipesList != null){
            result = recipesList.size();
        }
        return result;
    }

    @Override
    public Recipe getItem(int position) {
        Recipe result = null;
        if(recipesList != null){
            result = recipesList.get(position);
        }
        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder(convertView,context);
            convertView.setTag(viewHolder);
        }

        viewHolder.tvTitle.setText(recipesList.get(position).getRecipeTitle());
        viewHolder.tvSubtitle.setText(recipesList.get(position).getRecipeSubtitle());
        ImageLoader.getInstance()
                .displayImage(recipesList.get(position).getRecipeImageUrl(),
                viewHolder.ivThumbnail,options);

        return convertView;
    }

    public void setData(List<Recipe> recipesList){
        this.recipesList = recipesList;
    }

    static class ViewHolder {
        @InjectView(R.id.adapter_recipes_list_tv_title)
        TextView tvTitle;

        @InjectView(R.id.adapter_recipes_list_tv_subtitle)
        TextView tvSubtitle;

        @InjectView(R.id.adapter_recipes_list_iv_thumbnail)
        ImageView ivThumbnail;

        public ViewHolder(View view, Context context) {
            ButterKnife.inject(this, view);
            Typeface typefaceTitle = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
            tvTitle.setTypeface(typefaceTitle);

            Typeface typefaceSubtitle = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Light.ttf");
            tvSubtitle.setTypeface(typefaceSubtitle);
        }
    }
}
