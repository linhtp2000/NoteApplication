package com.example.appnote.ui.changepassword;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnote.ContentMainNav;
import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.UserView;
import com.example.appnote.ui.Database.Entity.User;
import com.example.appnote.ui.Database.NoteDatabase;

public class ChangePasswordFragment extends Fragment {
    private ChangePasswordViewModel mViewModel;
   // Database db;
   // Database DB;
    UserView user = new UserView();

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
         EditText editPassOld = view.findViewById(R.id.edit_passold);
        final EditText editPassNew = view.findViewById(R.id.edit_passnew);
        final EditText editConfirmPass = view.findViewById(R.id.edit_confirmpass);

   User user=  NoteDatabase.getInstance(this.getContext()).getUserDao().getUserFromId(MainActivity.IDCurrent);
        editPassOld.setText(user.getPassword());
        Button btn_save = (Button) view.findViewById(R.id.btnChangepass);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = editPassOld.getText().toString();
                String newPass = editPassNew.getText().toString();
                String confirmPass = editConfirmPass.getText().toString();
                if(newPass.equals(confirmPass))
                {
             //    Boolean checkupdatedata = UpdatePass(new User(MainActivity.IDCurrent, user.getFirstName(),user.getLastName(),user.getEmail(),newPass));

                   try{
                       NoteDatabase.getInstance(getActivity()).getUserDao().update(new User(MainActivity.IDCurrent, user.getFirstName(),user.getLastName(),user.getEmail(),newPass));
                       Toast.makeText(getActivity(), "Chỉnh sửa xong nhé!", Toast.LENGTH_SHORT).show();
                   }
                   catch (Exception ex)
                   {
                       Toast.makeText(getActivity(), "OMG, không sửa được nhé!", Toast.LENGTH_SHORT).show();
                   }
                }
                else
                {
                    Toast.makeText(getActivity(), "Password và confirm password chưa trùng khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_home = (Button) view.findViewById(R.id.btnHomepass);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContentMainNav.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        // TODO: Use the ViewModel
    }
//    private void getDataUser(){
//        Cursor cursor = db.getUser(MainActivity.IDCurrent);
//        while (cursor.moveToNext()) {
//            user.password = cursor.getString(4);
//            break;
//        }
//        cursor.close();
//    }
}