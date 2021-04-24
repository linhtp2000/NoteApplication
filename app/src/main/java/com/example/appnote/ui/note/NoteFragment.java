package com.example.appnote.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.CateListViewAdapter;
import com.example.appnote.Category;
import com.example.appnote.ContentMainNav;
import com.example.appnote.Database;
import com.example.appnote.MainActivity;
import com.example.appnote.Note;
import com.example.appnote.NoteListViewAdapter;
import com.example.appnote.PrioListViewAdapter;
import com.example.appnote.Priority;
import com.example.appnote.R;
import com.example.appnote.Status;
import com.example.appnote.StatusListViewAdapter;
import com.example.appnote.ui.note.NoteViewModel;
import com.example.appnote.ui.status.StatusFragment;
import com.example.appnote.ui.status.StatusViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class NoteFragment extends Fragment {

    private com.example.appnote.ui.note.NoteViewModel NoteViewModel;
    ArrayList<Note> listNotee;
    ArrayList<Category> listCategory;
    ArrayList<Priority> listPriority;
    ArrayList<Status> listStatus;
    Database database;
    int stt;
    int cate;
    int prio;
    int stat;
    int phantutable=0;
    int xoa =0;
    NoteListViewAdapter noteListViewAdapter;
    CateListViewAdapter cateListViewAdapter;
    PrioListViewAdapter prioListViewAdapter;
    StatusListViewAdapter statusListViewAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_note, container, false);
        final ListView listviewNote = root.findViewById(R.id.listNote);
        listNotee=new ArrayList<>();
        noteListViewAdapter = new NoteListViewAdapter(listNotee);
        listviewNote.setAdapter(noteListViewAdapter);

        ContentMainNav activity = (ContentMainNav) getActivity();
        database= activity.getMyData();
        getDataNote();

        listviewNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                stt=position;
                return false;
            }
        });



        Button add = root.findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });


        registerForContextMenu(listviewNote);
        setHasOptionsMenu(true);
        return root;
    }
    public void Showdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.note_add);
        dialog.show();
        Spinner spnCate=(Spinner) dialog.findViewById(R.id.spnerCategory);
        Spinner spnPrio=(Spinner) dialog.findViewById(R.id.spnerPriority);
        Spinner spnStat=(Spinner) dialog.findViewById(R.id.spnerStatus);
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAdd);
        Button date =(Button) dialog.findViewById(R.id.btnDate);
        listCategory=new ArrayList<>();
        cateListViewAdapter=new CateListViewAdapter(listCategory);
        getDataCategory();

        listPriority=new ArrayList<>();
        prioListViewAdapter=new PrioListViewAdapter(listPriority);
        getDataPriority();

        listStatus=new ArrayList<>();
        statusListViewAdapter=new StatusListViewAdapter(listStatus);
        getDataStatus();

          spnCate.setAdapter(cateListViewAdapter);
        spnPrio.setAdapter(prioListViewAdapter);
        spnStat.setAdapter(statusListViewAdapter);

        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnPrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prio=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stat=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText namePlan = (EditText) dialog.findViewById(R.id.nameplan);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay=calendar.get(Calendar.DATE);
                int thang=calendar.get(Calendar.MONTH);
                int nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam,thang,ngay);
                datePickerDialog.show();
            }
        });
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
                String datee=(simpleDateFormat.format(calendar.getTime()));

                String StatusName=namePlan.getText().toString();
                if (StatusName.equals("")){
                    namePlan.setHint("Tên không được để trống");
                }
                else {
//                    database.QueryData("INSERT INTO Category VALUES(null,'"+status.getText().toString()+"','"+ date+"  "+ gio + ":" + phut + ":" + giay+"',"+MainActivity.IDCurrent+")");
                    database.QueryData("INSERT INTO Notes VALUES(null,'" + namePlan.getText().toString() + "','" + listCategory.get(cate).id + "','" + listPriority.get(prio).id + "','" + listStatus.get(stat).id + "','" + date.getText().toString() +"','"+ datee+"  "+ gio + ":" + phut + ":" + giay +"',"+MainActivity.IDCurrent+")");

//                    database.QueryData("INSERT INTO Notes VALUES(null,'" + namePlan.getText().toString() + "','" +listCategory.get(cate).name + "','" + listPriority.get(prio).name+ "','" + listStatus.get(stat).name+ "','" + date.getText().toString() +"','"+ datee+"  "+ gio + ":" + phut + ":" + giay +"',"+MainActivity.IDCurrent+")");
                    getDataNote();
//                    if(xoa!=0){
//                        database.QueryData("INSERT INTO Notes VALUES("+ (xoa) + ",'" + namePlan.getText().toString() + "','" + listCategory.get(cate).name + "','" + listPriority.get(prio).name + "','" + listStatus.get(stat).name + "','" + date.getText().toString() +"','"+ datee+"  "+ gio + ":" + phut + ":" + giay +"')");
////                        database.QueryData("INSERT INTO Priority VALUES("+ (xoa) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");
//                        getDataNote();
//                        xoa=0;
//                    }else if(xoa==0){
//                        database.QueryData("INSERT INTO Notes VALUES("+listNotee.size()+1 + ",'" + namePlan.getText().toString() + "','" + listCategory.get(cate).name + "','" + listPriority.get(prio).name + "','" + listStatus.get(stat).name + "','" + date.getText().toString() +"','"+ datee+"  "+ gio + ":" + phut + ":" + giay +"')");
////                        database.QueryData("INSERT INTO Priority VALUES("+ (listPriority.size() + 1) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");
//                        getDataNote();
//                    }

//                    database.QueryData("INSERT INTO Category VALUES("+ (phantutable+1) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");




//                    database.QueryData("INSERT INTO Status VALUES("+ (listStatus.size()+1) +",'"+status.getText().toString() +"','"+ date+"  "+ gio + ":" + phut + ":" + giay +"')");
////                    listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),date + "  " + gio + ":" + phut + ":" + giay));
//                    getDataStatus();
                    dialog.cancel();
//                    statusListViewAdapter.notifyDataSetChanged();
                }


//                listNotee.add(new Note(listStatus.size() + 1,namePlan.getText().toString(),listCategory.get(cate).name,listPriority.get(prio).name,listStatus.get(stat).name,date.getText().toString(),datee + "  " + gio + ":" + phut + ":" + giay));
//                dialog.cancel();
//                noteListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Fixdialog(Note oldStatus, final int index){
//        Showdialog();
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.note_add);
        dialog.setTitle("Update Note");
        EditText note = (EditText) dialog.findViewById(R.id.nameplan);
        Spinner spnCate=(Spinner) dialog.findViewById(R.id.spnerCategory);
        Spinner spnPrio=(Spinner) dialog.findViewById(R.id.spnerPriority);
        Spinner spnStat=(Spinner) dialog.findViewById(R.id.spnerStatus);
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAdd);
        Button date =(Button) dialog.findViewById(R.id.btnDate);
        listCategory=new ArrayList<>();
        cateListViewAdapter=new CateListViewAdapter(listCategory);
        getDataCategory();


        listPriority=new ArrayList<>();
        prioListViewAdapter=new PrioListViewAdapter(listPriority);
        getDataPriority();


        listStatus=new ArrayList<>();
        statusListViewAdapter=new StatusListViewAdapter(listStatus);
        getDataStatus();
//        ArrayAdapter okay=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listCate);
//        spnCate.setAdapter(okay);
        spnCate.setAdapter(cateListViewAdapter);
        spnPrio.setAdapter(prioListViewAdapter);
        spnStat.setAdapter(statusListViewAdapter);

        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cate=0;
            }
        });

        spnPrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prio=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                prio=0;

            }
        });
        spnStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stat=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stat=0;
            }
        });
        dialog.show();
//        status.setText(oldStatus);
        //BtnCancel
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay=calendar.get(Calendar.DATE);
                int thang=calendar.get(Calendar.MONTH);
                int nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam,thang,ngay);
                datePickerDialog.show();
            }
        });
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
//                String catename=getNameFromId(listCategory.get(cate).id,"Category");
//                String prioname=getNameFromId(listPriority.get(index).id,"Priority");
//                    String statusname=getNameFromId(listStatus.get(index).id,"Status");

                database.QueryData("UPDATE Notes SET Name = '"+ note.getText().toString() +"' WHERE Name = '"+ listNotee.get(index).name +"' AND Created ='"+ listNotee.get(index).Createdday+"' AND UserID="+ MainActivity.IDCurrent+" ");
                database.QueryData("UPDATE Notes SET CateID = '"+ listCategory.get(cate).id +"' WHERE Name = '"+ listNotee.get(index).name +"' AND Created='"+listNotee.get(index).Createdday +"' AND UserID="+MainActivity.IDCurrent+"");
                database.QueryData("UPDATE Notes SET PrioID = '"+ listPriority.get(prio).id +"' WHERE Name = '"+ listNotee.get(index).name +"' AND Created = '"+listNotee.get(index).Createdday +"' AND UserID="+MainActivity.IDCurrent+"");
                database.QueryData("UPDATE Notes SET StaID = '"+ listStatus.get(stat).id +"' WHERE Name = '"+ listNotee.get(index).name +"' AND Created = '"+listNotee.get(index).Createdday+"' AND UserID="+MainActivity.IDCurrent+"");
                database.QueryData("UPDATE Notes SET Planday = '"+ date.getText().toString() +"' WHERE Name = '"+ listNotee.get(index).name +"' AND Created = '"+listNotee.get(index).Createdday+"' AND UserID="+MainActivity.IDCurrent+"");


                getDataNote();
//                listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),"17/04/2021 2:24:00 AM"));
                //Cập nhật listview
//                listNotee.set(index, new Note(listNotee.size() + 1,note.getText().toString(),listCategory.get(cate).name,listPriority.get(prio).name,listStatus.get(stat).name,date.getText().toString(),listNotee.get(index).Createdday));
                dialog.cancel();
                Toast.makeText(getContext(),"Sửa thành công!",Toast.LENGTH_SHORT).show();

//                noteListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Deldialog(Note oldStatus, final int index){
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
//                listNotee.remove(index);
                xoa=index+1;
                database.QueryData("DELETE FROM Notes WHERE Name = '"+ listNotee.get(index).name +"' AND Created='"+ listNotee.get(index).Createdday +"' AND UserID="+MainActivity.IDCurrent +"");

//                database.QueryData("DELETE FROM Notes WHERE Name = '"+ listNotee.get(index).name +"' AND Created='"+ listNotee.get(index).Createdday +"' ");
                getDataNote();
                dialog.cancel();
                Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();

//                noteListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getDataCategory(){
        Cursor dataCategory=database.GetData("SELECT * FROM Category where UserID ="+MainActivity.IDCurrent+"");
        listCategory.clear();
        while (dataCategory.moveToNext()){
            String name = dataCategory.getString(1);
            String created = dataCategory.getString(2);
            int id=dataCategory.getInt(0);
            listCategory.add(new Category(id,name,created));
            cateListViewAdapter.notifyDataSetChanged();
//            database =dataStatus.getString(1);
//            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
        }
    }
    private void getDataStatus(){
        Cursor dataStatus=database.GetData("SELECT * FROM Status where UserID ="+MainActivity.IDCurrent+"");
        listStatus.clear();
        while (dataStatus.moveToNext()){
            String name = dataStatus.getString(1);
            String created = dataStatus.getString(2);
            int id=dataStatus.getInt(0);
            listStatus.add(new Status(id,name,created));
            statusListViewAdapter.notifyDataSetChanged();
//            database =dataStatus.getString(1);
//            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
        }
    }
    private void getDataPriority(){
        Cursor dataPriority=database.GetData("SELECT * FROM Priority where UserID ="+MainActivity.IDCurrent+"");
        listPriority.clear();
        while (dataPriority.moveToNext()){
            String name = dataPriority.getString(1);
            String created = dataPriority.getString(2);
            int id=dataPriority.getInt(0);
            listPriority.add(new Priority(id,name,created));
            prioListViewAdapter.notifyDataSetChanged();
//            database =dataStatus.getString(1);
//            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
        }
    }
    private void getDataNote(){
        Cursor dataNote=database.GetData("SELECT * FROM Notes where UserID ="+MainActivity.IDCurrent+"");
        listNotee.clear();
        while (dataNote.moveToNext()){
            int id=dataNote.getInt(0);
            String name = dataNote.getString(1);
            int cid = dataNote.getInt(2);
            int pid = dataNote.getInt(3);
            int sid = dataNote.getInt(4);
           String category = getNameFromId(cid,"Category");
           String priority = getNameFromId(pid,"Priority");
           String status = getNameFromId(sid,"Status");
            String plandate = dataNote.getString(5);
            String create = dataNote.getString(6);
           listNotee.add(new Note(id,name,category,priority,status,plandate,create));

           noteListViewAdapter.notifyDataSetChanged();
//            database =dataStatus.getString(1);
//            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
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
            Fixdialog(listNotee.get(stt),stt);
        }else if(item.getTitle()=="Xóa"){
            Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog(listNotee.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
    private String getNameFromId( int id, String nametable)
    {
        String query = "SELECT * FROM " + nametable+" WHERE Id= "+id+";";
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String name="";
        while (cursor.moveToNext()) {
            name = cursor.getString(1);
            break;
        }
        cursor.close();
        db.close();
        return name;
    }

}

