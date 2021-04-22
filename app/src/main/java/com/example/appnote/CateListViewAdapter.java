package com.example.appnote;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CateListViewAdapter extends BaseAdapter {
    final ArrayList<Category> listCategory;
    public CateListViewAdapter(ArrayList<Category> listCate) {
        this.listCategory = listCate;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return listCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listCategory.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCate;
        if(convertView == null){
            viewCate = View.inflate(parent.getContext(), R.layout.status_view,null);
        }else viewCate=convertView;
        //bind dữ liệu
        Category category =(Category) getItem(position);
        ((TextView) viewCate.findViewById(R.id.statusname)).setText(String.format("Name: %s",category.name));
        ((TextView) viewCate.findViewById(R.id.statuscreate)).setText(String.format("Created: %s",category.Createdday));
//            ((TextView) viewStatus.findViewById(R.id.tv_email)).setText(String.format("Mail: %s",user.email));

        return viewCate;
    }
}
