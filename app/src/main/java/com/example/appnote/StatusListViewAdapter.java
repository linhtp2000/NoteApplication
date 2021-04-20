package com.example.appnote;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StatusListViewAdapter extends BaseAdapter {

    private Context context;
    final ArrayList<Status> listStatus;



    public StatusListViewAdapter(ArrayList<Status> listStatus) {
        this.listStatus = listStatus;
    }

    @Override
    public int getCount() {
        return listStatus.size();
    }

    private class ViewHolder{
        TextView txtName;
        TextView txtCreated;
    }


    @Override
    public Object getItem(int position) {
        return listStatus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listStatus.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewStatus;
        ViewHolder viewHolder;
        if(convertView == null){
            viewStatus = View.inflate(parent.getContext(), R.layout.status_view,null);
        }else viewStatus=convertView;
        //bind dữ liệu
//        Status status =(Status) getItem(position);
//        ((TextView) viewStatus.findViewById(R.id.statusname)).setText(String.format("Name: %s",status.name));
//        ((TextView) viewStatus.findViewById(R.id.statuscreate)).setText(String.format("Created: %s",status.Created));
//            ((TextView) viewStatus.findViewById(R.id.tv_email)).setText(String.format("Mail: %s",user.email));
        Status statusOne= listStatus.get(position);
        ((TextView) viewStatus.findViewById(R.id.statusname)).setText(String.format("Name: %s",statusOne.name));
        ((TextView) viewStatus.findViewById(R.id.statuscreate)).setText(String.format("Created: %s",statusOne.Created));


        return viewStatus;
    }
}

