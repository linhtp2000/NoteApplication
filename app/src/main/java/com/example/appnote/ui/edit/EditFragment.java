package com.example.appnote.ui.edit;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appnote.R;
import com.example.appnote.ui.edit.EditViewModel;
import com.example.appnote.ui.slideshow.SlideshowViewModel;

public class EditFragment extends Fragment {
    private com.example.appnote.ui.edit.EditViewModel EditViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditViewModel =
                new ViewModelProvider(this).get(EditViewModel.class);
        View root = inflater.inflate(R.layout.fragment_note, container, false);
        final TextView textView = root.findViewById(R.id.text_note);
        EditViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}