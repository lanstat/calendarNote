package dev.androidbasico.calendarnotes.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.models.Note;
import dev.androidbasico.calendarnotes.view.DetailNoteActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

public class Servicio extends Service {
	private static Timer timer;
	private mainTask tarea;
	private static NotificationCompat.Builder notif;
	private NotesDataSource controller;
	private Calendar calendar;
	private int mNotificationId = 1;

    public IBinder onBind(Intent arg0) 
    {
          return null;
    }

    public void onCreate() 
    {
          super.onCreate();
          timer = new Timer();
          tarea = new mainTask();
          notif = new NotificationCompat.Builder(getBaseContext());
          notif.setAutoCancel(true);
          
          calendar = Calendar.getInstance();
          controller = new NotesDataSource(getBaseContext());
          startService();
           
    }

    private void startService()
    {           
        timer.scheduleAtFixedRate(tarea, 0, 60000);
    }

    private class mainTask extends TimerTask
    { 
        public void run() 
        {
            toastHandler.sendEmptyMessage(0);
        }
    }    

    public void onDestroy() 
    {
          super.onDestroy();
          timer.cancel();
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
        	int date = calendar.get(Calendar.DAY_OF_MONTH)*1000000+(calendar.get(Calendar.MONTH)+1)*10000+calendar.get(Calendar.YEAR);
        	int time = calendar.get(Calendar.HOUR_OF_DAY)*100+calendar.get(Calendar.MINUTE);
        	ArrayList<Note> notes = controller.listNotesWhereDateAndTime(date, time);
        	for(Note note: notes)
        		publishNotification(note);
        }
    }; 
    
    public void publishNotification(Note note){
    	controller.deleteNote(note);
    	notif.setSmallIcon(R.drawable.ic_launcher);
        notif.setContentTitle(note.title);
        notif.setContentText(note.subject);
        Intent intent = new Intent(getBaseContext(), DetailNoteActivity.class);
        intent.putExtra("note", note);
    	PendingIntent result = PendingIntent.getActivity(getBaseContext(), mNotificationId, intent,PendingIntent.FLAG_UPDATE_CURRENT);
    	notif.setContentIntent(result);
    	
    	NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	mNotifyMgr.notify(mNotificationId, notif.build());
    	mNotificationId++;
    }

}
