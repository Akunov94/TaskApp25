package com.geektech.taskapp25.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp25.App;
import com.geektech.taskapp25.MainActivity;
import com.geektech.taskapp25.Note;
import com.geektech.taskapp25.R;
import com.geektech.taskapp25.interfaces.OnItemClickListener;
import com.geektech.taskapp25.room.AppDataBase;
import com.geektech.taskapp25.ui.form.FormFragment;
import com.geektech.taskapp25.utils.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    AppDataBase dataBase;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Boolean toAdd;
    private int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
        List<Note> list = App.dataBase.noteDao().getAll();
        adapter.addList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.txtOptionMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenu(view);
            }
        });
        initList();
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAdd = true;
                openForm();
            }
        });
        setFragmentListener();
    }

    private void popUpMenu(View view) {
        final PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_clear) {
                    new Prefs(requireContext()).clearPrefs();
                    ((MainActivity) requireActivity()).finish();
                    Toast.makeText(requireActivity(), "Deleted", Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });
        popupMenu.show();
    }

    private void openForm(){
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_home_to_formFragment);
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = (Note) result.getSerializable("note");
                        if (toAdd)
                            adapter.addItem(note);
                        else adapter.updateItem(pos, note);
                    }
                });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(int position) {
                pos = position;
                toAdd = false;
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_formFragment);
                Bundle bundle = new Bundle();
                bundle.putSerializable("not", adapter.getItem(position));
                getParentFragmentManager().setFragmentResult("list_pos", bundle);
            }

            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Удалить этот список?");
                alert.setMessage("");
                alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        App.dataBase.noteDao().delete(adapter.getItem(position));
                        adapter.removeItem(position);
                        Toast.makeText(requireContext(), "Успешно удалено", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Отмена", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.create().show();
            }
        });
    }


}