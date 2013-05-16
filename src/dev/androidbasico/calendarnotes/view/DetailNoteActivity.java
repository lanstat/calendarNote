package dev.androidbasico.calendarnotes.view;

import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.controllers.Utils;
import dev.androidbasico.calendarnotes.models.Note;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DetailNoteActivity extends Activity implements OnClickListener{
	Note note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_note);
		findViewById(R.id.BtnSlideBack).setOnClickListener(this);
		findViewById(R.id.BtnDelete).setOnClickListener(this);
		init();
	}
	
	private void init(){
		Bundle b = getIntent().getExtras();
		note = (Note)b.get("note");
		((TextView)findViewById(R.id.TxtDate)).setText(Utils.int2date(note.date));
		((TextView)findViewById(R.id.txtTime)).setText(Utils.int2time(note.time));
		((TextView)findViewById(R.id.txtTitle)).setText(note.title);
		((TextView)findViewById(R.id.txtContent)).setText(note.subject);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BtnSlideBack:
			this.finish();
			break;
		case R.id.BtnDelete:
			ListNoteActivity.controller.deleteNote(note);
			this.finish();
			break;
		}
	}

	
}
