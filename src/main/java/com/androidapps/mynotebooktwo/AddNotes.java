package com.androidapps.mynotebooktwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNotes extends AppCompatActivity {

    Toolbar toolbar;
    EditText editTitle, editDetails;
    Calendar calendar;
    String todaysDate;
    String currentTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white)); // change the color of the title text
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// modify the back button in the app manifest

        editTitle = findViewById(R.id.editTitle);
        editDetails = findViewById(R.id.editDetails);

        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Get values for calender
        calendar = Calendar.getInstance();
        todaysDate = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(calendar.get(Calendar.HOUR)) + "/" + pad(calendar.get(Calendar.MINUTE));

        Log.d( "Calender","Date and time: " + calendar + " and " + currentTime);
    }

    private  String pad(int i) {
        if(i<10)
            return "0" + i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,  menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete) {
//
//            Intent intent = new Intent(this, AddNotes.class);
//            startActivity(intent);
            Toast.makeText(this, "Deleted: ", Toast.LENGTH_SHORT).show();
            //Log.i("Info", "New notes added");
            onBackPressed();
        }
            if(item.getItemId() == R.id.save){
                Notes notes = new Notes(editTitle.getText().toString(), editDetails.getText().toString(), todaysDate, currentTime);
                NoteDatabase noteDatabase = new NoteDatabase(this);
                noteDatabase.addNote(notes);

            Toast.makeText(this, "saved: ", Toast.LENGTH_SHORT).show();
            //method to return to main
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
