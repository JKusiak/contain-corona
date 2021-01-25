package com.example.containcorona.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.example.containcorona.CountriesListAdapter;
import com.example.containcorona.ObservableChosenCountry;
import com.example.containcorona.R;
import java.util.Observable;
import java.util.Observer;


public class CountriesFragment extends Fragment implements Observer {
    public SharedPreferences appPreferences;
    public SharedPreferences.Editor editor;
    public ObservableChosenCountry observableChosenCountry;
    public ListView listView;
    CountriesListAdapter countryListAdapter;

    private String countryNames[] = {
            "Worldwide",
            "Austria",
            "Brazil",
            "China",
            "Czech Republic",
            "France",
            "Germany",
            "Hong Kong",
            "Hungary",
            "Iceland",
            "India",
            "Italy",
            "Norway",
            "Poland",
            "United Kingdom",
            "USA",
            "Vanuatu",
    };

    private Integer imageid[] = {
            R.drawable.ic_worldwide,
            R.drawable.ic_austria,
            R.drawable.ic_brazil,
            R.drawable.ic_china,
            R.drawable.ic_czech_republic,
            R.drawable.ic_france,
            R.drawable.ic_germany,
            R.drawable.ic_hong_kong,
            R.drawable.ic_hungary,
            R.drawable.ic_iceland,
            R.drawable.ic_india,
            R.drawable.ic_italy,
            R.drawable.ic_norway,
            R.drawable.ic_republic_of_poland,
            R.drawable.ic_united_kingdom,
            R.drawable.ic_united_states_of_america,
            R.drawable.ic_vanuatu,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appPreferences = this.getActivity().getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);

        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observableChosenCountry = new ObservableChosenCountry();
        observableChosenCountry.addObserver(this);

        observableChosenCountry.setChosenCountry(appPreferences.getString("currentCountryName", "Anything"));

        listView = (ListView) view.findViewById(R.id.countries_list);
        countryListAdapter = new CountriesListAdapter(getActivity(), countryNames, imageid, observableChosenCountry);

        listView.setAdapter(countryListAdapter);
        listView.setFocusable(false);
    }

    @Override
    public void update(Observable observable, Object chosenCountry) {
        if (observable instanceof ObservableChosenCountry) {
            ObservableChosenCountry observableChosenCountry = (ObservableChosenCountry) observable;
            editor = appPreferences.edit();
            editor.putString("currentCountryName", (String) observableChosenCountry.getChosenCountry());
            editor.apply();
            Toast.makeText(getActivity(),"You Selected "+ (String) observableChosenCountry.getChosenCountry() + " as Country", Toast.LENGTH_SHORT).show();
        }
    }
}