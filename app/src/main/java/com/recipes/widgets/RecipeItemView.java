package com.recipes.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.recipes.R;
import com.recipes.models.RecipeData;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_recipe_item)
public class RecipeItemView extends RelativeLayout {

    @ViewById
    TextView recipeItemTvTitle;

    @ViewById
    TextView recipeItemTvSubtitle;

    @ViewById
    ImageView recipeItemIvThumbnail;

    private DisplayImageOptions options;
    private Context context;

    public RecipeItemView(Context context) {
        super(context);
        this.context = context;
        options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.loading)
                //.showImageForEmptyUri(R.drawable.no_image)
                //.showImageOnFail(R.drawable.error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .build();
    }

    public void bind(RecipeData recipeData) {
        Typeface typefaceTitle = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        recipeItemTvTitle.setText(recipeData.getRecipeTitle());
        recipeItemTvTitle.setTypeface(typefaceTitle);

        Typeface typefaceSubtitle = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Light.ttf");
        recipeItemTvSubtitle.setText(recipeData.getRecipeSubtitle());
        recipeItemTvSubtitle.setTypeface(typefaceSubtitle);
        ImageLoader.getInstance().displayImage(recipeData.getRecipeImageUrl(), recipeItemIvThumbnail, options);
    }
}
