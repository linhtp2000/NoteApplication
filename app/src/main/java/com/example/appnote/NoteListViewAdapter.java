package com.example.appnote;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteListViewAdapter extends BaseAdapter {
    final ArrayList<Note> listNote;
    public NoteListViewAdapter(ArrayList<Note> listNote){
        this.listNote=listNote;
    }
    @Override
    public int getCount() {
        return listNote.size();
    }

    @Override
    public Object getItem(int position) {
        return listNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listNote.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewNote;
        if(convertView == null){
            viewNote = View.inflate(parent.getContext(), R.layout.note_view,null);
        }else viewNote=convertView;
        //bind dữ liệu
        Note note =(Note) getItem(position);
        ((TextView) viewNote.findViewById(R.id.notename)).setText(String.format("Name: %s",note.name));
        ((TextView) viewNote.findViewById(R.id.notecategory)).setText(String.format("Category: %s",note.category));
        ((TextView) viewNote.findViewById(R.id.notepriority)).setText(String.format("Priority: %s",note.priority));
        ((TextView) viewNote.findViewById(R.id.notestatus)).setText(String.format("Status: %s",note.status));
        ((TextView) viewNote.findViewById(R.id.noteplan)).setText(String.format("Plan date: %s",note.planDate));
        ((TextView) viewNote.findViewById(R.id.notecreate)).setText(String.format("Created Date: %s",note.Createdday));
        return viewNote;
    }
}

