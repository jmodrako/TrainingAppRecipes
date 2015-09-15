package com.recipes.android.activity;

import com.recipes.R;
import com.recipes.data.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Jacek Modrakowski
 * modrakowski.pl
 * 15/09/2015.
 */
public class SettingsActivity extends Activity {

	private static final Pattern IP_ADDRESS
			= Pattern.compile(
			"((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
					+ "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
					+ "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
					+ "|[1-9][0-9]|[0-9]))");

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ButterKnife.inject(this);
	}

	@OnClick(R.id.settingsSave) void onSaveClick() {
		final EditText et = (EditText) findViewById(R.id.settingsEndpointAddress);
		final String address = et.getText().toString();
		if (TextUtils.isEmpty(address)) {
			Toast.makeText(this, "Podaj adres!", Toast.LENGTH_SHORT).show();
		} else if (!validateAddress(address)) {
			Toast.makeText(this, "Podaj prawidłowy adres!", Toast.LENGTH_SHORT).show();
		} else {
			Settings.saveEndpointAddress(this, address);
			Toast.makeText(this, "Zapisano adres!", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private boolean validateAddress(String address) {
		return IP_ADDRESS.matcher(address).matches();
	}
}
