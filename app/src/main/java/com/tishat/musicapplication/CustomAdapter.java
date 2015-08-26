package com.tishat.musicapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tishat on 8/19/15.
 */
public class CustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public CustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        //don't need item ids for this list, using position as placeholder
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        //Handle TextView and display String from list
        TextView listItemName = (TextView)view.findViewById(R.id.itemName);
        listItemName.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageButton remove = (ImageButton)view.findViewById(R.id.removeButton);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
        });

        return view;
    }

}
