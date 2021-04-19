package com.example.appnote.ui.gallery;

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
import com.example.appnote.R;
import com.example.appnote.Status;
import com.example.appnote.ui.status.StatusFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    ArrayList<Category> listCategory;
    CateListViewAdapter cateListViewAdapter;
    int stt;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        final ListView listCate = root.findViewById(R.id.listcCate);
        listCategory=new ArrayList<>();
        listCategory.add(new Category(1,"Word","17/04/2021 2:24:00 AM"));
        listCategory.add(new Category(2,"Study","17/04/2021 2:24:00 AM"));
        listCategory.add(new Category(3,"Relax","17/04/2021 2:24:00 AM"));
//        listStatus.add(new Status(3,"cc","17/04/2021 2:24:00 AM"));
        cateListViewAdapter= new CateListViewAdapter((listCategory));
        listCate.setAdapter(cateListViewAdapter);
        Button add = root.findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });
        listCate.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                stt=position;
                return false;
            }
        });
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        registerForContextMenu(listCate);
        setHasOptionsMenu(true);
        return root;
    }
    public static class CateListViewAdapter extends BaseAdapter {
        final ArrayList<Category> listCategory;
        public CateListViewAdapter(ArrayList<Category> listCate) {
            this.listCategory = listCate;
        }

        @Override
        public int getCount() {
            return listCategory.size();
        }

        @Override
        public Object getItem(int position) {
            return listCategory.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listCategory.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewCate;
            if(convertView == null){
                viewCate = View.inflate(parent.getContext(), R.layout.status_view,null);
            }else viewCate=convertView;
            //bind dữ liệu
            Category category =(Category) getItem(position);
            ((TextView) viewCate.findViewById(R.id.statusname)).setText(String.format("Name: %s",category.name));
            ((TextView) viewCate.findViewById(R.id.statuscreate)).setText(String.format("Created: %s",category.Createdday));
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
                listCategory.add(new Category(listCategory.size() + 1,status.getText().toString(),date + "  " + gio + ":" + phut + ":" + giay));
                dialog.cancel();
                cateListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Fixdialog(Category oldStatus, final int index){
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
                listCategory.set(index, new Category(index,status.getText().toString(),listCategory.get(index).Createdday));
                dialog.cancel();
                cateListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Deldialog(Category oldStatus, final int index){
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
                listCategory.remove(index);
                dialog.cancel();
                cateListViewAdapter.notifyDataSetChanged();
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
            Fixdialog(listCategory.get(stt),stt);
        }else if(item.getTitle()=="Xóa"){
            Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog(listCategory.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
}