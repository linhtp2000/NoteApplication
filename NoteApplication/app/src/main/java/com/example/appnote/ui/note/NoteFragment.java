package com.example.appnote.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.ui.Adapter.CategoryAdapter;
import com.example.appnote.MainActivity;
import com.example.appnote.NoteView;
import com.example.appnote.ui.Adapter.NoteAdapter;
import com.example.appnote.ui.Adapter.PriorityAdapter;
import com.example.appnote.R;
import com.example.appnote.ui.Adapter.StatusAdapter;
import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.Entity.Note;
import com.example.appnote.ui.Database.Entity.Priority;
import com.example.appnote.ui.Database.Entity.Status;
import com.example.appnote.ui.Database.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NoteFragment extends Fragment {

    private com.example.appnote.ui.note.NoteViewModel NoteViewModel;
    List<NoteView> listNote;
    List<Category> listCategory;
    List<Priority> listPriority;
    List<Status> listStatus;

    String[] catenameList;
    String[]prionameList;
    String[]stanameList;
    //Database database;
    public static  int notestt;
    int cate;
    int prio;
    int stat;
    int phantutable=0;
    int xoa =0;
    NoteAdapter noteListViewAdapter;
    CategoryAdapter cateListViewAdapter;
    PriorityAdapter prioListViewAdapter;
    StatusAdapter statusListViewAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_note, container, false);
        final RecyclerView recyclerViewNote = root.findViewById(R.id.recycle_note);
        recyclerViewNote.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewNote.setLayoutManager(layoutManager);
        listNote = new ArrayList<>();
        getDataNote();
        NoteAdapter.OnLongClick onLongClick = null;
        noteListViewAdapter = new NoteAdapter(listNote, onLongClick, getContext());
        recyclerViewNote.setAdapter(noteListViewAdapter);


//        listviewNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                stt=position;
//                return false;
//            }
//        });



        Button add = root.findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });


        registerForContextMenu(recyclerViewNote);
        setHasOptionsMenu(true);
        return root;
    }
    public void Showdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.note_add);
        dialog.show();

        EditText edtname = (EditText) dialog.findViewById(R.id.namenote);
        Spinner spnCate=(Spinner) dialog.findViewById(R.id.spnerCategory);
        Spinner spnPrio=(Spinner) dialog.findViewById(R.id.spnerPriority);
        Spinner spnStat=(Spinner) dialog.findViewById(R.id.spnerStatus);
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnaddStatus =(Button) dialog.findViewById(R.id.btnAdd);
        Button btnPlandate =(Button) dialog.findViewById(R.id.btnPlanDate);
        TextView tvPlanDate= (TextView) dialog.findViewById(R.id.tvplandate) ;
        tvPlanDate.setText("Select plan date");
        //Category
        listCategory = new ArrayList<>();
        getDataCategory();
        cateListViewAdapter = new CategoryAdapter(listCategory);

        catenameList= NoteDatabase.getInstance(getContext()).getCategoryDao().getCateName(MainActivity.IDCurrent);
        ArrayAdapter lstCateAdt = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,catenameList);
        lstCateAdt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnCate.setAdapter(lstCateAdt);

        //Priority
        listPriority = new ArrayList<>();
        getDataPriority();
        prioListViewAdapter = new PriorityAdapter(listPriority);

        prionameList= NoteDatabase.getInstance(getContext()).getPriorityDao().getPrioName(MainActivity.IDCurrent);
        ArrayAdapter lstPrioAdt = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,prionameList);
        lstPrioAdt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnPrio.setAdapter(lstPrioAdt);

        //Status
        listStatus = new ArrayList<>();
        getDataStatus();
        statusListViewAdapter = new StatusAdapter(listStatus);

        stanameList= NoteDatabase.getInstance(getContext()).getStatusDao().getStaName(MainActivity.IDCurrent);
        ArrayAdapter lstStaAdt = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,stanameList);
        lstStaAdt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnStat.setAdapter(lstStaAdt);

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

        btnPlandate.setOnClickListener(new View.OnClickListener() {
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
                        tvPlanDate.setText(simpleDateFormat.format(calendar.getTime()));
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
        btnaddStatus.setOnClickListener(new View.OnClickListener() {
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
                String StatusName=edtname.getText().toString();
                String day=date+"  "+ gio + ":" + phut + ":" + giay;
                if (StatusName.equals("")||tvPlanDate.getText().toString().equals("Select plan date")){
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else {
                    getDataNote();
                    noteListViewAdapter.notifyDataSetChanged();
                    Boolean checkInsert = insertData(StatusName, listCategory.get(cate).getId(), listPriority.get(prio).getId(),
                            listStatus.get(stat).getId(), day, tvPlanDate.getText().toString() , MainActivity.IDCurrent);

                    if(checkInsert==true)
                    {
                        Toast.makeText(getContext(), "Đã thêm xong nhé!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Chưa thêm được nhé!", Toast.LENGTH_SHORT).show();
                    }
                    getDataNote();
                    noteListViewAdapter.setData(listNote);
                    dialog.cancel();

                }
            }
        });
    }
    public void Fixdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.note_edit);
        dialog.show();

        Spinner spnCate=(Spinner) dialog.findViewById(R.id.spnerCategory);
        Spinner spnPrio=(Spinner) dialog.findViewById(R.id.spnerPriority);
        Spinner spnStat=(Spinner) dialog.findViewById(R.id.spnerStatus);
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button edit =(Button) dialog.findViewById(R.id.btnEdit);
        Button btnDate =(Button) dialog.findViewById(R.id.btnDate);
        TextView tvnamePlan = (TextView) dialog.findViewById(R.id.tvplandate);
        EditText edtnamenote = (EditText) dialog.findViewById(R.id.namenote);

        edtnamenote.setText(listNote.get(notestt).name);
        tvnamePlan.setText(listNote.get(notestt).planDate);

        listCategory = new ArrayList<>();
        getDataCategory();
        cateListViewAdapter = new CategoryAdapter(listCategory);
        //set data category spinner
        catenameList= NoteDatabase.getInstance(getContext()).getCategoryDao().getCateName(MainActivity.IDCurrent);
        ArrayAdapter lstCateAdt = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,listCategory);
        lstCateAdt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnCate.setAdapter(lstCateAdt);

        listPriority= new ArrayList<>();
        getDataPriority();
        prioListViewAdapter = new PriorityAdapter(listPriority);
        //set data priority spinner
        prionameList= NoteDatabase.getInstance(getContext()).getPriorityDao().getPrioName(MainActivity.IDCurrent);
        ArrayAdapter lstPrioAdt = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,prionameList);
        lstPrioAdt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnPrio.setAdapter(lstPrioAdt);

        listStatus = new ArrayList<>();
        getDataStatus();
        statusListViewAdapter = new StatusAdapter(listStatus);
        //set data status spinner
        stanameList= NoteDatabase.getInstance(getContext()).getStatusDao().getStaName(MainActivity.IDCurrent);
        ArrayAdapter lstStaAdt = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,stanameList);
        lstStaAdt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnStat.setAdapter(lstStaAdt);

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
        dialog.show();
//        status.setText(oldStatus);
        //BtnCancel
        btnDate.setOnClickListener(new View.OnClickListener() {
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
                        tvnamePlan.setText(simpleDateFormat.format(calendar.getTime()));
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
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                int gio=calendar.get(Calendar.HOUR);
//                int phut=calendar.get(Calendar.MINUTE);
//                int giay=calendar.get(Calendar.SECOND);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                String datee=(simpleDateFormat.format(calendar.getTime()));
             String StatusName=edtnamenote.getText().toString();

                if(StatusName.equals(""))
                {
                    Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    getDataNote();
                    noteListViewAdapter.notifyDataSetChanged();
                    try {
                        Note note = new Note(listNote.get(notestt).id, StatusName, listCategory.get(cate).getId(), listPriority.get(prio).getId(),
                                listStatus.get(stat).getId(),  listNote.get(notestt).createdday,tvnamePlan.getText().toString(), MainActivity.IDCurrent);
                        NoteDatabase.getInstance(getContext()).getNoteDao().update(note);
                        Toast.makeText(getContext(), "Đã sửa xong nhé!", Toast.LENGTH_SHORT).show();
                        getDataNote();
                        noteListViewAdapter.setData(listNote);
                        dialog.cancel();

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Chưa sửa được nhé!", Toast.LENGTH_SHORT).show();
                        getDataNote();
                        noteListViewAdapter.setData(listNote);
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
                getDataNote();
                noteListViewAdapter.notifyDataSetChanged();
                try{
                    Note note= new Note(listNote.get(notestt).id,listNote.get(notestt).name,listNote.get(notestt).cateid,listNote.get(notestt).prioid,
                            listNote.get(notestt).statusid,listNote.get(notestt).createdday,listNote.get(notestt).planDate,MainActivity.IDCurrent);
                    NoteDatabase.getInstance(getContext()).getNoteDao().delete(note);
                    getDataNote();
                    noteListViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Đã xóa xong nhé!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex){
                    Toast.makeText(getContext(), "Chưa xóa được nhé!", Toast.LENGTH_SHORT).show();
                }
                getDataNote();
                noteListViewAdapter.setData(listNote);
                dialog.cancel();
            }
        });
    }
    public void getDataCategory(){
        listCategory.clear();
        listCategory=NoteDatabase.getInstance(getContext()).getCategoryDao().getAll(MainActivity.IDCurrent);
    }
    public void getDataStatus(){

        listStatus.clear();
        listStatus=NoteDatabase.getInstance(getContext()).getStatusDao().getAll(MainActivity.IDCurrent);
    }
    public void getDataPriority(){
        listPriority.clear();
        listPriority=NoteDatabase.getInstance(getContext()).getPriorityDao().getAll(MainActivity.IDCurrent);
    }
    private void getDataNote(){

        listNote.clear();
        List<Note> noteList= new ArrayList();
        noteList= NoteDatabase.getInstance(getContext()).getNoteDao().getAll(MainActivity.IDCurrent);
        for(int i=0;i<noteList.size();i++)
        {
            String catename=NoteDatabase.getInstance(getContext()).getCategoryDao().getNameFromId(noteList.get(i).getCateId(),MainActivity.IDCurrent);
            String prioname=NoteDatabase.getInstance(getContext()).getPriorityDao().getNameFromId(noteList.get(i).getPrioId(),MainActivity.IDCurrent);
            String staname=NoteDatabase.getInstance(getContext()).getStatusDao().getNameFromId(noteList.get(i).getStatId(),MainActivity.IDCurrent);

            NoteView noteView= new NoteView(noteList.get(i).getId(),noteList.get(i).getName(), noteList.get(i).getCateId(),catename,noteList.get(i).getPrioId(),
                    prioname,noteList.get(i).getStatId(),staname,noteList.get(i).getDate(),noteList.get(i).getPlanDate(),noteList.get(i).getUserId());
            listNote.add(noteView);
        }
    }

    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Edit"){
          //  Toast.makeText(getContext(),"Edit",Toast.LENGTH_SHORT).show();
//            fix=true;
            Fixdialog();
        }else if(item.getTitle()=="Delete"){
          //  Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog();
        }
        return super.onContextItemSelected(item);
    }
    public Boolean insertData(String name, int cateId, int prioId, int StatId, String date, String planDate, int idUser)
    {
        Note note = new Note(name, cateId, prioId, StatId, date, planDate, idUser);
        try {
            NoteDatabase.getInstance(getActivity()).getNoteDao().insert(note);
            return  true;
        }
        catch (Exception ex){
            return false;
        }
    }

}

