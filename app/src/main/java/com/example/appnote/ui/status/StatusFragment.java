package com.example.appnote.ui.status;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.R;
import com.example.appnote.StatusView;
import com.example.appnote.StatusListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class StatusFragment extends Fragment {
    private Button add;
    private StatusViewModel StatusViewModel;
    ArrayList<StatusView> listStatus;
    StatusListViewAdapter statusListViewAdapter;
    int stt;
    int xoa=0;
    String date;
    int phantutable=0;
  //  Database database;
//    Database database;
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
        statusListViewAdapter= new StatusListViewAdapter((listStatus));
        listViewStatus.setAdapter(statusListViewAdapter);
        //select data
//        ContentMainNav activity = (ContentMainNav) getActivity();
//        database= activity.getMyData();
////         Toast.makeText(getActivity(),StatusName,Toast.LENGTH_SHORT).show();
//        getDataStatus();


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
//                Toast.makeText(getActivity(),position,Toast.LENGTH_LONG).show();
                return false;
            }
        });


        registerForContextMenu(listViewStatus);
        setHasOptionsMenu(true);
        return root;
    }
//    private void getDataStatus(){
//        Cursor dataStatus=database.GetData("SELECT * FROM Status where UserID =" +MainActivity.IDCurrent+"");
//        listStatus.clear();
//        while (dataStatus.moveToNext()){
//            String name = dataStatus.getString(1);
//            String created = dataStatus.getString(2);
//            int id=dataStatus.getInt(0);
//            listStatus.add(new Status(id,name,created));
//            statusListViewAdapter.notifyDataSetChanged();
////            database =dataStatus.getString(1);
////            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
//        }
//    }

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
                String StatusName=status.getText().toString();
//                if (StatusName.equals("")){
//                    status.setHint("Tên không được để trống");
//                }
//                else {
//                    database.QueryData("INSERT INTO Status VALUES(null,'"+status.getText().toString()+"','"+ date+"  "+ gio + ":" + phut + ":" + giay+"',"+MainActivity.IDCurrent+")");
////                    database.QueryData("INSERT INTO Status VALUES("+ (phantutable+1) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"',"+MainActivity.IDCurrent+")");
//////                    listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),date + "  " + gio + ":" + phut + ":" + giay));
//                    getDataStatus();
//                    dialog.cancel();
////                    statusListViewAdapter.notifyDataSetChanged();
//                }

            }
                });
    }

    public void Fixdialog(StatusView oldStatus, final int index){
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
//                Toast.makeText(getActivity(),index,Toast.LENGTH_LONG);
//                database.QueryData("UPDATE Status SET StatusName = '"+ status.getText().toString() +"' WHERE StatusName = '"+ listStatus.get(index).name +"' AND Created='"+ listStatus.get(index).Created+"' ");
//                database.QueryData("UPDATE Status SET Name = '"+ status.getText().toString() +"' WHERE Name = '"+ listStatus.get(index).name +"' AND Created='"+listStatus.get(index).Created+"' AND UserID="+MainActivity.IDCurrent +"");
//
//                getDataStatus();
////                listStatus.set(index, new Status(index,status.getText().toString(),listStatus.get(index).Created));
//                dialog.cancel();
//                Toast.makeText(getContext(),"Sửa thành công!",Toast.LENGTH_SHORT).show();

//                statusListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Deldialog(StatusView oldStatus, final int index){
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
//                xoa=index+1;
//                database.QueryData("DELETE FROM Status WHERE Name = '"+ listStatus.get(index).name +"' AND Created ='" + listStatus.get(index).Created +"' AND UserID="+MainActivity.IDCurrent+"");
//
////                database.QueryData("DELETE FROM Status WHERE StatusName = '"+ listStatus.get(index).name +"' AND Created='"+ listStatus.get(index).Created +"' ");
////                listStatus.remove(index);
//                getDataStatus();
//                dialog.cancel();
//                Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();

//                statusListViewAdapter.notifyDataSetChanged();
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
            Fixdialog(listStatus.get(stt),stt);
        }else if(item.getTitle()=="Xóa"){
            Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog(listStatus.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
}
