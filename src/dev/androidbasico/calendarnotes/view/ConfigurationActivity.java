package dev.androidbasico.calendarnotes.view;

import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.R.layout;
import dev.androidbasico.calendarnotes.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ConfigurationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuration, menu);
		return true;
	}

}
