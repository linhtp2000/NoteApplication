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
import com.example.appnote.ui.Database.Entity.Priority;
import com.example.appnote.ui.slideshow.SlideshowFragment;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PrioViewHolder>{
    private List<Priority> arrayListPrio;
    private PriorityAdapter.OnLongClick onLongClick;
    Context context;
    PriorityAdapter.onLongClickListener mOnLongClickListener;

    public void setData (List<Priority> mListPriority) {
        arrayListPrio=mListPriority;
        notifyDataSetChanged();
    }
    public void setOnLongClickListener(PriorityAdapter.onLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }
    public void  setOnLongClick(PriorityAdapter.OnLongClick onLongClick){
        this.onLongClick = onLongClick;
    }

    public interface onLongClickListener {
        void ItemLongClicked(View v, int position);
    }

    public PriorityAdapter(PriorityAdapter.OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }
    public PriorityAdapter(List<Priority> arrayListPrio) {
        this.arrayListPrio = arrayListPrio;
    }

    public PriorityAdapter(List<Priority> arrayListPrio, OnLongClick onLongClick, Context context) {
        this.arrayListPrio = arrayListPrio;
        this.onLongClick = onLongClick;
        this.context = context;
    }

    public interface OnLongClick {
        //        void showMenuUpdateDelete(StatusView statusView);
        void ItemLongClicked(View v, int position);
    }


    @NonNull
    @Override
    public PriorityAdapter.PrioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_view, parent, false);
        return new PriorityAdapter.PrioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityAdapter.PrioViewHolder holder, int position) {
        Priority priorityView = arrayListPrio.get(position);
        if (arrayListPrio == null) {
            return;
        }
        holder.tvName.setText("Name: " + priorityView.getName());
        holder.tvDate.setText("Created Date: " + priorityView.getDate());
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
        if (arrayListPrio != null) {
            return arrayListPrio.size();
        }
        return 0;
    }

    public class PrioViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView tvName, tvDate;

        public PrioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.statusname);
            tvDate = itemView.findViewById(R.id.statuscreate);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    SlideshowFragment.priostt=position;
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
