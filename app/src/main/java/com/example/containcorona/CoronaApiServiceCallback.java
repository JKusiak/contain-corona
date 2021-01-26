package com.example.containcorona;

import java.util.ArrayList;

public interface CoronaApiServiceCallback {
    void callback(int[] values, Graph which, boolean shouldWeFake, ArrayList<DailySummary> week);
}
