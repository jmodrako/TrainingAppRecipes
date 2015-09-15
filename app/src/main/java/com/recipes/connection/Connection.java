package com.recipes.connection;

import com.recipes.connection.interfaces.IRecipeApi;
import com.recipes.data.Settings;

import android.content.Context;

import retrofit.RestAdapter;

/**
 * Jacek Modrakowski
 * modrakowski.pl
 * 15/09/2015.
 */
public class Connection {

	public static IRecipeApi createApi(Context context) {
		final String url = Settings.getEndpointAddress(context);
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.setEndpoint(url)
				.build();

		return restAdapter.create(IRecipeApi.class);
	}
}
