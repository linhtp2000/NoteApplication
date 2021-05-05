package com.example.appnote.ui.Adapter;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.R;
import com.example.appnote.ui.Database.Entity.Status;
import com.example.appnote.ui.status.StatusFragment;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder>{
    private List<Status> arrayListStatus;
    private StatusAdapter.OnLongClick onLongClick;
    Context context;
    StatusAdapter.onLongClickListener mOnLongClickListener;

    public void setData (List<Status> mListStatus) {
        arrayListStatus=mListStatus;
        notifyDataSetChanged();
    }
    public void setOnLongClickListener(StatusAdapter.onLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }
    public void  setOnLongClick(StatusAdapter.OnLongClick onLongClick){
        this.onLongClick = onLongClick;
    }

    public interface onLongClickListener {
        void ItemLongClicked(View v, int position);
    }

    public StatusAdapter(StatusAdapter.OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }
    public StatusAdapter(List<Status> arrayListStatus) {
        this.arrayListStatus = arrayListStatus;
    }

    public StatusAdapter(List<Status> arrayListStatus, OnLongClick onLongClick, Context context) {
        this.arrayListStatus = arrayListStatus;
        this.onLongClick = onLongClick;
        this.context = context;
    }

    public interface OnLongClick {
//        void showMenuUpdateDelete(StatusView statusView);
        void ItemLongClicked(View v, int position);
    }


    @NonNull
    @Override
    public StatusAdapter.StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_view, parent, false);
        return new StatusAdapter.StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.StatusViewHolder holder, int position) {
        Status statusView = arrayListStatus.get(position);
        if (arrayListStatus == null) {
            return;
        }
        holder.tvName.setText("Name: " + statusView.getName());
        holder.tvDate.setText("Created Date: " + statusView.getDate());
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
        if (arrayListStatus != null) {
            return arrayListStatus.size();
        }
        return 0;
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView tvName, tvDate;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.statusname);
            tvDate = itemView.findViewById(R.id.statuscreate);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    StatusFragment.stastt=position;
//                    new onLongItemClickListener() {
//                        @Override
//                        public void ItemLongClicked(View v, int position) {
//                            arrayListStatus.get(position);
//                        }
//                    };
//                    arrayListStatus.get(position);
//                    onLongClick.ItemLongClicked(v, position);
//                    Toast.makeText(itemView.getContext(),position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),v.getId(),1,"Edit");
            menu.add(this.getAdapterPosition(),v.getId(),2,"Delete");
        }
//        public void getposition(int a){
//            int position = getAdapterPosition();
//
////                    new onLongItemClickListener() {
////                        @Override
////                        public void ItemLongClicked(View v, int position) {
////                            arrayListStatus.get(position);
////                        }
////                    };
//            a=position;
//        };

    }


}
