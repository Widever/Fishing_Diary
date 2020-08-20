package com.example.fishingdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Result extends AppCompatActivity implements View.OnClickListener {
    Integer ID;
    ListView lvRes;
    Spinner statparam;
    TextView tvcount;
    Button btnstat;
    BarChart chart;
    SimpleAdapter adapter;
    SQLiteDatabase db;
    Cursor c;
    String[] result;
    Integer wclear,wcloud,wrain,time4,time8,time12,time16,time20,time00,dhight,dmedium,dlow;
    ArrayList<Map<String,Object>> res = new ArrayList<>();
    String time,data,location,depth,weather,bait,species;
    String orderBy = "Date";
    Long date;
    DBHelper dbHelper;
    Map<String,Object> m;
    final String ATTRIBUTE_LOCATION = "location";
    final String ATTRIBUTE_BAIT = "bait";
    final String ATTRIBUTE_SPECIES = "species";
    final String ATTRIBUTE_ID = "id";
    final String ATTRIBUTE_TIME = "time";
    final String ATTRIBUTE_DATA = "data";
    final String ATTRIBUTE_DEPTH = "depth";
    final String ATTRIBUTE_WEATHER = "weather";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        wclear=0;
        wcloud=0;
        wrain=0;
        time20=0;
        time16=0;
        time12=0;
        time8=0;
        time4=0;
        time00=0;
        dhight=0;
        dmedium=0;
        dlow=0;

        statparam = findViewById(R.id.statparam);

        tvcount = findViewById(R.id.tvcount);

        chart = findViewById(R.id.chart);

        btnstat = findViewById(R.id.btnstat);
        btnstat.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        BarDataSet dataSet;

       lvRes=findViewById(R.id.lvRes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Журнал і стастика");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Main2Activity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnstat:

                if(statparam.getSelectedItem().toString().equals("Погода")) {
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    entries.add(new BarEntry(0, wclear));
                    entries.add(new BarEntry(1, wcloud));
                    entries.add(new BarEntry(2, wrain));

                    BarDataSet dataset = new BarDataSet(entries, "# of fish");


                    ArrayList lables = new ArrayList();
                    lables.add("Ясно");
                    lables.add("Хмарно");
                    lables.add("Дощ");

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(lables));
                    //xAxis.setDrawLabels(true);
                    //xAxis.setDrawGridLines(false);
                    //xAxis.setDrawAxisLine(true);
                    //xAxis.getXOffset();

                    YAxis yAxis = chart.getAxisRight();
                    yAxis.setEnabled(false);

                    BarData barData = new BarData(dataset);
                    //chart.getAxisLeft().setEnabled(false);
                    //chart.getAxisRight().setEnabled(false);
                    chart.setData(barData);
                }
                if(statparam.getSelectedItem().toString().equals("Час доби")){
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    entries.add(new BarEntry(0, time4));
                    entries.add(new BarEntry(1, time8));
                    entries.add(new BarEntry(2, time12));
                    entries.add(new BarEntry(3, time16));
                    entries.add(new BarEntry(4, time20));
                    entries.add(new BarEntry(5, time00));

                    BarDataSet dataset = new BarDataSet(entries, "# of fish");


                    ArrayList lables = new ArrayList();
                    lables.add("4:00..");
                    lables.add("8:00..");
                    lables.add("12:00..");
                    lables.add("16:00..");
                    lables.add("20:00..");
                    lables.add("00:00..");

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(lables));
                    //xAxis.setDrawLabels(true);
                    //xAxis.setDrawGridLines(false);
                    //xAxis.setDrawAxisLine(true);
                    //xAxis.getXOffset();

                    YAxis yAxis = chart.getAxisRight();
                    yAxis.setEnabled(false);

                    BarData barData = new BarData(dataset);
                    //chart.getAxisLeft().setEnabled(false);
                    //chart.getAxisRight().setEnabled(false);
                    chart.setData(barData);
                }
                if(statparam.getSelectedItem().toString().equals("Глибина")) {
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    entries.add(new BarEntry(0, dlow));
                    entries.add(new BarEntry(1, dmedium));
                    entries.add(new BarEntry(2, dhight));

                    BarDataSet dataset = new BarDataSet(entries, "# of fish");


                    ArrayList lables = new ArrayList();
                    lables.add("Мілко");
                    lables.add("Середньо");
                    lables.add("Глибоко");

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(lables));
                    //xAxis.setDrawLabels(true);
                    //xAxis.setDrawGridLines(false);
                    //xAxis.setDrawAxisLine(true);
                    //xAxis.getXOffset();

                    YAxis yAxis = chart.getAxisRight();
                    yAxis.setEnabled(false);

                    BarData barData = new BarData(dataset);
                    //chart.getAxisLeft().setEnabled(false);
                    //chart.getAxisRight().setEnabled(false);
                    chart.setData(barData);
                }
                //chart.setDescription(null);
                //chart.setDrawBarShadow(false);
                //chart.setDrawValueAboveBar(false);
                //chart.getLegend().setEnabled(false);

                chart.invalidate();


        }
    }

    @Override
    public void onResume(){
        super.onResume();
        res.clear();
        c = db.query("mytableres",null,null,null,null,null,orderBy+" DESC");
        c.moveToFirst();
        Integer count = c.getCount();
        tvcount.setText(count.toString());

        for(int i=0;i<c.getCount();i++){
            m=new HashMap<>();
            time = c.getString(0);
            data = c.getString(1);
            location = c.getString(2);
            if(location.length()>20)location = location.substring(0,20)+"...";
            depth = c.getString(3);
            weather = c.getString(4);
            bait = c.getString(5);
            if(bait.length()>10)bait = bait.substring(0,10)+"...";
            species = c.getString(6);
            date = c.getLong(7);
            ID = c.getInt(8);
            Log.d("DATE // // // ",  date.toString());

            Log.d("WEATHER______",weather);
            if(weather.equals("Ясно"))wclear++;
            if(weather.equals("Хмарно"))wcloud++;
            if(weather.equals("Дощ"))wrain++;

            if(time.equals("З 4:00 до 8:00"))time4++;
            if(time.equals("З 8:00 до 12:00"))time8++;
            if(time.equals("З 12:00 до 16:00"))time12++;
            if(time.equals("З 16:00 до 20:00"))time16++;
            if(time.equals("З 20:00 до 00:00"))time20++;
            if(time.equals("З 00:00 до 4:00"))time00++;

            if(depth.equals("Мілко"))dlow++;
            if(depth.equals("Середня глибина"))dmedium++;
            if(depth.equals("Глибоко"))dhight++;

            m.put(ATTRIBUTE_LOCATION,location);
            m.put(ATTRIBUTE_BAIT,bait);
            m.put(ATTRIBUTE_SPECIES,species);
            m.put(ATTRIBUTE_ID,ID);
            m.put(ATTRIBUTE_DATA, data);
            m.put(ATTRIBUTE_DEPTH, depth);
            m.put(ATTRIBUTE_TIME, time);
            m.put(ATTRIBUTE_WEATHER, weather);

            Log.d("MY STRINGS  //  ",m.get(ATTRIBUTE_LOCATION).toString());
            res.add(m);

            c.moveToNext();
        }
        String[] from ={ATTRIBUTE_LOCATION,ATTRIBUTE_BAIT,ATTRIBUTE_SPECIES};
        int[] to = {R.id.tvloc,R.id.tvbait,R.id.tvspec};
        adapter = new SimpleAdapter(this, res,R.layout.my_list_item,from,to);
        lvRes.setAdapter(adapter);
        btnstat.callOnClick();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        lvRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               // AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Information")
                        .setMessage("Час: " + res.get(position).get(ATTRIBUTE_TIME).toString() + "\n" +
                                    "Дата: " + res.get(position).get(ATTRIBUTE_DATA).toString() + "\n" +
                                    "Локація: " + res.get(position).get(ATTRIBUTE_LOCATION).toString() + "\n" +
                                    "Глибина: " + res.get(position).get(ATTRIBUTE_DEPTH).toString() + "\n" +
                                    "Погода: " + res.get(position).get(ATTRIBUTE_WEATHER).toString() + "\n" +
                                    "Приманка: " + res.get(position).get(ATTRIBUTE_BAIT).toString() + "\n" +
                                    "Вид: " + res.get(position).get(ATTRIBUTE_SPECIES).toString()
                                    )

                        .setIcon(R.drawable.ic_launcher_background)
                        .setCancelable(false)
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db = dbHelper.getWritableDatabase();
                                String s = res.get(position).get(ATTRIBUTE_ID).toString();
                                db.delete("mytableres","id = ?",new String[]{s});
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
        });


        c.close();
        Log.d("WCLEAR____",wclear.toString());
        Log.d("WCLOUD____",wcloud.toString());
        Log.d("WRAIN_____",wrain.toString());
    }

}
