package com.example.containcorona.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.containcorona.GraphSettingsList;
import com.example.containcorona.R;


public class AddGraphFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (GraphSettingsList.pieNewVsTotalOn) {
            CheckBox pie = getView().findViewById(R.id.pieCheck);
            pie.setChecked(true);
        }
        if (GraphSettingsList.someOtherChartOn) {
            CheckBox col = getView().findViewById(R.id.colCheck);
            col.setChecked(true);
        }
    }

    @Override
    public void onDestroyView() {
        CheckBox pie = getView().findViewById(R.id.pieCheck);
        CheckBox col = getView().findViewById(R.id.colCheck);
        GraphSettingsList.pieNewVsTotalOn = pie.isChecked();
        GraphSettingsList.someOtherChartOn = col.isChecked();
        super.onDestroyView();
    }
}