package com.example.containcorona.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Chart;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.example.containcorona.CoronaApiService;
import com.example.containcorona.CoronaApiServiceCallback;
import com.example.containcorona.DailySummary;
import com.example.containcorona.Graph;
import com.example.containcorona.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CoronaApiServiceCallback {

    private final ArrayList<Integer> anvIds = new ArrayList<>();
    private int howManyDrawn = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        CoronaApiService coronaApiService = new CoronaApiService(this.getActivity(), this);
        fakeAll();
        coronaApiService.requestData();
    }

    @Override
    public void callback(int[] values, Graph which, boolean shouldWeFake, ArrayList<DailySummary> week) {
        switch (which) {
            case PIE_NEW_VS_TOTAL:
                if (shouldWeFake) {
                    break;
                }
            {
                List<Integer> cases = new ArrayList<>();
                String[] types = {"New", "Total"};

                cases.add(values[0]);
                cases.add(values[1]);

                List<DataEntry> data = new ArrayList<>();
                for (int i = 0; i < cases.size(); i++)
                    data.add(new ValueDataEntry(types[i], cases.get(i)));

                Pie pie = AnyChart.pie();
                pie.setData(data);

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(pie);
            }
            break;
            case COLUMN_NEWS:
                if (shouldWeFake) {
                    break;
                }
            {
                List<Integer> cases = new ArrayList<>();
                String[] types = {"New Cases", "New Deaths", "New Recovers"};

                cases.add(values[0]);
                cases.add(values[1]);
                cases.add(values[2]);

                List<DataEntry> data = new ArrayList<>();
                for (int i = 0; i < cases.size(); i++)
                    data.add(new ValueDataEntry(types[i], cases.get(i)));

                Cartesian cart = AnyChart.column();
                cart.setData(data);
                cart.getYScale().setMinimum(0.0);

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(cart);
            }
            break;
            case STH_OTHER:
                if (shouldWeFake) {
                    break;
                }
            {
                List<Integer> cases = new ArrayList<>();
                String[] types = {"Total Deaths", "Total Recovers"};

                cases.add(values[0]);
                cases.add(values[1]);

                List<DataEntry> data = new ArrayList<>();
                for (int i = 0; i < cases.size(); i++)
                    data.add(new ValueDataEntry(types[i], cases.get(i)));

                Cartesian cart = AnyChart.bar();
                cart.setData(data);

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(cart);
            }
            break;
        }
    }

    private void fakeAll() {
        anvIds.add(R.id.any_chart_view);
        anvIds.add(R.id.any_chart_viuu);
        anvIds.add(R.id.any_chart_view3);

        for (int i = 0; i < anvIds.size(); i++) {
            fakeGraph(i);
        }
    }
    private void fakeGraph(int which) {
        AnyChartView anyChartView = (AnyChartView) getView().findViewById(anvIds.get(which));
        //anyChartView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        anyChartView.setChart(new Chart());
    }
}