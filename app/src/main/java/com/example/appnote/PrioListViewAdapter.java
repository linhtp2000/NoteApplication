package com.example.appnote;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PrioListViewAdapter extends BaseAdapter {
    final ArrayList<Priority> listPriority;
    public PrioListViewAdapter(ArrayList<Priority> listPriority) {
        this.listPriority = listPriority;
    }

    @Override
    public int getCount() {
        return listPriority.size();
    }

    @Override
    public Object getItem(int position) {
        return listPriority.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listPriority.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCate;
        if(convertView == null){
            viewCate = View.inflate(parent.getContext(), R.layout.status_view,null);
        }else viewCate=convertView;
        //bind dữ liệu
        Priority priority =(Priority) getItem(position);
        ((TextView) viewCate.findViewById(R.id.statusname)).setText(String.format("Name: %s",priority.name));
        ((TextView) viewCate.findViewById(R.id.statuscreate)).setText(String.format("Created: %s",priority.Createdday));
//            ((TextView) viewStatus.findViewById(R.id.tv_email)).setText(String.format("Mail: %s",user.email));

        return viewCate;
    }
}


