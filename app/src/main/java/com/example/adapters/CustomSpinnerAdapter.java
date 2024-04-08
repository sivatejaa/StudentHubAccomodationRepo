package com.example.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class CustomSpinnerAdapter extends ArrayAdapter<String>{

    private Map<String, Boolean> enabledItemsMap;
    public CustomSpinnerAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
        enabledItemsMap = new HashMap<>();
        // By default, all items are enabled
        for (String item : items) {
            enabledItemsMap.put(item, true);
        }
    }

    // Method to disable a particular item
    public void disableItemByValue(String itemValue) {
        enabledItemsMap.put(itemValue, false);
        notifyDataSetChanged();
    }

    // Method to enable a particular item
    public void enableItemByValue(String itemValue) {
        enabledItemsMap.put(itemValue, true);
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        // Check if the item is enabled or disabled
        String itemValue = getItem(position);
        return enabledItemsMap.get(itemValue);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Get the default dropdown view
        View view = super.getDropDownView(position, convertView, parent);
        // Disable the dropdown item if it's not enabled
        String itemValue = getItem(position);
        if (itemValue != null) {
            boolean isEnabled = enabledItemsMap.get(itemValue);
            view.setEnabled(isEnabled);
            if (!isEnabled) {
                // Set a grayed-out color for disabled items
                TextView textView = (TextView) view;
                textView.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
            }
        }
        return view;
    }
}