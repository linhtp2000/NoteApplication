package com.example.appnote.ui.editprofile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnote.ContentMainNav;
import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.ui.Database.Entity.User;
import com.example.appnote.ui.Database.NoteDatabase;

public class EditProfileFragment extends Fragment {

    private EditViewModel mViewModel;
//    Database db;
//    Database DB;
    String getMail;
    //User user = new UserView();
    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        final EditText editMail = view.findViewById(R.id.edit_mail);
        final EditText editFirstName = view.findViewById(R.id.edit_firstName);
        final EditText editLastName = view.findViewById(R.id.edit_lastName);

        User user=  NoteDatabase.getInstance(this.getContext()).getUserDao().getUserFromId(MainActivity.IDCurrent);

        editMail.setText(user.getEmail());
        editFirstName.setText(user.getFirstName());
        editLastName.setText(user.getLastName());
        Button btn_save = (Button) view.findViewById(R.id.btnChange);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString().trim();
                String lastName = editLastName.getText().toString().trim();
                String email = editMail.getText().toString().trim();

                try{
                    NoteDatabase.getInstance(getActivity()).getUserDao().update(new User(MainActivity.IDCurrent, firstName,lastName,email,user.getPassword()));
                    Toast.makeText(getActivity(), "Ch???nh s???a xong nh??!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(), "OMG, kh??ng s???a ???????c nh??!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_home = (Button) view.findViewById(R.id.btnHome);
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
        mViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        // TODO: Use the ViewModel
    }
}