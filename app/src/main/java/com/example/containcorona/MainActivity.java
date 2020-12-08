package com.example.containcorona;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CoronaApiServiceCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoronaApiService coronaApiService = new CoronaApiService(this);
        coronaApiService.requestData();
    }

    @Override
    public void callback(int newCases, int globalCases) {

        List<Integer> cases = new ArrayList<>();
        String[] types = {"New", "Total"};

        cases.add(newCases);
        cases.add(globalCases);

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < cases.size(); i++)
            data.add(new ValueDataEntry(types[i], cases.get(i)));

        Pie pie = AnyChart.pie();
        pie.setData(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
    }

}