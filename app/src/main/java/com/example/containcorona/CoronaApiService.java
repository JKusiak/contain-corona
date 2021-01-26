package com.example.containcorona;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoronaApiService {
    public int globalCases;
    public int newCases;
    public int newDeaths;
    public int newRecovers;
    public int totalDeaths;
    public int totalRecovers;
    private int nc;
    private int gc;
    private int nr;
    private int nd;
    private int td;
    private int tr;
    private String jsonBody;
    private JSONObject jsonData;
    private JSONArray countryData;
    private final CoronaApiServiceCallback apiCallback;
    private final Activity context;
    public SharedPreferences appPreferences;


    private boolean cropWorldWide = true;

    public void set1(int newCases, int globalCases){
        this.newCases = newCases;
        this.globalCases = globalCases;
    }

    public void set2(int newCases, int newDeaths, int newRecovered){
        this.newCases = newCases;
        this.newDeaths = newDeaths;
        this.newRecovers = newRecovered;
    }

    public void set3(int totalDeaths, int totalRecovers){
        this.totalDeaths = totalDeaths;
        this.totalRecovers = totalRecovers;
    }

    public CoronaApiService(Activity context, CoronaApiServiceCallback apiCallback) {
        this.context = context;
        this.apiCallback = apiCallback;
        this.appPreferences = context.getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);
    }

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://api.covid19api.com/summary")
            .method("GET", null)
            .build();

    public void requestData() {
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonBody = response.body().string();
                boolean[] whichToFake = new boolean[Graph.values().length];
                String selectedCountry = appPreferences.getString("currentCountryName", "Poland");
                if (!selectedCountry.equals("Global")) {
                    try {
                        if (appPreferences.getBoolean("barTotalCasesVsTodayCases", false)) {
                            jsonData = new JSONObject(jsonBody);
                            for (int i = 0; i < jsonData.getJSONArray("Countries").length(); i++) {
                                if (jsonData.getJSONArray("Countries").getJSONObject(i).getString("Country").equals(selectedCountry)) {
                                    nc = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewConfirmed"));
                                    gc = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("TotalConfirmed"));
                                    set1(nc, gc);
                                    int[] result = {nc, gc};
                                    apiCallback.callback(result, Graph.BAR_TOTAL_VS_TODAY, false, null);
                                    break;
                                }
                            }
                        }
                        if (appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", false)) {
                            jsonData = new JSONObject(jsonBody);
                            for (int i = 0; i < jsonData.getJSONArray("Countries").length(); i++) {
                                if (jsonData.getJSONArray("Countries").getJSONObject(i).getString("Country").equals(selectedCountry)) {
                                    nc = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewConfirmed"));
                                    nd = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewDeaths"));
                                    nr = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewRecovered"));
                                    set2(nc, nd, nr);
                                    int[] result = {nc, nd, nr};
                                    apiCallback.callback(result, Graph.COLUMNS_TODAY, false, null);
                                    break;
                                }
                            }
                        }
                        if (appPreferences.getBoolean("pieTotalDeathsVsTotalRecoveries", false)) {
                            jsonData = new JSONObject(jsonBody);
                            for (int i = 0; i < jsonData.getJSONArray("Countries").length(); i++) {
                                if (jsonData.getJSONArray("Countries").getJSONObject(i).getString("Country").equals(selectedCountry)) {
                                    td = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("TotalDeaths"));
                                    tr = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("TotalRecovered"));
                                    set3(td, tr);
                                    int[] result = {td, tr};
                                    apiCallback.callback(result, Graph.PIE_TD_VS_TR, false, null);
                                    break;
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        if (appPreferences.getBoolean("barTotalCasesVsTodayCases", false)) {
                            jsonData = new JSONObject(jsonBody);
                                    nc = Integer.parseInt(jsonData.getJSONObject("Global").getString("NewConfirmed"));
                                    gc = Integer.parseInt(jsonData.getJSONObject("Global").getString("TotalConfirmed"));
                                    set1(nc, gc);
                                    int[] result = {nc, gc};
                                    apiCallback.callback(result, Graph.BAR_TOTAL_VS_TODAY, false, null);
                        }
                        if (appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", false)) {
                            jsonData = new JSONObject(jsonBody);
                                    nc = Integer.parseInt(jsonData.getJSONObject("Global").getString("NewConfirmed"));
                                    nd = Integer.parseInt(jsonData.getJSONObject("Global").getString("NewDeaths"));
                                    nr = Integer.parseInt(jsonData.getJSONObject("Global").getString("NewRecovered"));
                                    set2(nc, nd, nr);
                                    int[] result = {nc, nd, nr};
                                    apiCallback.callback(result, Graph.COLUMNS_TODAY, false, null);
                        }
                        if (appPreferences.getBoolean("pieTotalDeathsVsTotalRecoveries", false)) {
                            jsonData = new JSONObject(jsonBody);
                                    td = Integer.parseInt(jsonData.getJSONObject("Global").getString("TotalDeaths"));
                                    tr = Integer.parseInt(jsonData.getJSONObject("Global").getString("TotalRecovered"));
                                    set3(td, tr);
                                    int[] result = {td, tr};
                                    apiCallback.callback(result, Graph.PIE_TD_VS_TR, false, null);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
        });

        String choosenCountry = appPreferences.getString("currentCountryName", "Poland");
        Request countryRequest = new Request.Builder()
                .url("https://api.covid19api.com/country/"+choosenCountry)
                .method("GET", null)
                .build();
        client.newCall(countryRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (appPreferences.getBoolean("waterfallOn", false)) {
                        countryData = new JSONArray(response.body());
                        for (int i = countryData.length() - 7; i < countryData.length(); i++) {
                            ArrayList<DailySummary> week = new ArrayList<>();
                            JSONObject daySummaryJSON = countryData.getJSONObject(i);
                            DailySummary daySummary = new DailySummary();
                            daySummary.confirmed = daySummaryJSON.getInt("Confirmed");
                            daySummary.deaths = daySummaryJSON.getInt("Deaths");
                            daySummary.recovered = daySummaryJSON.getInt("Recovered");
                            daySummary.active = daySummaryJSON.getInt("Active");
                            daySummary.date = daySummaryJSON.getString("Date");
                            apiCallback.callback(null, Graph.WATERFALL, false, week);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
        });
    }

}
