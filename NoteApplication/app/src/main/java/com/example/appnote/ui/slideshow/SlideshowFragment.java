package com.example.appnote.ui.slideshow;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.appnote.ui.Adapter.PriorityAdapter;
import com.example.appnote.R;
import com.example.appnote.ui.Database.Entity.Priority;
import com.example.appnote.ui.Database.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private Button add;
    private SlideshowViewModel slideshowViewModel;
    List<Priority> arrayListPrio;
    PriorityAdapter prioListViewAdapter;
    public static int priostt;

    int xoa=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final RecyclerView recyclerViewPrio = root.findViewById(R.id.recycle_prio);
        recyclerViewPrio.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPrio.setLayoutManager(layoutManager);
        add = root.findViewById(R.id.btnAdd);
        arrayListPrio = new ArrayList<>();
        getDataPriority();
        PriorityAdapter.OnLongClick onLongClick = null;
        prioListViewAdapter = new PriorityAdapter(arrayListPrio, onLongClick, getContext());
        recyclerViewPrio.setAdapter(prioListViewAdapter);
  //      getDataPriority();

        Button add = root.findViewById(R.id.btnAdd);
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
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        addStatus.setText("Add");
        EditText status = (EditText) dialog.findViewById(R.id.editName);
        TextView title = (TextView) dialog.findViewById(R.id.tvForm);
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date=(simpleDateFormat.format(calendar.getTime()));
                String StatusName=status.getText().toString();
                Priority p=getPriority(status.getText().toString());
                if(status.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (p != null) {
                        Toast.makeText(getContext(), "Status đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkInsert = insertData(StatusName, date + "  " + gio + ":" + phut + ":" + giay, MainActivity.IDCurrent);
                        getDataPriority();
                        if (checkInsert == true) {
                            Toast.makeText(getContext(), "Đã thêm xong nhé!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Chưa thêm được nhé!", Toast.LENGTH_SHORT).show();
                        }
                        getDataPriority();
                        prioListViewAdapter.setData(arrayListPrio);
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
        final EditText status = (EditText) dialog.findViewById(R.id.editName);
        status.setText(arrayListPrio.get(priostt).getName().toString());
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
                Priority p=getPriority(status.getText().toString());
                if(status.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (p != null) {
                        Toast.makeText(getContext(), "Status đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            arrayListPrio.get(priostt).setName(status.getText().toString());
                            NoteDatabase.getInstance(getContext()).getPriorityDao().update(arrayListPrio.get(priostt));
//                    NoteDatabase.getInstance(getContext()).getPriorityDao().update(arrayListPrio.get(stt));
                            Toast.makeText(getContext(), "Đã sửa xong nhé!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Chưa sửa được nhé!", Toast.LENGTH_SHORT).show();
                        }
                        getDataPriority();
                        prioListViewAdapter.setData(arrayListPrio);
                        dialog.cancel();
                    }
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
             //   xoa=index+1;
                try{
                    NoteDatabase.getInstance(getContext()).getPriorityDao().delete(arrayListPrio.get(priostt));
                    Toast.makeText(getContext(), "Đã xóa xong nhé!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex){
                    Toast.makeText(getContext(), "Chưa xóa được nhé!", Toast.LENGTH_SHORT).show();
                }
                getDataPriority();
                prioListViewAdapter.setData(arrayListPrio);
                dialog.cancel();
            }
        });
    }
    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Edit"){
          //  Toast.makeText(getContext(),"Sửa",Toast.LENGTH_SHORT).show();
            Fixdialog();
        }else if(item.getTitle()=="Delete"){
         //   Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog();
        }
        return super.onContextItemSelected(item);
    }
    public Boolean insertData(String name, String date, int idUser)
    {
        Priority priority = new Priority(name, date, idUser);
        try {
            NoteDatabase.getInstance(getActivity()).getPriorityDao().insert(priority);
            return  true;
        }
        catch (Exception ex){
            return false;
        }
    }
    public void getDataPriority(){
        arrayListPrio.clear();
        arrayListPrio=NoteDatabase.getInstance(getContext()).getPriorityDao().getAll(MainActivity.IDCurrent);
    }
    private Priority getPriority(String name)
    {
        Priority priority = NoteDatabase.getInstance(getActivity()).getPriorityDao().getPriority(name,MainActivity.IDCurrent);
        return priority;
    }
}