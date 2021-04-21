package com.example.appnote.ui.slideshow;

import android.app.Dialog;
import android.database.Cursor;
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
import com.example.appnote.Database;
import com.example.appnote.MainActivity;
import com.example.appnote.PrioListViewAdapter;
import com.example.appnote.Priority;
import com.example.appnote.R;
import com.example.appnote.Status;
import com.example.appnote.ui.gallery.GalleryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ArrayList<Priority> listPriority;
    PrioListViewAdapter prioListViewAdapter;
    int stt;
    int phantutable=0;
    Database database;
    int xoa=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final ListView listPri = root.findViewById(R.id.listPrio);
        listPriority=new ArrayList<>();
        prioListViewAdapter= new PrioListViewAdapter(listPriority);
        listPri.setAdapter(prioListViewAdapter);

        //select data
        MainActivity activity = (MainActivity) getActivity();
        database= activity.getMyData();
//         Toast.makeText(getActivity(),StatusName,Toast.LENGTH_SHORT).show();
        getDataPriority();



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
//                Toast.makeText(getActivity(), stt,Toast.LENGTH_LONG).show();
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
    private void getDataPriority(){
        Cursor dataPriority=database.GetData("SELECT * FROM Priority");
        listPriority.clear();
        while (dataPriority.moveToNext()){
            String name = dataPriority.getString(1);
            String created = dataPriority.getString(2);
            int id=dataPriority.getInt(0);
            listPriority.add(new Priority(id,name,created));
            phantutable=phantutable+1;
            prioListViewAdapter.notifyDataSetChanged();
//            database =dataStatus.getString(1);
//            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
        }
    }
    public void Showdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.show();
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        EditText status = (EditText) dialog.findViewById(R.id.editName);
        TextView title = (TextView) dialog.findViewById(R.id.textView2);
        title.setText("Priority Form");
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
                String StatusName=status.getText().toString();
//                if(xoa!=0){
//                    database.QueryData("INSERT INTO Priority VALUES("+ (xoa) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");
//                    getDataPriority();
//                    xoa=0;
//                }else if(xoa==0){
//                    database.QueryData("INSERT INTO Priority VALUES("+ (listPriority.size() + 1) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");
//                    getDataPriority();
//                }
                if (StatusName.equals("")){
                    status.setHint("Tên không được để trống");
                }
                else {
                    database.QueryData("INSERT INTO Priority VALUES("+ (phantutable+1) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");
                    getDataPriority();
                    dialog.cancel();
                }


                dialog.cancel();
//                prioListViewAdapter.notifyDataSetChanged();
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
//                listPriority.set(index, new Priority(index,status.getText().toString(),listPriority.get(index).Createdday));
                database.QueryData("UPDATE Priority SET PrioName = '"+ status.getText().toString() +"' WHERE PrioName = '"+ listPriority.get(index).name +"' AND Created='"+ listPriority.get(index).Createdday+"' ");
                getDataPriority();
                dialog.cancel();
                Toast.makeText(getContext(),"Sửa thành công!",Toast.LENGTH_SHORT).show();
//                prioListViewAdapter.notifyDataSetChanged();
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
                xoa=index+1;
//                listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),"17/04/2021 2:24:00 AM"));
                //Cập nhật listview
//                listStatus.set(index, new Status(index,status.getText().toString(),"17/04/2021 2:24:00 AM"));
//                listPriority.remove(index);
                database.QueryData("DELETE FROM Priority WHERE PrioName = '"+ listPriority.get(index).name +"' AND Created='"+listPriority.get(index).Createdday+"' ");
//                listStatus.remove(index);
                getDataPriority();

                dialog.cancel();
                Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();
//                prioListViewAdapter.notifyDataSetChanged();
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

//            fix=true;
            Fixdialog(listPriority.get(stt),stt);
        }else if(item.getTitle()=="Xóa"){

            Deldialog(listPriority.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
}