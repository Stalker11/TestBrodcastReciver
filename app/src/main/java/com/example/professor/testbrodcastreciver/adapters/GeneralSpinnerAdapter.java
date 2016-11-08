package com.example.professor.testbrodcastreciver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.professor.testbrodcastreciver.R;

public class GeneralSpinnerAdapter extends ArrayAdapter<String> {
    private String[] spinnerText;
    private ViewHolder viewHolder;
    public static final String TAG = GeneralSpinnerAdapter.class.getSimpleName();

    public GeneralSpinnerAdapter(Context context, String[] spinnerText) {
        super(context, R.layout.spinner_layout, spinnerText);
        this.spinnerText = spinnerText;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createSpinnerView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createSpinnerView(position, convertView, parent);
    }

    private View createSpinnerView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.positionName = (TextView) convertView.findViewById(R.id.spinner_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.positionName.setText(spinnerText[position]);

        return convertView;
    }

    private class ViewHolder {
        private TextView positionName;
    }
}
