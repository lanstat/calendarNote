package dev.androidbasico.calendarnotes.view;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import dev.androidbasico.calendarnotes.R;
import dev.androidbasico.calendarnotes.R.id;
import dev.androidbasico.calendarnotes.R.layout;
import dev.androidbasico.calendarnotes.controllers.NotesDataSource;
import dev.androidbasico.calendarnotes.controllers.Servicio;
import dev.androidbasico.calendarnotes.models.DataBase;
import dev.androidbasico.calendarnotes.models.Note;
import dev.androidbasico.calendarnotes.view.MyHorizontalScrollView.SizeCallback;

public class ListNoteActivity extends Activity implements OnClickListener, OnItemClickListener{
    MyHorizontalScrollView scrollView;
    View menu;
    View app;
    View submenu;
    View search;
    ImageView btnSlide;
    boolean menuOut = false;
    Handler handler = new Handler();
    int btnWidth;
    FrameLayout frames;
    boolean isSubMenuOrderByVisible = false;
    boolean isSubMenuSearchVisible = false;
    ClickListenerForScrolling scrolling;
    
    ArrayList<Note> notesList;
    
    public static NotesDataSource controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.activity_list_notes, null);
        setContentView(scrollView);

        menu = inflater.inflate(R.layout.list_notes_menu, null);
        app = inflater.inflate(R.layout.list_notes_content, null);
        submenu = inflater.inflate(R.layout.list_notes_submenu_orderby, null);
        search = inflater.inflate(R.layout.list_notes_submenu_search, null);
        
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);

        ListView listView = (ListView) app.findViewById(R.id.list);
        
        controller = new NotesDataSource(this);
        notesList = controller.listNotes("");
        
        LazyAdapter adapter=new LazyAdapter(this, notesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        
        menu.findViewById(R.id.new_note).setOnClickListener(this);
        menu.findViewById(R.id.acercade).setOnClickListener(this);
        menu.findViewById(R.id.configuracion).setOnClickListener(this);

        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
        
        scrolling = new ClickListenerForScrolling(scrollView, menu);
        btnSlide.setOnClickListener(scrolling);
        
        ((ImageView)tabBar.findViewById(R.id.BtnSubMenu)).setOnClickListener(this);
        ((ImageView)tabBar.findViewById(R.id.BtnSearch)).setOnClickListener(this);
        frames = (FrameLayout)app.findViewById(R.id.frames);

        final View[] children = new View[] { menu, app };

        int scrollToViewIdx = 1;
        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
        
        startService(new Intent(this, Servicio.class));
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ListView listView = (ListView) app.findViewById(R.id.list);
		notesList = controller.listNotes("");
        
        LazyAdapter adapter=new LazyAdapter(this, notesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
	}

	static class ClickListenerForScrolling implements OnClickListener {
        HorizontalScrollView scrollView;
        View menu;
        boolean menuOut = false;

        public ClickListenerForScrolling(HorizontalScrollView scrollView, View menu) {
            super();
            this.scrollView = scrollView;
            this.menu = menu;
        }

        @Override
        public void onClick(View v) {

            int menuWidth = menu.getMeasuredWidth();
            menu.setVisibility(View.VISIBLE);

            if (!menuOut) {
                int left = 0;
                scrollView.smoothScrollTo(left, 0);
            } else {
                int left = menuWidth;
                scrollView.smoothScrollTo(left, 0);
            }
            menuOut = !menuOut;
        }
    }

    
    static class SizeCallbackForMenu implements SizeCallback {
        int btnWidth;
        View btnSlide;

        public SizeCallbackForMenu(View btnSlide) {
            super();
            this.btnSlide = btnSlide;
        }

        @Override
        public void onGlobalLayout() {
            btnWidth = btnSlide.getMeasuredWidth();
            System.out.println("btnWidth=" + btnWidth);
        }

        @Override
        public void getViewSize(int idx, int w, int h, int[] dims) {
            dims[0] = w;
            dims[1] = h;
            final int menuIdx = 0;
            if (idx == menuIdx) {
                dims[0] = w - btnWidth;
            }
        }
    }


	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.BtnSubMenu:
			if(isSubMenuSearchVisible){
				frames.removeView(search);
				isSubMenuOrderByVisible = false;
			}
			if(!isSubMenuOrderByVisible){
				frames.addView(submenu);
				isSubMenuOrderByVisible = true;
			}else{
				frames.removeView(submenu);
				isSubMenuOrderByVisible = false;
			}
			break;
		case R.id.BtnSearch:
			if(isSubMenuOrderByVisible){
				frames.removeView(submenu);
				isSubMenuOrderByVisible = false;
			}
			if(!isSubMenuSearchVisible){
				frames.addView(search);
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null){
				    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
				}
				isSubMenuSearchVisible = true;
			}else{
				frames.removeView(search);
				isSubMenuSearchVisible = false;
			}
			break;
		case R.id.new_note:
			scrolling.onClick(null);
			intent = new Intent(this, NewNoteActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, DetailNoteActivity.class);
		intent.putExtra("note", notesList.get(arg2));
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		controller.close();
	}
	
	

}
