package com.geektech.taskapp25.ui.form;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geektech.taskapp25.App;
import com.geektech.taskapp25.MainActivity;
import com.geektech.taskapp25.Note;
import com.geektech.taskapp25.R;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormFragment extends Fragment {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        setFragmentListener();
//        Bundle bundle = this.getArguments();
//        if (bundle != null){
//            String text = bundle.getString("text");
//            if (text !=null){
//                editText.setText(text);
//            }
//        }
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("list_pos",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = (Note) result.getSerializable("text_pos");
                        editText.setText(note.getTitle());
                    }
                });
    }


    private void save() {
        String text = editText.getText().toString();
        Log.e("FormFragment", "text = " + text);
        Bundle bundle = new Bundle();
        Note note = new Note(text, System.currentTimeMillis());
        App.dataBase.noteDao().insert(note);
        bundle.putSerializable("note", note);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
        ((MainActivity) requireActivity()).closeFragment();
    }
}