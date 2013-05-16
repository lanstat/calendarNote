package dev.androidbasico.calendarnotes.view;

import java.util.ArrayList;
import java.util.HashMap;

import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.R.id;
import dev.androidbasico.calendarnotes.R.layout;
import dev.androidbasico.calendarnotes.controllers.Utils;
import dev.androidbasico.calendarnotes.models.Note;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter{
	private Activity activity;
    private ArrayList<Note> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
 
    public LazyAdapter(Activity a, ArrayList<Note> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        Note note = data.get(position);
 
        title.setText(note.title);
        artist.setText(note.subject);
        duration.setText(Utils.int2date(note.date));
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}
