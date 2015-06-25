package com.recipes.android.views;

import android.content.Context;
import android.widget.RelativeLayout;


//@EViewGroup(R.layout.adapter_recipes_list)
public class RecipeItemView extends RelativeLayout {

    /*@ViewById
    TextView recipeItemTvTitle;

    @ViewById
    TextView recipeItemTvSubtitle;

    @ViewById
    ImageView recipeItemIvThumbnail;

    private DisplayImageOptions options;
    private Context context;*/

    public RecipeItemView(Context context) {
        super(context);
        /*this.context = context;
        options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.loading)
                //.showImageForEmptyUri(R.drawable.no_image)
                //.showImageOnFail(R.drawable.error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .build();*/
    }

   /* public void bind(Recipe recipe) {
        Typeface typefaceTitle = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        recipeItemTvTitle.setText(recipe.getRecipeTitle());
        recipeItemTvTitle.setTypeface(typefaceTitle);

        Typeface typefaceSubtitle = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Light.ttf");
        recipeItemTvSubtitle.setText(recipe.getRecipeSubtitle());
        recipeItemTvSubtitle.setTypeface(typefaceSubtitle);
        ImageLoader.getInstance().displayImage(recipe.getRecipeImageUrl(), recipeItemIvThumbnail, options);
    }*/
}
