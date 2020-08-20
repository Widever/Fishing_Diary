package com.example.fishingdiary;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.Menu;


import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    Button addRes;
    Spinner time, location, depth, weather, bait, species;
    TextView data;
    String Stime, Slocation, Sdepth, Sweather, Sbait, Sspecies, Sdata;
    long Ldate;
    SQLiteDatabase db;
    Cursor c;
    String[] columns, loc_, baits_;
    ArrayList<String> locations = new ArrayList<>(), baits = new ArrayList<>();
    String s;
    Calendar date = Calendar.getInstance();
    Date fa;


    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.time1);
        data = findViewById(R.id.data);
        data.setOnClickListener(this);
        location = findViewById(R.id.location);
        depth = findViewById(R.id.depth);
        weather = findViewById(R.id.weather);
        bait = findViewById(R.id.bait);
        species = findViewById(R.id.species);

        addRes = findViewById(R.id.addRes);
        addRes.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Новий запис");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main2Activity.class);
                startActivity(intent);
            }

            }
        );

    }

    @Override
    public void onResume() {
        super.onResume();
        locations.clear();
        baits.clear();
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        columns = new String[]{"Mylocations"};
        c = db.query("mytable", columns, null, null, null, null, null);
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            s = c.getString(0);
            if (s != null) locations.add(s);
            c.moveToNext();
        }
        c.close();

        columns = new String[]{"Mybaits"};
        c = db.query("mytable", columns, null, null, null, null, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            s = c.getString(0);
            if (s != null) baits.add(s);
            c.moveToNext();
        }
        c.close();

        locations.add("+ New location");
        baits.add("+ New bait");


        loc_ = locations.toArray(new String[locations.size()]);
        baits_ = baits.toArray(new String[baits.size()]);

        ArrayAdapter<String> adapter_locations = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loc_);
        adapter_locations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter_baits = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, baits_);
        adapter_baits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(adapter_locations);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String working_s = location.getItemAtPosition(position).toString();
                if (working_s.equals("+ New location")) {
                    Intent intent = new Intent(getBaseContext(), Mylocations.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bait.setAdapter(adapter_baits);
        bait.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String working_s = bait.getItemAtPosition(position).toString();
                if (working_s.equals("+ New bait")) {
                    Intent intent = new Intent(getBaseContext(), Mybaits.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setInitialDate();
    }

    @Override
    public void onClick(View v) {

        db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.addRes:
                ContentValues cv = new ContentValues();

                Stime = time.getSelectedItem().toString();
                Sdata = data.getText().toString();
                Slocation = location.getSelectedItem().toString();
                Sdepth = depth.getSelectedItem().toString();
                Sweather = weather.getSelectedItem().toString();
                Sbait = bait.getSelectedItem().toString();
                Sspecies = species.getSelectedItem().toString();
                Ldate = date.getTimeInMillis();

                cv.put("Time", Stime);
                cv.put("Data", Sdata);
                cv.put("Location", Slocation);
                cv.put("Depth", Sdepth);
                cv.put("Weather", Sweather);
                cv.put("Bait", Sbait);
                cv.put("Species", Sspecies);
                cv.put("Date", Ldate);

                db.insert("mytableres", null, cv);
                Toast.makeText(this, "Запис успішно доданий!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.data:
                setDate();
                break;

        }


        dbHelper.close();
        c.close();
    }

    public void setDate() {
        new DatePickerDialog(this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDate() {
        data.setText(DateUtils.formatDateTime(this, date.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };
}


class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context){
        super(context,"myDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable("
                + "id integer primary key autoincrement,"
                + "Mylocations text,"
                + "Mybaits text"
                + ");");
        db.execSQL("create table mytableres("
                + "Time text,"
                + "Data text,"
                + "Location text,"
                + "Depth text,"
                + "Weather text,"
                + "Bait text,"
                + "Species text,"
                + "Date integer,"
                + "id integer primary key autoincrement"
                +");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
