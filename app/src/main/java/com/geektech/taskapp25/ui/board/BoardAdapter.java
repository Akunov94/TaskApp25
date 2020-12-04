package com.geektech.taskapp25.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp25.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();

    public BoardAdapter() {
        titles.add("Fast");
        titles.add("Free");
        titles.add("Powerful");

        desc.add("Fast Fast Fast Fast Fast Fast Fast Fast Fast Fast Fast Fast Fast Fast");
        desc.add("Free");
        desc.add("Powerful");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
        }

        public void bind(int position) {
            textTitle.setText(titles.get(position));
            textDesc.setText(desc.get(position));
        }
    }
}
