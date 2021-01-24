package com.example.containcorona.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
//import com.example.containcorona.CoronaApiService;
import com.example.containcorona.CoronaApiServiceCallback;
import com.example.containcorona.Graph;
import com.example.containcorona.GraphSettingsList;
import com.example.containcorona.R;

import java.util.ArrayList;
import java.util.List;

//implements CoronaApiServiceCallback
public class HomeFragment extends Fragment  {

    private TextView feedback;
    private int howManyAlDrawn = 0;
//    private ArrayList<AnyChartView> charts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        CoronaApiService coronaApiService = new CoronaApiService(this.getActivity(), this);
//        coronaApiService.requestData();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        feedback = view.findViewById(R.id.feedBack);
//        StringBuilder sb = new StringBuilder().append(GraphSettingsList.country).append(" ").append(howManyAlDrawn);
//        feedback.setText(sb);
//        ArrayList<Integer> anvIds = new ArrayList<>();
//        anvIds.add(R.id.any_chart_view);
//        anvIds.add(R.id.any_chart_viuu);
//        while (howManyAlDrawn != 2) {
//            howManyAlDrawn++;/*
//            List<DataEntry> fake = new ArrayList<>();
//            Pie pie = AnyChart.pie();
//            pie.setData(fake);
//
//            AnyChartView anyChartView;
//            anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyAlDrawn));
//            anyChartView.setChart(pie);
//            charts.add(anyChartView);*/
//        }
//    }
//
//    @Override
//    public void callback(int newCases, int globalCases, Graph which) {
//
//        switch (which) {
//            case PIE_NEW_VS_TOTAL:
//                List<Integer> cases = new ArrayList<>();
//                String[] types = {"New", "Total"};
//
//                cases.add(newCases);
//                cases.add(globalCases);
//
//                List<DataEntry> data = new ArrayList<>();
//                for (int i = 0; i < cases.size(); i++)
//                    data.add(new ValueDataEntry(types[i], cases.get(i)));
//
//                Pie pie = AnyChart.pie();
//                pie.setData(data);
//
//                AnyChartView anyChartView;
//                anyChartView = (AnyChartView) getView().findViewById(R.id.any_chart_view);
//                anyChartView.setChart(pie);
//                charts.add(anyChartView);
//                howManyAlDrawn++;
//                break;
//            case STH_OTHER:
//                List<Integer> quases = new ArrayList<>();
//                String[] typoes = {"New", "Total"};
//
//                quases.add(newCases);
//                quases.add(globalCases);
//
//                List<DataEntry> dejta = new ArrayList<>();
//                for (int i = 0; i < quases.size(); i++)
//                    dejta.add(new ValueDataEntry(typoes[i], quases.get(i)));
//
//                Pie pai = AnyChart.pie();
//                pai.setData(dejta);
//
//                AnyChartView anyChartViuu;
//                anyChartViuu = (AnyChartView) getView().findViewById(R.id.any_chart_viuu);
//                anyChartViuu.setChart(pai);
//                charts.add(anyChartViuu);
//                howManyAlDrawn++;
//                break;
//        }
//    }
}