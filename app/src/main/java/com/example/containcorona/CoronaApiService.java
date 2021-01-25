package com.example.containcorona;

import android.app.Activity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    private final CoronaApiServiceCallback apiCallback;
    private final Activity context;

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
                if (cropWorldWide) {/*
                //here I try and fail to include the global stats as a country
                    String jsonStart = jsonBody.substring(0, jsonBody.indexOf("Message") + 12);
                    String jsonMain = jsonBody.substring(jsonBody.indexOf("Countries") - 1);
                    String jsonMainAfters = jsonMain.substring(13);
                    String jsonMainHeader = jsonMain.substring(0, 14);
                    String jsonGlob = jsonBody.substring(jsonBody.indexOf("Global") - 1, jsonBody.indexOf("Countries") - 1 );

                    StringBuilder sb = new StringBuilder().append(jsonStart).append(jsonMainHeader);
                    GraphSettingsList.check = sb.toString();*/
                }
                boolean[] whichToFake = new boolean[Graph.values().length];
                try {
                    if (GraphSettingsList.pieNewVsTotalOn) {
                        jsonData = new JSONObject(jsonBody);
                        for (int i = 0; i < jsonData.getJSONArray("Countries").length(); i++) {
                            if (jsonData.getJSONArray("Countries").getJSONObject(i).getString("Country").equals(GraphSettingsList.country)) {
                                nc = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewConfirmed"));
                                gc = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("TotalConfirmed"));
                                set1(nc, gc);
                                int[] result = {nc, gc};
                                apiCallback.callback(result, Graph.PIE_NEW_VS_TOTAL, false);
                                break;
                            }
                        }
                        whichToFake[0] = false;
                    } else {
                        whichToFake[0] = true;
                    }

                    if (GraphSettingsList.columnNewsOn) {
                        jsonData = new JSONObject(jsonBody);
                        for (int i = 0; i < jsonData.getJSONArray("Countries").length(); i++) {
                            if (jsonData.getJSONArray("Countries").getJSONObject(i).getString("Country").equals(GraphSettingsList.country)) {
                                nc = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewConfirmed"));
                                nd = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewDeaths"));
                                nr = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("NewRecovered"));
                                set2(nc, nd, nr);
                                int[] result = {nc, nd, nr};
                                apiCallback.callback(result, Graph.COLUMN_NEWS, false);
                                break;
                            }
                        }
                        whichToFake[1] = false;
                    } else {
                        whichToFake[1] = true;
                    }
                    if (GraphSettingsList.sthOtherOn) {
                        jsonData = new JSONObject(jsonBody);
                        for (int i = 0; i < jsonData.getJSONArray("Countries").length(); i++) {
                            if (jsonData.getJSONArray("Countries").getJSONObject(i).getString("Country").equals(GraphSettingsList.country)) {
                                td = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("TotalDeaths"));
                                tr = Integer.parseInt(jsonData.getJSONArray("Countries").getJSONObject(i).getString("TotalRecovered"));
                                set3(td, tr);
                                int[] result = {td, tr};
                                apiCallback.callback(result, Graph.STH_OTHER, false);
                                break;
                            }
                        }
                        whichToFake[2] = false;
                    } else {
                        whichToFake[2] = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < whichToFake.length; i++) {
                    if (whichToFake[i]) {
                        apiCallback.callback(new int[]{0}, Graph.values()[i], true);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
        });
    }

}
