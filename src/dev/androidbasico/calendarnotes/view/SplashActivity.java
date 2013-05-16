package dev.androidbasico.calendarnotes.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.controllers.Servicio;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
            public void run() {
                    startActivity(new Intent(SplashActivity.this, ListNoteActivity.class));
                    finish();
            }
		}, 5000);
		startService(new Intent(this, Servicio.class));
	}

}
