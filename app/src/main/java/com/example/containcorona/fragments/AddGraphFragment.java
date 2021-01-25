package com.example.containcorona.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.containcorona.GraphSettingsList;
import com.example.containcorona.R;


public class AddGraphFragment extends Fragment {

    public SharedPreferences appPreferences;
    public SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.appPreferences = this.getActivity().getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);

        CheckBox pie = getView().findViewById(R.id.pieCheck);
        CheckBox col = getView().findViewById(R.id.colCheck);
        CheckBox bar = getView().findViewById(R.id.barCheck);

        pie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = appPreferences.edit();
                editor.putBoolean("pieNewCasesVsTotalCasesOn", isChecked);
                editor.apply();
            }
        });

        col.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = appPreferences.edit();
                editor.putBoolean("columnNewCasesDeathsAndRecoveriesOn", isChecked);
                editor.apply();
            }
        });

        bar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = appPreferences.edit();
                editor.putBoolean("barTotalDeathsVsRecoveriesOn", isChecked);
                editor.apply();
            }
        });

        if (appPreferences.getBoolean("pieNewCasesVsTotalCasesOn", false)) {
            pie.setChecked(true);
        }
        if (appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", false)) {
            col.setChecked(true);
        }
        if (appPreferences.getBoolean("barTotalDeathsVsRecoveriesOn", false)) {
            bar.setChecked(true);
        }
    }

    @Override
    public void onDestroyView() {/*
        CheckBox pie = getView().findViewById(R.id.pieCheck);
        CheckBox col = getView().findViewById(R.id.colCheck);
        CheckBox bar = getView().findViewById(R.id.barCheck);
        //GraphSettingsList.pieNewVsTotalOn = pie.isChecked();
        GraphSettingsList.columnNewsOn = col.isChecked();
        GraphSettingsList.sthOtherOn = bar.isChecked();*/
        super.onDestroyView();
    }
}