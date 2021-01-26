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
import android.widget.ScrollView;

import com.example.containcorona.R;


public class AddGraphFragment extends Fragment {
    public SharedPreferences appPreferences;
    public SharedPreferences.Editor editor;
    CheckBox pie;
    CheckBox col;
    CheckBox bar;

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

        ScrollView addGraphScroll;
        addGraphScroll = getActivity().findViewById(R.id.add_graph_scroll);
        addGraphScroll.setVerticalScrollBarEnabled(false);

        pie = getView().findViewById(R.id.pieCheck);
        col = getView().findViewById(R.id.colCheck);
        bar = getView().findViewById(R.id.barCheck);

        setBoxState(pie, appPreferences.getBoolean("pieNewCasesVsTotalCasesOn", false));
        setBoxState(col, appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", false));
        setBoxState(bar, appPreferences.getBoolean("barTotalDeathsVsRecoveriesOn", false));


        pie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = appPreferences.edit();
                editor.putBoolean("pieNewCasesVsTotalCasesOn", isChecked);
                editor.apply();

                setBoxState(pie, appPreferences.getBoolean("pieNewCasesVsTotalCasesOn", false));
            }
        });

        col.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = appPreferences.edit();
                editor.putBoolean("columnNewCasesDeathsAndRecoveriesOn", isChecked);
                editor.apply();

                setBoxState(col, appPreferences.getBoolean("columnNewCasesDeathsAndRecoveriesOn", false));
            }
        });

        bar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = appPreferences.edit();
                editor.putBoolean("barTotalDeathsVsRecoveriesOn", isChecked);
                editor.apply();

                setBoxState(bar, appPreferences.getBoolean("barTotalDeathsVsRecoveriesOn", false));
            }
        });
    }

    public void setBoxState(CheckBox checkBox, boolean isChecked) {
        checkBox.setChecked(isChecked);

        if (isChecked){
            checkBox.setAlpha((float)1.00);
        } else {
            checkBox.setAlpha((float)0.15);
        }
    }
}