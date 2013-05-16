package dev.androidbasico.calendarnotes.controllers;

import java.util.ArrayList;

import dev.androidbasico.calendarnotes.models.DataBase;
import dev.androidbasico.calendarnotes.models.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotesDataSource {
	DataBase mDatabase;
	SQLiteDatabase db;
	
	public NotesDataSource(Context context){
		mDatabase = new DataBase(context);
		db = mDatabase.getWritableDatabase();
	}
	
	public void insertNote(Note note){
		ContentValues values = new ContentValues();
		values.put(Note.TITLE, note.title);
		values.put(Note.SUBJECT, note.subject);
		values.put(Note.DATE, note.date);
		values.put(Note.TIME, note.time);
		values.put(Note.STATE, true);
		db.insert(DataBase.TB_NOTE_NAME, null, values);
	}
	
	public ArrayList<Note> lisNotesWhereTitleLike(String title){
		return listNotes(Note.TITLE+" LIKE %"+title+"%");
	}
	
	public ArrayList<Note> lisNotesOrderByDate(){
		return listNotes("");
	}
	
	public ArrayList<Note> listNotesWhereDateAndTime(int date, int time){
		return listNotes(Note.DATE+" <= "+date+" AND "+Note.TIME+" <= "+time);
	}
	
	public ArrayList<Note> listNotes(String criteria){
		ArrayList<Note> notes = new ArrayList<Note>();
		String query = Note.STATE+"=1";
		if(criteria.length()>1)
			query = criteria + " AND " + query;
		Cursor cursor = db.query(DataBase.TB_NOTE_NAME, Note.ALL, query, null, Note.DATE, null, Note.DATE+" desc");
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			notes.add(cursor2note(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}
	
	public void deleteNote(Note note){
		//db.delete(DataBase.TB_NOTE_NAME, Note.ID+"="+note.id, null);
		ContentValues values = new ContentValues();
		values.put(Note.STATE, "0");
		db.update(DataBase.TB_NOTE_NAME, values, Note.ID+"="+note.id, null);
	}
	
	private Note cursor2note(Cursor cursor){
		Note note = new Note();
		note.id = cursor.getInt(0);
		note.title = cursor.getString(1);
		note.subject = cursor.getString(2);
		note.date = cursor.getInt(4);
		note.time = cursor.getInt(3);
		note.state = cursor.getInt(5)==1? true: false;
		
		return note;
	}
	
	public void close(){
		db.close();
	}
}
