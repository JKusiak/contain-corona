package com.example.containcorona.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.containcorona.CountriesListAdapter;
import com.example.containcorona.R;


public class CountriesFragment extends Fragment {

    private String countryNames[] = {
            "Worldwide",
            "Austria",
            "China",
            "Czech Republic",
            "Germany",
            "Iceland",
            "India",
            "Norway",
            "Poland",
            "United Kingdom",
            "USA",
            "Vanuatu",
    };

    private Integer imageid[] = {
            R.drawable.ic_worldwide,
            R.drawable.ic_austria,
            R.drawable.ic_china,
            R.drawable.ic_czech_republic,
            R.drawable.ic_germany,
            R.drawable.ic_iceland,
            R.drawable.ic_india,
            R.drawable.ic_norway,
            R.drawable.ic_republic_of_poland,
            R.drawable.ic_united_kingdom,
            R.drawable.ic_united_states_of_america,
            R.drawable.ic_vanuatu,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.countries_list);
        CountriesListAdapter countryListAdapter = new CountriesListAdapter(getActivity(), countryNames, imageid);

        listView.setAdapter(countryListAdapter);
        listView.setFocusable(false);

    }


}