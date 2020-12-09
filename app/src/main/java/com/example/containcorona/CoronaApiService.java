package com.example.containcorona;

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
    private int nc;
    private int gc;
    private String jsonBody;
    private JSONObject jsonData;
    private CoronaApiServiceCallback apiCallback;

    public void set(int newCases, int globalCases){
        this.newCases = newCases;
        this.globalCases = globalCases;
    }

    public CoronaApiService(CoronaApiServiceCallback apiCallback) {
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
                try {
                    jsonData = new JSONObject(jsonBody);
                    nc = Integer.parseInt(jsonData.getJSONObject("Global").getString("NewConfirmed"));
                    gc = Integer.parseInt(jsonData.getJSONObject("Global").getString("TotalConfirmed"));
                    set(nc, gc);
                    apiCallback.callback(nc, gc);
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
