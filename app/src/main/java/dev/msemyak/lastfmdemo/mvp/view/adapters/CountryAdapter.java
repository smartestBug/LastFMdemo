package dev.msemyak.lastfmdemo.mvp.view.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dev.msemyak.lastfmdemo.R;

public class CountryAdapter extends ArrayAdapter<CountryAdapter.CountryInfo> {
    private ArrayList<CountryInfo> data = null;
    private LayoutInflater inflater;

    public CountryAdapter(Activity context, int resource, ArrayList<CountryInfo> data) {
        super(context, resource, data);
        this.data = data;
        inflater = context.getLayoutInflater();
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            row = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        }

        CountryInfo item = data.get(position);
        if (item != null) {
            ImageView myFlag = row.findViewById(R.id.iv_flag);
            TextView myCountry = row.findViewById(R.id.tv_country);
            myFlag.setImageResource(item.getCountryFlag());
            myCountry.setText(item.getCountryName());
        }

        return row;
    }

    // inner class
    public static class CountryInfo {
        private String countryName;
        private String queryName;
        private int countryFlag;

        public CountryInfo(String countryName, String queryName, int countryFlag) {
            this.countryName = countryName;
            this.countryFlag = countryFlag;
            this.queryName = queryName;
        }

        public String getQueryName() {
            return queryName;
        }

        public String getCountryName() {
            return countryName;
        }

        public int getCountryFlag() {
            return countryFlag;
        }
    }
}
