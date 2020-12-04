package com.geektech.taskapp25.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geektech.taskapp25.MainActivity;
import com.geektech.taskapp25.R;


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
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        String text = editText.getText().toString();
        Log.e("FormFragment", "text = " + text);
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
        ((MainActivity) requireActivity()).closeFragment();
    }
}