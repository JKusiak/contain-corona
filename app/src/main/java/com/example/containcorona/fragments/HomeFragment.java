package com.example.containcorona.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Chart;
import com.anychart.anychart.ChartsWaterfall;
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
    private SharedPreferences appPreferences;

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
        appPreferences = getContext().getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);
        TextView message = getView().findViewById(R.id.noGraphMessage);
        if (!appPreferences.getBoolean("pieNewCasesVsTotalCasesOn", true)
        && !appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", true)
        && !appPreferences.getBoolean("barTotalDeathsVsRecoveriesOn", true)) {
            message.setText(R.string.noGraphsText);
        } else message.setText("");
    }

    @Override
    public void callback(int[] values, Graph which, boolean shouldWeFake, ArrayList<DailySummary> week) {
        switch (which) {
            case BAR_TOTAL_VS_TODAY:
                if (shouldWeFake) {
                    break;
                }
            {
                List<Integer> cases = new ArrayList<>();
                String[] types = {"Cases Today", "Total Cases"};

                cases.add(values[0]);
                cases.add(values[1]);

                List<DataEntry> data = new ArrayList<>();
                for (int i = 0; i < cases.size(); i++)
                    data.add(new ValueDataEntry(types[i], cases.get(i)));

                Cartesian cart = AnyChart.bar();
                cart.setData(data);
                cart.setPalette(new String[]{"#12100b"});

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(cart);
            }
            break;
            case COLUMNS_TODAY:
                if (shouldWeFake) {
                    break;
                }
            {
                List<Integer> cases = new ArrayList<>();
                String[] types = {"Cases Today", "Deaths Today", "Recoveries Today"};

                cases.add(values[0]);
                cases.add(values[1]);
                cases.add(values[2]);

                List<DataEntry> data = new ArrayList<>();
                for (int i = 0; i < cases.size(); i++)
                    data.add(new ValueDataEntry(types[i], cases.get(i)));

                Cartesian cart = AnyChart.column();
                cart.setData(data);
                cart.setPalette(new String[]{"#12100b"});

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(cart);
            }
            break;
            case PIE_TD_VS_TR:
                if (shouldWeFake) {
                    break;
                }
            {
                List<Integer> cases = new ArrayList<>();
                String[] types = {"Total Deaths", "Total Recoveries"};

                cases.add(values[0]);
                cases.add(values[1]);

                List<DataEntry> data = new ArrayList<>();
                for (int i = 0; i < cases.size(); i++)
                    data.add(new ValueDataEntry(types[i], cases.get(i)));

                Pie pie = AnyChart.pie();
                pie.setData(data);
                pie.explodeSlices(true);
                pie.setPalette(new String[] { "#12100b", "#e52629"});

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(pie);
            }
            break;
            case WATERFALL:
                if (shouldWeFake) {
                    break;
                }
            {
                ChartsWaterfall waterfall = AnyChart.waterfall();
                waterfall.getYScale().setMinimum(0d);
                waterfall.setLabels(true);
                waterfall.getGetSeries(0.0).fallingFill("#12100b", 1.0);
                waterfall.getGetSeries(0.0).risingFill("#e52629", 1.0);
                waterfall.getLabels().setFormat(
                        "function() {\n" +
                                "      if (this['isTotal']) {\n" +
                                "        return anychart.format.number(this.absolute, {\n" +
                                "          scale: true\n" +
                                "        })\n" +
                                "      }\n" +
                                "\n" +
                                "      return anychart.format.number(this.value, {\n" +
                                "        scale: true\n" +
                                "      })\n" +
                                "    }");

                List<DataEntry> data = new ArrayList<>();
                List<Integer> increases = new ArrayList<>();
                List<String> dates = new ArrayList<>();

                int[] valuen = {2000, 500, -680, 600, -30};
                String[] daten = {"NOV", "DEC", "JAN", "FEB", "MAR"};

                for (int i = 0; i < valuen.length; i++) {
                    data.add(new ValueDataEntry(daten[i], valuen[i]));
                }

                /*for (DailySummary day : week
                ) {
                    increases.add(day.confirmed - day.deaths - day.recovered);
                    dates.add(day.date);
                }

                for (int i = 0; i < values.length; i++) {
                    data.add(new ValueDataEntry(dates.get(i), increases.get(i)));
                }*/
                DataEntry end = new DataEntry();
                end.setValue("x", "NOW");
                end.setValue("isTotal", true);
                data.add(end);

                waterfall.data(data);

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(waterfall);
            }
        }
    }

    private void fakeAll() {
        anvIds.add(R.id.any_chart_view);
        anvIds.add(R.id.any_chart_viuu);
        anvIds.add(R.id.any_chart_view3);
        anvIds.add(R.id.any_chart_view4);

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