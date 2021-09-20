package com.example.eventreminder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.eventreminder.adapter.EventViewAdapter;
import com.example.eventreminder.dao.EventDao;
import com.example.eventreminder.database.CommonDatabase;
import com.example.eventreminder.database.EventDatabase;
import com.example.eventreminder.model.Event;
import com.example.eventreminder.model.Event.Severity;
import com.example.eventreminder.model.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static EventViewModel eventViewModel;
    public static EventViewAdapter eventViewAdapter;
    private List<Event> eventList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CommonDatabase.db = Room.databaseBuilder(getApplicationContext(),
                EventDatabase.class, "database-event").allowMainThreadQueries().build();

        eventList = updateUI();
        eventViewAdapter = new EventViewAdapter(eventList);

        RecyclerView recyclerView = findViewById(R.id.eventView);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(eventViewAdapter);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        final Observer<List<Event>> contentTextListObserver = eventTextList -> {
            eventList = updateUI();
            eventViewAdapter = new EventViewAdapter(updateUI());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(eventViewAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            eventViewAdapter.setOnEventClickListener(new EventViewAdapter.OnEventClickListener(){
                @Override
                public void onEventClick(int position) {
                    Toast.makeText(getApplicationContext(),"Position = "+position+"\n Event = "+eventList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
            eventViewAdapter.notifyDataSetChanged();
        };

        eventViewModel.getCurrentContent().observe(this, contentTextListObserver);

        eventViewAdapter.setOnEventClickListener(new EventViewAdapter.OnEventClickListener(){
            @Override
            public void onEventClick(int position) {
                Event event = eventList.get(position);
                Toast.makeText(getApplicationContext(),
                        event.getTitle() + "\n" + event.getEventTime(), Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Event> updateUI() {
        final EventDao eventDao = CommonDatabase.db.eventDao();
        List<Event> eventListDB = eventDao.getAll();
        return eventListDB;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    public void onMyMenuClick(MenuItem menuItem){
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
    }

}