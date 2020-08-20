package com.example.fishingdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


public class Mylocations extends AppCompatActivity implements View.OnClickListener {

    LinearLayout scroll;
    String s,s2;
    DBHelper dbHelper;
    EditText etLoc;
    Button btnAddLoc;
    SQLiteDatabase db;
    Cursor c;
    String[] columns;
    ContentValues cv;
    String loc;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylocations);

        dbHelper = new DBHelper(this);
        scroll = findViewById(R.id.scroll);

        etLoc = findViewById(R.id.etLoc);

        btnAddLoc = findViewById(R.id.btnAddLoc);
        btnAddLoc.setOnClickListener(this);
        columns = new String[]{"Mylocations"};
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Мої локації");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Main2Activity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        scroll.removeAllViews();
        db = dbHelper.getWritableDatabase();
        c = db.query("mytable", columns, null, null, null, null, null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            s2=c.getString(0);
            if(s2!=null) {
                Button location = new Button(this);
                location.setText(s2);
                location.setId(i);
                location.setOnClickListener(this);
                scroll.addView(location);
            }
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    @Override
    public void onClick(View v) {
        db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnAddLoc:
                loc = etLoc.getText().toString();
                cv = new ContentValues();
                cv.put("Mylocations", loc);
                db.insert("mytable", null, cv);
                onResume();
                break;
            default:
                c = db.query("mytable", columns, null, null, null, null, null);
                for (int i = 0; i < c.getCount(); i++) {
                    if(v.getId()==i){
                        Button btn = findViewById(i);
                        s = btn.getText().toString();
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Warning")
                                .setMessage("Ви точно хочете видалити запис?")
                                .setIcon(R.drawable.ic_launcher_background)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d(TAG,"My STRING [ "+s+" ]");
                                        db = dbHelper.getWritableDatabase();
                                        db.delete("mytable","Mylocations = ?",new String[]{s});
                                        onResume();
                                        dialog.cancel();
                                    }
                                })
                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
                break;

        }

        c.close();
        db.close();

    }


}


