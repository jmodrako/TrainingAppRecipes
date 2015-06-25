package com.recipes;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.models.Recipe;
import com.recipes.dependency_injection.interfaces.DaggerRecipeDaoLayer;
import com.recipes.dependency_injection.interfaces.RecipeDaoLayer;
import com.recipes.dependency_injection.modules.RecipeModule;

/**
 * Created by michal.radtke@gmail.com on 2015-06-16.
 */
public class RecipeApplication extends Application {

    protected RecipeDaoLayer recipeDaoLayer;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());

        prepareDependencies();
    }

    protected void prepareDependencies(){
        recipeDaoLayer = DaggerRecipeDaoLayer.builder().recipeModule(new RecipeModule(this)).build();
    }

    /**
     * This method is responsible for image loader initializing with pre-defined config.
     * This config sets thread priority, max number of cached files and size of cache (in this case it is 50mb).
     *
     * @param context indicates in which context image loader will be using.
     */
    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheExtraOptions(480, 800, null)
                .diskCacheFileCount(100)
                .diskCacheSize(50 * 1024 * 1024)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }

    public IRecipeDao<Recipe> getRecipeDao() {
        return recipeDaoLayer.recipeDao();
    }
}
