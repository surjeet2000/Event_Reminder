package com.example.eventreminder;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eventreminder.dao.EventDao;
import com.example.eventreminder.database.CommonDatabase;
import com.example.eventreminder.helper.SharedPreferencesHelper;
import com.example.eventreminder.model.Event;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout eventTitleInput;
    private TextInputLayout eventDescriptionInput;
    private TextInputLayout eventDateInput;
    private TextInputLayout eventTimeInput;
    private Spinner spinner;
    private Button button;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);

        eventTitleInput = findViewById(R.id.eventTitle);
        eventDescriptionInput = findViewById(R.id.eventDescription);
        eventDateInput = findViewById(R.id.eventDate);
        eventTimeInput = findViewById(R.id.eventTime);
        spinner = findViewById(R.id.severity);
        button = findViewById(R.id.button);

        event = new Event();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.severity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EventDao contentDao = CommonDatabase.db.eventDao();
                Editable eventTitle = eventTitleInput.getEditText().getText();
                Editable eventDescription = eventDescriptionInput.getEditText().getText();
                Editable eventDate = eventDateInput.getEditText().getText();
                Editable eventTime = eventTimeInput.getEditText().getText();

                int availableId = SharedPreferencesHelper.getAvailableId(CreateActivity.this);
                event.setId(availableId);
                event.setTitle(eventTitle.toString());
                event.setDescription(eventDescription.toString());
                event.setEventTime(eventTime.toString() + " at " + eventDate.toString());
                contentDao.insertAll(event);

                SharedPreferencesHelper.updateEventIdCount(CreateActivity.this);
                Toast.makeText(getApplicationContext(), "Event: " + event.getTitle() +" created.", Toast.LENGTH_SHORT).show();
                MainActivity.eventViewModel.getCurrentContent().setValue(new ArrayList<>());
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        spinner.setSelection(pos);
        if(pos==0)event.setSeverity(Event.Severity.COLD);
        else if(pos==1)event.setSeverity(Event.Severity.WARM);
        else if(pos==2)event.setSeverity(Event.Severity.HOT);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
