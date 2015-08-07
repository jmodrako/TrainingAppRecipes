package com.recipes;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.recipes.data.interfaces.IRecipeDao;
import com.recipes.data.model.Recipe;
import com.recipes.internal.interfaces.DaggerRecipeDaoLayer;
import com.recipes.internal.module.RecipeModule;

public class RecipeApplication extends Application {

	protected IRecipeDao recipeDao;

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
		prepareDependencies();
	}

	protected void prepareDependencies() {
		recipeDao = DaggerRecipeDaoLayer.builder().
				recipeModule(new RecipeModule(this)).build().recipeDao();
	}

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
		return recipeDao;
	}
}
