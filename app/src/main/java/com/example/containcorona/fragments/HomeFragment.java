package com.example.containcorona.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.example.containcorona.CoronaApiService;
import com.example.containcorona.CoronaApiServiceCallback;
import com.example.containcorona.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements CoronaApiServiceCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CoronaApiService coronaApiService = new CoronaApiService(this);
        coronaApiService.requestData();
        return inflater.inflate(R.layout.fragment_home, container, false);
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

        AnyChartView anyChartView = (AnyChartView) getView().findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
    }
}