package com.example.appnote.ui.gallery;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.CateListViewAdapter;
import com.example.appnote.CategoryView;
import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.Entity.User;
import com.example.appnote.ui.Database.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    List<Category> listCategory;
    CateListViewAdapter cateListViewAdapter;
    int stt;
    int phantutable=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        final ListView listCate = root.findViewById(R.id.listcCate);
        listCategory=new ArrayList<>() ;


        cateListViewAdapter= new CateListViewAdapter(listCategory);
        listCate.setAdapter(cateListViewAdapter);
        //Select Data
//        ContentMainNav activity = (ContentMainNav) getActivity();
//        database= activity.getMyData();
        getDataCategory(getActivity());



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
                Toast.makeText(getContext(),listCategory.get(stt).getDate(),Toast.LENGTH_LONG).show();
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
    private void getDataCategory(Context context){
      listCategory= NoteDatabase.getInstance(context).getCategoryDao().getAll();
    }
    public void Showdialog(){
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.status_add);
        dialog.show();
        Button close = (Button) dialog.findViewById(R.id.btnCancel);
        Button addStatus =(Button) dialog.findViewById(R.id.btnAddStatus);
        EditText status = (EditText) dialog.findViewById(R.id.editName);
        TextView title = (TextView) dialog.findViewById(R.id.textView2);
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
                int gio=calendar.get(Calendar.HOUR);
                int phut=calendar.get(Calendar.MINUTE);
                int giay=calendar.get(Calendar.SECOND);
//                int msgiay=calendar.get(Calendar.)
//                int msgiay=calendar.get(Calendar.AM_PM);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date=(simpleDateFormat.format(calendar.getTime()));
                String StatusName=status.getText().toString();
                if (StatusName.equals("")){
                    status.setHint("Tên không được để trống");
                }
                else {
                    try{
                        String day=date+ gio + ":" + phut + ":" + giay;
                        NoteDatabase.getInstance(getContext()).getCategoryDao().insert(new Category(status.getText().toString(),day,MainActivity.IDCurrent));

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getContext(), "OMG, không thêm được nhé!", Toast.LENGTH_SHORT).show();
                    }
//                    database.QueryData("INSERT INTO Category VALUES(null,'"+status.getText().toString()+"','"+ date+"  "+ gio + ":" + phut + ":" + giay+"',"+MainActivity.IDCurrent+")");
                    getDataCategory(getContext());
                    dialog.cancel();
                }
                dialog.cancel();
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
                Category category= NoteDatabase.getInstance(getActivity()).getCategoryDao().getCategory(listCategory.get(index).getName(),listCategory.get(index).getDate(),MainActivity.IDCurrent);
                try{
                    NoteDatabase.getInstance(getContext()).getCategoryDao().update(new Category(category.getId(),status.getText().toString(),category.getDate(),MainActivity.IDCurrent));
                    Toast.makeText(getContext(),"Sửa thành công!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getContext(), "OMG, không sửa được nhé!", Toast.LENGTH_SHORT).show();
                }
//                database.QueryData("UPDATE Category SET Name = '"+ status.getText().toString() +"' WHERE Name = '"+ listCategory.get(index).name +"' AND Created='"+listCategory.get(index).Createdday+"' AND UserID="+MainActivity.IDCurrent +"");
////                database.QueryData("UPDATE Category SET Name = '"+ status.getText().toString() +"' WHERE Name = '"+ listCategory.get(index).name +"' AND Created='"+listCategory.get(index).Createdday+"' ");
//
                getDataCategory(getContext());
                dialog.cancel();


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
                Category category= NoteDatabase.getInstance(getContext()).getCategoryDao().getCategory(listCategory.get(index).getName(),listCategory.get(index).getDate(),MainActivity.IDCurrent);
                try{
                    NoteDatabase.getInstance(getContext()).getCategoryDao().delete(category);
                    Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getContext(), "OMG, không sửa được nhé!", Toast.LENGTH_SHORT).show();
                }
////                database.QueryData("DELETE FROM Category WHERE Name = '"+ listCategory.get(index).name +"' AND Created ='" + listCategory.get(index).Createdday +"' AND UserID ="+MainActivity.IDCurrent+"");
//                database.QueryData("DELETE FROM Category WHERE Name = '"+ listCategory.get(index).name +"' AND Created ='" + listCategory.get(index).Createdday +"' AND UserID="+MainActivity.IDCurrent+"");
//
//
//
                getDataCategory(getContext());
               dialog.cancel();
//                Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();

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