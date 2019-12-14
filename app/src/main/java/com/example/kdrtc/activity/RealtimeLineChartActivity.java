package com.example.kdrtc.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.kdrtc.R;
import com.example.kdrtc.notimportant.DemoBase;
import com.third.mikephil.charting.charts.LineChart;
import com.third.mikephil.charting.components.Legend;
import com.third.mikephil.charting.components.XAxis;
import com.third.mikephil.charting.components.YAxis;
import com.third.mikephil.charting.data.Entry;
import com.third.mikephil.charting.data.LineData;
import com.third.mikephil.charting.data.LineDataSet;
import com.third.mikephil.charting.highlight.Highlight;
import com.third.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.third.mikephil.charting.listener.OnChartValueSelectedListener;
import com.third.mikephil.charting.utils.ColorTemplate;


public class RealtimeLineChartActivity extends DemoBase implements
        OnChartValueSelectedListener {

    private LineChart chart1;
    Thread thread1;
    private LineChart chart2;
    Thread thread2;
    private LineChart chart3;
    Thread thread3;
    private LineChart chart4;
    Thread thread4;


    void initChart(LineChart chart, OnChartValueSelectedListener chartValueSelectedListener){
        chart.setOnChartValueSelectedListener(chartValueSelectedListener);
        // enable description text
        chart.getDescription().setEnabled(true);
        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
        xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
//        xl.setAxisMaximum(100f);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void addEntry(LineChart chart) {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(50);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }


    private void feedMultiple(Thread thread, final LineChart chart) {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                addEntry(chart);
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 200; i++) {

                    // Don't generate garbage runnables inside the loop.
                    runOnUiThread(runnable);

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_realtime_linechart);

        setTitle("RealtimeLineChartActivity");
        chart1 = findViewById(R.id.chart1);
        chart2 = findViewById(R.id.chart2);
        chart3 = findViewById(R.id.chart3);
        chart4 = findViewById(R.id.chart4);

        initChart(chart1, new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString() + " other:" + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        initChart(chart2, new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        initChart(chart3, new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        initChart(chart4, new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }



    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(2f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.realtime, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/RealtimeLineChartActivity.java"));
                startActivity(i);
                break;
            }
            case R.id.actionAdd: {
                addEntry(chart1);
                addEntry(chart2);
                addEntry(chart3);
                addEntry(chart4);
                break;
            }
            case R.id.actionClear: {
                chart1.clearValues();
                chart2.clearValues();
                chart3.clearValues();
                chart4.clearValues();
                Toast.makeText(this, "Chart cleared!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.actionFeedMultiple: {
                feedMultiple(thread1, chart1);
                feedMultiple(thread2, chart2);
                feedMultiple(thread3, chart3);
                feedMultiple(thread4, chart4);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart1);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void saveToGallery() {

        saveToGallery(chart1, "RealtimeLineChartActivity1");
        saveToGallery(chart2, "RealtimeLineChartActivity2");
        saveToGallery(chart3, "RealtimeLineChartActivity3");
        saveToGallery(chart4, "RealtimeLineChartActivity4");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (thread1 != null) {
            thread1.interrupt();
        }
        if (thread2 != null) {
            thread2.interrupt();
        }
        if (thread3 != null) {
            thread3.interrupt();
        }
        if (thread4 != null) {
            thread4.interrupt();
        }
    }
}
