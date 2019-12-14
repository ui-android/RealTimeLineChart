package com.example.kdrtc.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.kdrtc.R;
import com.third.mikephil.charting.charts.LineChart;
import com.third.mikephil.charting.components.Description;
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

public class FragmentChart extends FragmentBase {

    private LineChart chart1;
    Thread thread1;
    private LineChart chart2;
    Thread thread2;
    private LineChart chart3;
    Thread thread3;
    private LineChart chart4;
    Thread thread4;

    void initChart(LineChart chart, String Ydescription, OnChartValueSelectedListener chartValueSelectedListener){
        chart.setOnChartValueSelectedListener(chartValueSelectedListener);
        // enable description text
        chart.getDescription().setEnabled(true);

        Description description = new Description();
        description.setText(Ydescription);
        description.setTextSize(15);
        description.setPosition(180,30);
        description.setTextColor(Color.LTGRAY);
        chart.setDescription(description);

        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        // set an alternative background color
        chart.setBackgroundColor(Color.TRANSPARENT);
        LineData data = new LineData();
        data.setValueTextColor(Color.BLUE);


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
        xl.setTextColor(Color.BLUE);
        xl.setDrawGridLines(true);
//        xl.setAxisMaximum(100f);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void addEntry(LineChart chart,String lable, float y) {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet(lable);
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), y), 0);
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
                addEntry(chart, "时间/s",(float) (Math.random() * 40) + 30f);
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 200; i++) {

                    // Don't generate garbage runnables inside the loop.
                    getActivity().runOnUiThread(runnable);

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

    private LineDataSet createSet(String lable) {

        LineDataSet set = new LineDataSet(null, lable);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.BLACK);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_realtime_linechart,container,false);  // 此处的布局文件是普通的线性布局

        chart1 = view.findViewById(R.id.chart1);
        chart2 = view.findViewById(R.id.chart2);
        chart3 = view.findViewById(R.id.chart3);
        chart4 = view.findViewById(R.id.chart4);

        initChart(chart1,"帧率/fps", new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString() + " other:" + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        initChart(chart2, "发码率/bps", new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        initChart(chart3,"收码率/bps", new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        initChart(chart4, "丢包数/bps",new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });


        return view;
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart1, "RealtimeLineChartActivity1");
        saveToGallery(chart2, "RealtimeLineChartActivity2");
        saveToGallery(chart3, "RealtimeLineChartActivity3");
        saveToGallery(chart4, "RealtimeLineChartActivity4");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.realtime, menu);
        super.onCreateOptionsMenu(menu,inflater);
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
                addEntry(chart1, "时间/s",(float) (Math.random() * 40) + 30f);
                addEntry(chart2, "时间/s",(float) (Math.random() * 40) + 30f);
                addEntry(chart3, "时间/s",(float) (Math.random() * 40) + 30f);
                addEntry(chart4, "时间/s",(float) (Math.random() * 40) + 30f);
                break;
            }
            case R.id.actionClear: {
                chart1.clearValues();
                chart2.clearValues();
                chart3.clearValues();
                chart4.clearValues();
                Toast.makeText(getActivity(), "Chart cleared!", Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart1);
                }
                break;
            }
        }
        return true;
    }



}
