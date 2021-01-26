package com.example.containcorona.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
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

        ScrollView chart_scroll_view;
        chart_scroll_view = getActivity().findViewById(R.id.chart_scroll_view);
        chart_scroll_view.setVerticalScrollBarEnabled(false);

        appPreferences = getContext().getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);

        TextView message = getView().findViewById(R.id.noGraphMessage);
        if (!appPreferences.getBoolean("pieTotalDeathsVsTotalRecoveries", true)
                && !appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", true)
                && !appPreferences.getBoolean("barTotalCasesVsTodayCases", true)
                && !appPreferences.getBoolean("waterfallOn", true)
                && !appPreferences.getBoolean("accelerationYesterdayNewVsTodayNew", true)) {
            message.setText(R.string.noGraphsText);
        } else message.setText("");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void callback(int[] values, Graph which, boolean shouldWeFake, ArrayList<DailySummary> week) {
        switch (which) {
            case HEADER:
                if (shouldWeFake) {
                    break;
                }
            {
                TextView header = getView().findViewById(R.id.header);
                TextView header_second = getView().findViewById(R.id.header_scnd);
                double changeYesterday = week.get(1).confirmed - week.get(0).confirmed;
                double changeToday = week.get(2).confirmed - week.get(1).confirmed;
                double dpercentage = ((changeToday - changeYesterday) / changeYesterday) * 100;
                int percentage = (int) dpercentage;

                header.setText(String.format("New cases in %s : %d",
                        new Object[]{appPreferences.getString("currentCountryName", "Global"),
                                week.get(2).confirmed - week.get(1).confirmed}));

                if (percentage > 0) {
                    header_second.setText(percentage + "% more than yesterday");
                }
                else {
                    percentage *= -1;
                    header_second.setText(percentage + "% less than yesterday");
                }
            }
            break;
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
                pie.setPalette(new String[]{"#12100b", "#e52629"});

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

                for (DailySummary day : week
                ) {
                    increases.add(day.active);
                    dates.add(day.date.substring(0, day.date.indexOf('T')));
                }

                for (int i = increases.size() - 1; i > 0; i--) {
                    increases.set(i, increases.get(i) - increases.get(i - 1));
                }

                for (int i = 0; i < dates.size(); i++) {
                    data.add(new ValueDataEntry(dates.get(i), increases.get(i)));
                }
                DataEntry end = new DataEntry();
                end.setValue("x", "NOW");
                end.setValue("isTotal", true);
                data.add(end);

                waterfall.data(data);

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(waterfall);
            }
            break;
            case LINE_ACCELERATION:
                if (shouldWeFake) {
                    break;
                }
            {
                Cartesian line = AnyChart.line();
                List<DataEntry> data = new ArrayList<>();

                ArrayList<Integer> newCases = new ArrayList<>();
                ArrayList<String> dates = new ArrayList<>();
                for (int i = 1; i < week.size(); i++) {
                    newCases.add(week.get(i).confirmed - week.get(i - 1).confirmed);
                    dates.add(week.get(i).date.substring(0, week.get(i).date.indexOf('T')));
                }
                ArrayList<Float> percentages = new ArrayList<>();
                for (int i = 1; i < newCases.size(); i++) {
                    percentages.add((float) ((newCases.get(i) - newCases.get(i - 1))));
                }

                for (int i = 0; i < percentages.size(); i++) {
                    data.add(new ValueDataEntry(dates.get(i + 1), percentages.get(i).intValue()));
                }


                line.setData(data);
                line.setPalette(new String[]{"#12100b", "#e52629"});

                AnyChartView anyChartView;
                anyChartView = (AnyChartView) getView().findViewById(anvIds.get(howManyDrawn++));
                anyChartView.setChart(line);
            }

        }
    }

    private void fakeAll() {
        anvIds.add(R.id.any_chart_view);
        anvIds.add(R.id.any_chart_viuu);
        anvIds.add(R.id.any_chart_view3);
        anvIds.add(R.id.any_chart_view4);
        anvIds.add(R.id.any_chart_view5);

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