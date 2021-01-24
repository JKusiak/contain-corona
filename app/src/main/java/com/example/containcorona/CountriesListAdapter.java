package com.example.containcorona;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CountriesListAdapter extends ArrayAdapter{
    private String[] countryNames;
    private Integer[] imageid;
    private Activity context;
    private ObservableChosenCountry chosenCountry;

    public CountriesListAdapter(Activity context, String[] countryNames, Integer[] imageid, ObservableChosenCountry chosenCountry) {
        super(context, R.layout.row_countries, countryNames);
        this.context = context;
        this.countryNames = countryNames;
        this.imageid = imageid;
        this.chosenCountry = chosenCountry;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView==null)
            row = inflater.inflate(R.layout.row_countries, null, true);

        TextView textViewCountry = (TextView) row.findViewById(R.id.countryName);
        textViewCountry.isClickable();
        textViewCountry.setFocusable(true);
        textViewCountry.setText(countryNames[position]);
        textViewCountry.setCompoundDrawablesWithIntrinsicBounds(imageid[position], 0, 0, 0);

        refreshAllCountries(parent);

        textViewCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chosenCountry.setChosenCountry(countryNames[position]);
                refreshAllCountries(parent);
            }
        });

        return row;
    }


    public void refreshAllCountries(ViewGroup listOfCountries) {
        for(int i=0; i < listOfCountries.getChildCount(); i++) {
            View row = listOfCountries.getChildAt(i);
            TextView textView = row.findViewById(R.id.countryName);
            if (chosenCountry.getChosenCountry().equals(textView.getText().toString())) {
                textView.setBackgroundResource(R.drawable.box_clicked);
            } else {
                textView.setBackgroundResource(R.drawable.box_unclicked);
            }
        }
    }
}
