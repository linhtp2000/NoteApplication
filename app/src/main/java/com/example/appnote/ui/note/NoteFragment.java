package com.example.appnote.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.example.appnote.Category;
import com.example.appnote.Note;
import com.example.appnote.Priority;
import com.example.appnote.R;
import com.example.appnote.Status;
import com.example.appnote.ui.note.NoteViewModel;
import com.example.appnote.ui.status.StatusFragment;
import com.example.appnote.ui.status.StatusViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NoteFragment extends Fragment {

    private com.example.appnote.ui.note.NoteViewModel NoteViewModel;
    ArrayList<Note> listNotee;
    ArrayList<Category> listCategory;
    ArrayList<Priority> listPrio;
    ArrayList<Status> listStatus;
    int stt;
    int cate;
    int prio;
    int stat;
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

        listNotee.add(new Note(1,"Soccer","Relax","dsadsad","Comming","19/04/2021","19/04/2021"));
        listNotee.add(new Note(2,"None","Study","dsadsad","Comming","19/04/2021","19/04/2021"));
        noteListViewAdapter = new NoteListViewAdapter(listNotee);
        listviewNote.setAdapter(noteListViewAdapter);


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
        listCategory.add(new Category(1,"Word","17/04/2021 2:24:00 AM"));
        listCategory.add(new Category(2,"Study","17/04/2021 2:24:00 AM"));
        listCategory.add(new Category(3,"Relax","17/04/2021 2:24:00 AM"));
        cateListViewAdapter=new CateListViewAdapter(listCategory);
        listPrio=new ArrayList<>();
        listPrio.add(new Priority(1,"High","17/04/2021 2:24:00 AM"));
        listPrio.add(new Priority(2,"Medium","17/04/2021 2:24:00 AM"));
        listPrio.add(new Priority(3,"Slow","17/04/2021 2:24:00 AM"));
        prioListViewAdapter=new PrioListViewAdapter(listPrio);
        listStatus=new ArrayList<>();
        listStatus.add(new Status(1,"Done","17/04/2021 2:24:00 AM"));
        listStatus.add(new Status(2,"Coming","17/04/2021 2:24:00 AM"));
        listStatus.add(new Status(3,"Pending","17/04/2021 2:24:00 AM"));
        statusListViewAdapter=new StatusListViewAdapter(listStatus);
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
                listNotee.add(new Note(listStatus.size() + 1,namePlan.getText().toString(),listCategory.get(cate).name,listPrio.get(prio).name,listStatus.get(stat).name,date.getText().toString(),datee + "  " + gio + ":" + phut + ":" + giay));
                dialog.cancel();
                noteListViewAdapter.notifyDataSetChanged();
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
        listCategory.add(new Category(1,"Word","17/04/2021 2:24:00 AM"));
        listCategory.add(new Category(2,"Study","17/04/2021 2:24:00 AM"));
        listCategory.add(new Category(3,"Relax","17/04/2021 2:24:00 AM"));
        cateListViewAdapter=new CateListViewAdapter(listCategory);
        listPrio=new ArrayList<>();
        listPrio.add(new Priority(1,"High","17/04/2021 2:24:00 AM"));
        listPrio.add(new Priority(2,"Medium","17/04/2021 2:24:00 AM"));
        listPrio.add(new Priority(3,"Slow","17/04/2021 2:24:00 AM"));
        prioListViewAdapter=new PrioListViewAdapter(listPrio);
        listStatus=new ArrayList<>();
        listStatus.add(new Status(1,"Done","17/04/2021 2:24:00 AM"));
        listStatus.add(new Status(2,"Coming","17/04/2021 2:24:00 AM"));
        listStatus.add(new Status(3,"Pending","17/04/2021 2:24:00 AM"));
        statusListViewAdapter=new StatusListViewAdapter(listStatus);
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
//                listStatus.add(new Status(listStatus.size() + 1,status.getText().toString(),"17/04/2021 2:24:00 AM"));
                //Cập nhật listview
                listNotee.set(index, new Note(listNotee.size() + 1,note.getText().toString(),listCategory.get(cate).name,listPrio.get(prio).name,listStatus.get(stat).name,date.getText().toString(),listNotee.get(index).Createdday));
                dialog.cancel();
                noteListViewAdapter.notifyDataSetChanged();
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
                listNotee.remove(index);
                dialog.cancel();
                noteListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    //Tạo adapter
    public class NoteListViewAdapter extends BaseAdapter {
        final ArrayList<Note> listNote;
        NoteListViewAdapter(ArrayList<Note> listNote){
            this.listNote=listNote;
        }
        @Override
        public int getCount() {
            return listNote.size();
        }

        @Override
        public Object getItem(int position) {
            return listNote.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listNote.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewNote;
            if(convertView == null){
                viewNote = View.inflate(parent.getContext(), R.layout.note_view,null);
            }else viewNote=convertView;
            //bind dữ liệu
            Note note =(Note) getItem(position);
            ((TextView) viewNote.findViewById(R.id.notename)).setText(String.format("Name: %s",note.name));
            ((TextView) viewNote.findViewById(R.id.notecategory)).setText(String.format("Category: %s",note.category));
            ((TextView) viewNote.findViewById(R.id.notepriority)).setText(String.format("Priority: %s",note.priority));
            ((TextView) viewNote.findViewById(R.id.notestatus)).setText(String.format("Status: %s",note.status));
            ((TextView) viewNote.findViewById(R.id.noteplan)).setText(String.format("Plan date: %s",note.planDate));
            ((TextView) viewNote.findViewById(R.id.notecreate)).setText(String.format("Created Date: %s",note.Createdday));
            return viewNote;
        }
    }
    public class CateListViewAdapter extends BaseAdapter {
        final ArrayList<Category> listCate;
        CateListViewAdapter(ArrayList<Category> listCate){
            this.listCate=listCate;
        }
        @Override
        public int getCount() {
            return listCate.size();
        }

        @Override
        public Object getItem(int position) {
            return listCate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listCate.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewCate;
            if(convertView == null){
                viewCate = View.inflate(parent.getContext(), R.layout.category_view,null);
            }else viewCate=convertView;
            //bind dữ liệu
            Category cate =(Category) getItem(position);
            ((TextView) viewCate.findViewById(R.id.categoryName)).setText(String.format(cate.name));
            return viewCate;
        }

    }
    public class PrioListViewAdapter extends BaseAdapter {
        final ArrayList<Priority> listPrio;
        PrioListViewAdapter(ArrayList<Priority> listPrio){
            this.listPrio=listPrio;
        }
        @Override
        public int getCount() {
            return listPrio.size();
        }

        @Override
        public Object getItem(int position) {
            return listPrio.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listPrio.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewPrio;
            if(convertView == null){
                viewPrio = View.inflate(parent.getContext(), R.layout.category_view,null);
            }else viewPrio=convertView;
            //bind dữ liệu
            Priority prio =(Priority) getItem(position);
            ((TextView) viewPrio.findViewById(R.id.categoryName)).setText(String.format(prio.name));
            return viewPrio;
        }

    }
    public class StatusListViewAdapter extends BaseAdapter {
        final ArrayList<Status> listStatus;
        StatusListViewAdapter(ArrayList<Status> listStatus){
            this.listStatus=listStatus;
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
            View viewStat;
            if(convertView == null){
                viewStat = View.inflate(parent.getContext(), R.layout.category_view,null);
            }else viewStat=convertView;
            //bind dữ liệu
            Status stat =(Status) getItem(position);
            ((TextView) viewStat.findViewById(R.id.categoryName)).setText(String.format(stat.name));
            return viewStat;
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

}

