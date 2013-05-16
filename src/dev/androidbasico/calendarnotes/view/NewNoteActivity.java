package dev.androidbasico.calendarnotes.view;

import java.util.Calendar;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.controllers.NotesDataSource;
import dev.androidbasico.calendarnotes.controllers.Utils;
import dev.androidbasico.calendarnotes.models.Note;

public class NewNoteActivity extends Activity implements OnClickListener{
	DatePickerDialog DateDialog = null;
	TimePickerDialog TimeDialog = null; 
	Button BtnDate = null, BtnTime = null;
	NotesDataSource controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		init();
	}
	
	private void init(){
		Spinner spn = (Spinner)findViewById(R.id.SpnReminder);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.reminders, android.R.layout.simple_spinner_item );
		spn.setAdapter(adapter);
		Calendar dtTxt = Calendar.getInstance();
		BtnDate = (Button)findViewById(R.id.BtnDate);
		BtnDate.setOnClickListener(this);
		BtnDate.setText(dtTxt.get(Calendar.DAY_OF_MONTH)+"/"+dtTxt.get(Calendar.MONTH)+"/"+dtTxt.get(Calendar.YEAR));
		BtnTime = (Button)findViewById(R.id.BtnTime);
		BtnTime.setOnClickListener(this);
		BtnTime.setText(dtTxt.get(Calendar.HOUR_OF_DAY)+":"+dtTxt.get(Calendar.MINUTE));
		findViewById(R.id.BtnDelete).setOnClickListener(this);
		findViewById(R.id.BtnSlideBack).setOnClickListener(this);
		controller = new NotesDataSource(this);
	}

	@Override
	public void onClick(View arg0) {
		Calendar dtTxt = Calendar.getInstance();
		switch (arg0.getId()) {
		case R.id.BtnDate:
	        if(DateDialog == null)
	        	DateDialog = new DatePickerDialog(arg0.getContext(),new PickDate(),dtTxt.get(Calendar.YEAR),dtTxt.get(Calendar.MONTH),dtTxt.get(Calendar.DAY_OF_MONTH));
	        DateDialog.show();
		break;
		case R.id.BtnTime:
			if(TimeDialog == null)
				TimeDialog = new TimePickerDialog(arg0.getContext(), new PickTime(),dtTxt.get(Calendar.HOUR_OF_DAY), dtTxt.get(Calendar.MINUTE), true);
			TimeDialog.show();
			break;
		case R.id.BtnDelete:
			Note note = new Note();
			note.title = ((EditText)findViewById(R.id.editText1)).getText().toString();
			note.subject = ((EditText)findViewById(R.id.editText2)).getText().toString();
			note.date = Utils.date2int(BtnDate.getText().toString());
			note.time = Utils.time2int(BtnTime.getText().toString());
			controller.insertNote(note);
			this.finish();
			break;
		case R.id.BtnSlideBack:
			this.finish();
			break;
		}
	}
	
	private class PickDate implements DatePickerDialog.OnDateSetListener {

	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        view.updateDate(year, monthOfYear, dayOfMonth);
	        if((monthOfYear+1)<10)
	        	BtnDate.setText(dayOfMonth+"/0"+(monthOfYear+1)+"/"+year);
	        else
	        	BtnDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
	        DateDialog.dismiss();
	    }
	    
	}
	
	private class PickTime implements TimePickerDialog.OnTimeSetListener{

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if(minute+1<10)
				BtnTime.setText(hourOfDay+":0"+(minute+1));
			else
				BtnTime.setText(hourOfDay+":"+(minute+1));
			TimeDialog.dismiss();
		}
		
	}
}
