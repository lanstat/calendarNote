package dev.androidbasico.calendarnotes.models;

import java.io.Serializable;

public class Note implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8870666082928141595L;
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String SUBJECT = "subject";
	public static final String TIME = "time";
	public static final String DATE = "date";
	public static final String STATE = "state";
	
	
	public int id;
	public String title, subject;
	public int time, date;
	public boolean state;

	public static final String[] ALL = {ID, TITLE, SUBJECT, TIME, DATE, STATE}; 
}
