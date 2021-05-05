package com.example.appnote.ui.status;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.ui.Adapter.StatusAdapter;
import com.example.appnote.ui.Database.Entity.Status;
import com.example.appnote.ui.Database.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.AdapterView.*;


public class StatusFragment extends Fragment {
    public static int stastt;
    private Button add;
    private StatusViewModel StatusViewModel;
    List<Status> arrayListStatus;
    StatusAdapter statusListViewAdapter;
    String date;
    int phantutable=0;

    boolean fix;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_status, container, false);
        final RecyclerView recyclerViewStatus = root.findViewById(R.id.recycle_status);
        recyclerViewStatus.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewStatus.setLayoutManager(layoutManager);
        StatusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        add = root.findViewById(R.id.btnAdd);

        arrayListStatus = new ArrayList<>();
        getDataStatus();
        StatusAdapter.OnLongClick onLongClick = null;
        statusListViewAdapter = new StatusAdapter(arrayListStatus, onLongClick, getContext());
        recyclerViewStatus.setAdapter(statusListViewAdapter);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });
        return root;
    }


    public void Showdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.show();
        TextView title = (TextView) dialog.findViewById(R.id.tvForm);
        title.setText("Priority Form");
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        addStatus.setText("Add");
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
                Status s= getStatus(StatusName);
                if (StatusName.equals("")){
                    Toast.makeText(getContext(), "Tên không để trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(s!=null)
                    {
                        Toast.makeText(getContext(), "Status đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkInsert = insertData(StatusName, date+"  "+ gio + ":" + phut + ":" + giay, MainActivity.IDCurrent);

                        if(checkInsert==true)
                        {
                            Toast.makeText(getContext(), "Đã thêm xong nhé!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Chưa thêm được nhé!", Toast.LENGTH_SHORT).show();
                        }
                        getDataStatus();
                        statusListViewAdapter.setData(arrayListStatus);
                        dialog.cancel();
                    }
                }
           }
                });
    }

    public void Fixdialog(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.setTitle("Status Form");
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        addStatus.setText("Update");
        TextView title = (TextView) dialog.findViewById(R.id.tvForm);
        title.setText("Status Form");
        final EditText status = (EditText) dialog.findViewById(R.id.editName);
        status.setText(arrayListStatus.get(stastt).getName().toString());
        dialog.show();

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
                Status s= getStatus(status.getText().toString());
                if(status.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                }
               else
                {
                    if(s!=null)
                    {
                        Toast.makeText(getContext(), "Status đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            arrayListStatus.get(stastt).setName(status.getText().toString());
                            NoteDatabase.getInstance(getContext()).getStatusDao().update(arrayListStatus.get(stastt));
                            Toast.makeText(getContext(), "Đã sửa xong nhé!", Toast.LENGTH_SHORT).show();

                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Chưa sửa được nhé!", Toast.LENGTH_SHORT).show();
                        }
                        getDataStatus();
                        statusListViewAdapter.setData(arrayListStatus);
                        dialog.cancel();
                    };
                }
            }
        });
    }
    public void Deldialog(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_delete);
        Button close = (Button) dialog.findViewById(R.id.btnNo);
        Button addStatus =(Button) dialog.findViewById(R.id.btnYes);
        dialog.show();
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
              try{

                    NoteDatabase.getInstance(getContext()).getStatusDao().delete(arrayListStatus.get(stastt));
                    Toast.makeText(getContext(), "Đã xóa xong nhé!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex){
                    Toast.makeText(getContext(), "Chưa xóa được nhé!", Toast.LENGTH_SHORT).show();
                }
                getDataStatus();
                statusListViewAdapter.setData(arrayListStatus);
                dialog.cancel();
            }
        });
    }

    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Edit"){
           // Toast.makeText(getContext(),"Sửa",Toast.LENGTH_SHORT).show();
            Fixdialog();
        }else if(item.getTitle()=="Delete"){
          //  Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog();
        }
        return super.onContextItemSelected(item);
    }
    public Boolean insertData(String name, String date, int idUser)
    {
        Status status = new Status(name, date, idUser);
        try {
            NoteDatabase.getInstance(getActivity()).getStatusDao().insert(status);
            return  true;
        }
        catch (Exception ex){
            return false;
        }
    }
    public void getDataStatus(){
        arrayListStatus.clear();
        arrayListStatus=NoteDatabase.getInstance(getContext()).getStatusDao().getAll(MainActivity.IDCurrent);
    }
    private Status getStatus(String name)
    {
        Status status= NoteDatabase.getInstance(getActivity()).getStatusDao().getStatus(name,MainActivity.IDCurrent);
        return status;
    }
}
