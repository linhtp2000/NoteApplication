package com.example.appnote.ui.status;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.R;
import com.example.appnote.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class StatusFragment extends Fragment {
    private Button add;
    private StatusViewModel StatusViewModel;
    ArrayList<Status> listStatus;
    StatusListViewAdapter statusListViewAdapter;
    int stt;
    String date;
    boolean fix;
//    boolean fix=true;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_status, container, false);
//        View dialog = inflater.inflate(R.layout.status_add,container,false);
        final ListView listViewStatus = root.findViewById(R.id.listviewStatus);
        StatusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        add = root.findViewById(R.id.btnAdd);






        listStatus=new ArrayList<>();
        listStatus.add(new Status(1,"Done","17/04/2021 2:24:00 AM"));
        listStatus.add(new Status(2,"Coming","17/04/2021 2:24:00 AM"));
        listStatus.add(new Status(3,"Pending","17/04/2021 2:24:00 AM"));
//        listStatus.add(new Status(3,"cc","17/04/2021 2:24:00 AM"));
        statusListViewAdapter= new StatusListViewAdapter((listStatus));
        listViewStatus.setAdapter(statusListViewAdapter);
//
//        statusListViewAdapter = new StatusListViewAdapter(listStatus);
//        listViewStatus.setAdapter(statusListViewAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog
                Showdialog();
            }
        });

        listViewStatus.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                stt=position;
                return false;
            }
        });


        registerForContextMenu(listViewStatus);
        setHasOptionsMenu(true);
        return root;
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
//                int msgiay=calendar.get(Calendar.AM_PM);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date=(simpleDateFormat.format(calendar.getTime()));


                listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),date + "  " + gio + ":" + phut + ":" + giay));
                dialog.cancel();
                statusListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Fixdialog(Status oldStatus,final int index){
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
                listStatus.set(index, new Status(index,status.getText().toString(),listStatus.get(index).Created));
                dialog.cancel();
                statusListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Deldialog(Status oldStatus,final int index){
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
                listStatus.remove(index);
                dialog.cancel();
                statusListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    //Tạo adapter
//    public class StatusListViewAdapter extends BaseAdapter {
//        final ArrayList<Status> listStatus;
//        StatusListViewAdapter(ArrayList<Status> listStatus){
//            this.listStatus=listStatus;
//        }
//        @Override
//        public int getCount() {
//            return listStatus.size();
//        }
//
////        @Override
////        public Object getItem(int position) {
////            return listStatus.get(position);
////        }
//
//        @Override
//        public long getItemId(int position) {
//            return listStatus.get(position).id;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View viewStatus;
//            if(convertView == null){
//                viewStatus = View.inflate(parent.getContext(), R.layout.status_view,null);
//            }else viewStatus=convertView;
//            //bind dữ liệu
//            Status status =(Status) getItem(position);
//            ((TextView) viewStatus.findViewById(R.id.statusname)).setText(String.format("Name: %s",status.name));
//            ((TextView) viewStatus.findViewById(R.id.statuscreate)).setText(String.format("Created Day: %s",status.Created));
//            return viewStatus;
//        }
//    }

    public static class StatusListViewAdapter extends BaseAdapter {
        final ArrayList<Status> listStatus;
        public StatusListViewAdapter(ArrayList<Status> listStatus) {
            this.listStatus = listStatus;
        }

        @Override
        public int getCount() {
            return listStatus.size();
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
            if(convertView == null){
                viewStatus = View.inflate(parent.getContext(), R.layout.status_view,null);
            }else viewStatus=convertView;
            //bind dữ liệu
            Status status =(Status) getItem(position);
            ((TextView) viewStatus.findViewById(R.id.statusname)).setText(String.format("Name: %s",status.name));
            ((TextView) viewStatus.findViewById(R.id.statuscreate)).setText(String.format("Created: %s",status.Created));
//            ((TextView) viewStatus.findViewById(R.id.tv_email)).setText(String.format("Mail: %s",user.email));

            return viewStatus;
        }
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
            Fixdialog(listStatus.get(stt),stt);
        }else if(item.getTitle()=="Xóa"){
            Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog(listStatus.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
}
