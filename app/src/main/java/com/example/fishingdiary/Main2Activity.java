package com.example.fishingdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnList,btnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(this);

        btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume(){


        super.onResume();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnList:
                Intent intent3 = new Intent(this,Result.class);
                startActivity(intent3);
                break;
            case R.id.btnPlus:
                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_mylocations:
                Intent intent = new Intent(this,Mylocations.class);
                startActivity(intent);
                break;

            case R.id.menu_mybaits:
                Intent intent2 = new Intent(this,Mybaits.class);
                startActivity(intent2);
                break;

            case R.id.menu_myrecords:
                Intent intent3 = new Intent(this,Result.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
