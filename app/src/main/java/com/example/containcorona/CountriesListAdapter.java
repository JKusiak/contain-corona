package com.example.containcorona;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.containcorona.fragments.CountriesFragment;


public class CountriesListAdapter extends ArrayAdapter {
    private String[] countryNames;
    private Integer[] imageid;
    private Activity context;
    public SharedPreferences.Editor editor;

    public CountriesListAdapter(Activity context, String[] countryNames, Integer[] imageid) {
        super(context, R.layout.row_countries, countryNames);
        this.context = context;
        this.countryNames = countryNames;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView==null)
            row = inflater.inflate(R.layout.row_countries, null, true);

        TextView textViewCountry = (TextView) row.findViewById(R.id.countryName);
        textViewCountry.isClickable();
        textViewCountry.setFocusable(true);
        textViewCountry.setText(countryNames[position]);
        textViewCountry.setCompoundDrawablesWithIntrinsicBounds(imageid[position], 0, 0, 0);
        //TODO MAKE IT CHANGE BACKGROUND COLOR ON CLICK FFS
//        textViewCountry.setBackground(context.getResources().getDrawable(R.drawable.box_selector));

        textViewCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context,"You Selected "+ countryNames[position] + " as Country", Toast.LENGTH_SHORT).show();
                GraphSettingsList.country = countryNames[position];
//                editor = CountriesFragment.appPreferences.edit();
//                editor.putString("currentCountryName", countryNames[position]);
//                editor.apply();
            }
        });

        return row;
    }
}
