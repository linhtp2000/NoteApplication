package com.example.appnote.ui.gallery;

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

import com.example.appnote.ui.Adapter.CategoryAdapter;
import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GalleryFragment extends Fragment {

    public static int catestt;
    private GalleryViewModel galleryViewModel;
  //  private Button add;
    List<Category> arrayListCate;
    CategoryAdapter cateListViewAdapter;
//    int stt;
    int phantutable=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final RecyclerView recyclerViewCate = root.findViewById(R.id.recycle_category);
        recyclerViewCate.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCate.setLayoutManager(layoutManager);
      //  add = root.findViewById(R.id.btnAdd);

        arrayListCate = new ArrayList<>();
        getDataCategory();
        CategoryAdapter.OnLongClick onLongClick = null;
        cateListViewAdapter = new CategoryAdapter(arrayListCate, onLongClick, getContext());
        recyclerViewCate.setAdapter(cateListViewAdapter);



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
        title.setText("Category Form");
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
                int gio = calendar.get(Calendar.HOUR);
                int phut = calendar.get(Calendar.MINUTE);
                int giay = calendar.get(Calendar.SECOND);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = (simpleDateFormat.format(calendar.getTime()));
                String StatusName = status.getText().toString();
                Category c = getCatory(status.getText().toString());
                if (status.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    if (c != null) {
                        Toast.makeText(getContext(), "Status đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkInsert = insertData(StatusName, date + "  " + gio + ":" + phut + ":" + giay, MainActivity.IDCurrent);
                        //   getDataCategory();
                        if (checkInsert == true) {
                            Toast.makeText(getContext(), "Đã thêm xong nhé!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Chưa thêm được nhé!", Toast.LENGTH_SHORT).show();
                        }

                        getDataCategory();
                        cateListViewAdapter.setData(arrayListCate);
                        dialog.cancel();
                    }
                }
            }
        });
    }
    public void Fixdialog(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.setTitle("Update Status");
        TextView tvForm= (TextView) dialog.findViewById(R.id.tvForm);
        tvForm.setText("Category Form");
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        addStatus.setText("Update");
        final EditText status = (EditText) dialog.findViewById(R.id.editName);
        status.setText(arrayListCate.get(catestt).getName().toString());
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
                Category c=getCatory(status.getText().toString());
                if(status.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(c!=null)
                    {
                        Toast.makeText(getContext(), "Status đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            arrayListCate.get(catestt).setName(status.getText().toString());
                            NoteDatabase.getInstance(getContext()).getCategoryDao().update(arrayListCate.get(catestt));
                            Toast.makeText(getContext(), "Đã sửa xong nhé!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Chưa sửa được nhé!", Toast.LENGTH_SHORT).show();
                        }
                        getDataCategory();
                        cateListViewAdapter.setData(arrayListCate);
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
                try{

                    NoteDatabase.getInstance(getContext()).getCategoryDao().delete(arrayListCate.get(catestt));
                    Toast.makeText(getContext(), "Đã xóa xong nhé!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex){
                    Toast.makeText(getContext(), "Chưa xóa được nhé!", Toast.LENGTH_SHORT).show();
                }
                getDataCategory();
                cateListViewAdapter.setData(arrayListCate);
                dialog.cancel();

            }
        });
    }
    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Edit"){
            //Toast.makeText(getContext(),"Sửa",Toast.LENGTH_SHORT).show();
//            fix=true;
            Fixdialog();
        }
        else if(item.getTitle()=="Delete"){
       //     Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
            Deldialog();
        }
        return super.onContextItemSelected(item);
    }
    public Boolean insertData(String name, String date, int idUser)
    {
        Category category = new Category(name, date, idUser);
        try {
            NoteDatabase.getInstance(getActivity()).getCategoryDao().insert(category);
            return  true;
        }
        catch (Exception ex){
            return false;
        }
    }
    public void getDataCategory(){
            arrayListCate.clear();
            arrayListCate=NoteDatabase.getInstance(getActivity()).getCategoryDao().getAll(MainActivity.IDCurrent);

    }
    private Category getCatory(String name)
    {
       Category category = NoteDatabase.getInstance(getActivity()).getCategoryDao().getCategory(name,MainActivity.IDCurrent);
        return category;
    }
}