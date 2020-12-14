package com.geektech.taskapp25.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.geektech.taskapp25.MainActivity;
import com.geektech.taskapp25.R;
import com.geektech.taskapp25.interfaces.OnItemClickListener;
import com.geektech.taskapp25.ui.form.FormFragment;
import com.geektech.taskapp25.utils.Prefs;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    ArrayList<String> list = new ArrayList<>();

    //private static int REQUEST_COD = 200;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
        //ArrayList<String> list = new ArrayList<>();
        list.add("Омурзак");
        list.add("Махабат");
        list.add("Атай");
        list.add("Назар");
        list.add("Айназик");
        list.add("Эрмек");
        list.add("Данияр");
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
                final PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_clear:
                                new Prefs(getContext()).clearPrefs();
                                ((MainActivity)requireActivity()).finish();
                                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        initList();
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_formFragment);
            }
        });
        setFragmentListener();
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String text = result.getString("text");
                        adapter.addItem(text);
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
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_formFragment);
                String textList = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("text_pos", textList);
                //FormFragment formFragment = new FormFragment();
                //formFragment.setArguments(bundle);
                getParentFragmentManager().setFragmentResult("list_pos", bundle);
            }

            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Удалить этот список?");
                alert.setMessage(list.get(position));
                alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Успешно удалено", Toast.LENGTH_SHORT).show();
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