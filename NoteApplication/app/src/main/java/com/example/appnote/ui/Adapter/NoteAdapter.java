package com.example.appnote.ui.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.NoteView;
import com.example.appnote.R;
import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.note.NoteFragment;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<NoteView> arrayListNote;
    private NoteAdapter.OnLongClick onLongClick;
    Context context;
    NoteAdapter.onLongClickListener mOnLongClickListener;

    public void setData (List<NoteView> mList) {
        arrayListNote = mList;
        notifyDataSetChanged();
    }
    public void setOnLongClickListener(NoteAdapter.onLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }
    public void  setOnLongClick(NoteAdapter.OnLongClick onLongClick){
        this.onLongClick = onLongClick;
    }

    public interface onLongClickListener {
        void ItemLongClicked(View v, int position);
    }

    public NoteAdapter(NoteAdapter.OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }
    public NoteAdapter(List<NoteView> arrayListNote) {
        this.arrayListNote = arrayListNote;
    }

    public NoteAdapter(List<NoteView> arrayListNote, NoteAdapter.OnLongClick onLongClick, Context context) {
        this.arrayListNote = arrayListNote;
        this.onLongClick = onLongClick;
        this.context = context;
    }

    public interface OnLongClick {
        //        void showMenuUpdateDelete(StatusView statusView);
        void ItemLongClicked(View v, int position);
    }


    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        return new NoteAdapter.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        NoteView noteView = arrayListNote.get(position);
        if (arrayListNote == null) {
            return;
        }
        holder.tvName.setText("Name: " + noteView.getName());
        holder.tvCate.setText("Category: " + noteView.getCategory());
        holder.tvPrio.setText("Priority: " + noteView.getPriority());
        holder.tvSta.setText("Status: " + noteView.getStatus());
        holder.tvPlanDate.setText("Plan date: " + noteView.getPlanDate());
        holder.tvCreatedDate.setText("Created Date: " + noteView.getCreatedday());
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                onLongClick.ItemLongClicked(v, position);
//                return true;
//            }
//        });

    }
    @Override
    public int getItemCount() {
        if (arrayListNote!= null) {
            return arrayListNote.size();
        }
        return 0;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView tvName, tvCate, tvPrio, tvSta, tvPlanDate, tvCreatedDate;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.notename);
            tvCate = itemView.findViewById(R.id.notecategory);
            tvPrio = itemView.findViewById(R.id.notepriority);
            tvSta = itemView.findViewById(R.id.notestatus);
            tvPlanDate = itemView.findViewById(R.id.noteplan);
            tvCreatedDate = itemView.findViewById(R.id.notecreate);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                  NoteFragment.notestt=position;
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),v.getId(),1,"Edit");
            menu.add(this.getAdapterPosition(),v.getId(),2,"Delete");
        }
    }
}

