package com.example.appnote.ui.slideshow;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.Category;
import com.example.appnote.Priority;
import com.example.appnote.R;
import com.example.appnote.ui.gallery.GalleryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ArrayList<Priority> listPriority;
    PrioListViewAdapter prioListViewAdapter;
    int stt;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final ListView listPri = root.findViewById(R.id.listPrio);
        listPriority=new ArrayList<>();
        listPriority.add(new Priority(1,"High","17/04/2021 2:24:00 AM"));
        listPriority.add(new Priority(2,"Medium","17/04/2021 2:24:00 AM"));
        listPriority.add(new Priority(3,"Slow","17/04/2021 2:24:00 AM"));
        prioListViewAdapter= new PrioListViewAdapter((listPriority));
        listPri.setAdapter(prioListViewAdapter);
        Button add = root.findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });
        listPri.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                stt=position;
                return false;
            }
        });

        registerForContextMenu(listPri);
        setHasOptionsMenu(true);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
    public static class PrioListViewAdapter extends BaseAdapter {
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
    public void Showdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.show();
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        EditText status = (EditText) dialog.findViewById(R.id.editName);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        addStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int gio=calendar.get(Calendar.HOUR);
                int phut=calendar.get(Calendar.MINUTE);
                int giay=calendar.get(Calendar.SECOND);
//                int msgiay=calendar.get(Calendar.)
//                int msgiay=calendar.get(Calendar.AM_PM);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date=(simpleDateFormat.format(calendar.getTime()));
                listPriority.add(new Priority(listPriority.size() + 1,status.getText().toString(),date + "  " + gio + ":" + phut + ":" + giay));
                dialog.cancel();
                prioListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Fixdialog(Priority oldStatus, final int index){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.setTitle("Update Status");
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        addStatus.setText("Save");
        final EditText status = (EditText) dialog.findViewById(R.id.editName);
        dialog.show();
//        status.setText(oldStatus);
        //BtnCancel
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //BtnSave
        addStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),"17/04/2021 2:24:00 AM"));
                //Cập nhật listview
                listPriority.set(index, new Priority(index,status.getText().toString(),listPriority.get(index).Createdday));
                dialog.cancel();
                prioListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Deldialog(Priority oldStatus, final int index){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_delete);
        Button close = (Button) dialog.findViewById(R.id.btnNo);
        Button addStatus =(Button) dialog.findViewById(R.id.btnYes);
        dialog.show();
//        status.setText(oldStatus);
        //BtnCancel
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //BtnOk Xóa
        addStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),"17/04/2021 2:24:00 AM"));
                //Cập nhật listview
//                listStatus.set(index, new Status(index,status.getText().toString(),"17/04/2021 2:24:00 AM"));
                listPriority.remove(index);
                dialog.cancel();
                prioListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1,v.getId(),1,"Sửa");
        menu.add(2,v.getId(),2,"Xóa");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Sửa"){
            Toast.makeText(getContext(),"Sửa",Toast.LENGTH_SHORT).show();
//            fix=true;
            Fixdialog(listPriority.get(stt),stt);
        }else if(item.getTitle()=="Xóa"){
            Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog(listPriority.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
}