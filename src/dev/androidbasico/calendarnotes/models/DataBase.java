package dev.androidbasico.calendarnotes.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper{
	public static final String DB_NAME = "dbnotes.db";
	public static final int DB_VERSION = 2;
	public static final String TB_NOTE_NAME = "notes";
	
	public DataBase(Context context){
		super(context, DB_NAME, null, DB_VERSION );
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+TB_NOTE_NAME+"("+Note.ID+" integer primary key autoincrement, "+Note.TITLE+" text"
				+", "+Note.SUBJECT+" text, "+Note.DATE+" int not null, "+Note.TIME+" int not null, "+Note.STATE+" int not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TB_NOTE_NAME);
		onCreate(db);
	}

}
