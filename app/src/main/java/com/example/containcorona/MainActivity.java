package com.example.containcorona;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    int globalCases;
    int newCases;
    String jsonBody;
    JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.covid19api.com/summary")
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonBody = response.body().string();
                try {
                    jsonData = new JSONObject(jsonBody);

                    newCases = Integer.parseInt(jsonData.getJSONObject("Global").getString("NewConfirmed"));
                    globalCases = Integer.parseInt(jsonData.getJSONObject("Global").getString("TotalConfirmed"));

                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    String[] types = {"Total", "New"};
                    List<Integer> cases = new ArrayList<>();
                    cases.add(newCases);
                    cases.add(globalCases);

                    for (int i = 0; i < cases.size(); i++)
                        data.add(new ValueDataEntry(types[i], cases.get(i)));


                    pie.setData(data);

                    AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
                    anyChartView.setChart(pie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ;
            }
        });
    }
}