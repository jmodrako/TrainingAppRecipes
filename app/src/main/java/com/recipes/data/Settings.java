package com.recipes.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Jacek Modrakowski
 * modrakowski.pl
 * 15/09/2015.
 */
public class Settings {

	private static final String SERVER_PORT = "5000";

	private static final String PREFS_NAME = "recipes.prefs";
	private static final String PREFS_ADDRESS_KEY = "recipes.prefs.address";

	private Settings() {
		// Nothing here.
	}

	public static void saveEndpointAddress(Context context, String address) {
		final SharedPreferences sp = context.getSharedPreferences(
				PREFS_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(PREFS_ADDRESS_KEY, "http://" + address + ":" + SERVER_PORT).apply();
	}

	public static String getEndpointAddress(Context context) {
		final SharedPreferences sp = context.getSharedPreferences(
				PREFS_NAME, Context.MODE_PRIVATE);
		return sp.getString(PREFS_ADDRESS_KEY, "127.0.0.1");
	}
}
