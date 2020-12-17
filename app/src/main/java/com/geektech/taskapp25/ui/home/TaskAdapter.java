package com.geektech.taskapp25.ui.home;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp25.App;
import com.geektech.taskapp25.MainActivity;
import com.geektech.taskapp25.Note;
import com.geektech.taskapp25.R;
import com.geektech.taskapp25.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Note> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TaskAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public void addList(List<Note> list) {
        this.list = (ArrayList<Note>) list;
        notifyDataSetChanged();

    }

    public void addItem(Note note) {
        list.add(note);
        notifyItemInserted(list.size() - 1);
    }

    public void updateItem(int pos, Note note) {
        list.set(pos, note);
        notifyItemChanged(pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView textTimeCreateAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textTimeCreateAt = itemView.findViewById(R.id.textTimeCreateAt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition(),list.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(getAdapterPosition(),list.get(getAdapterPosition()));
                    return true;
                }
            });
        }

        public void bind(Note note) {
            String time = (String) DateFormat.format("HH:mm dd MMM yyyy", new Date(note.getCreateAt()));
            textTimeCreateAt.setText(time);
            textView.setText(note.getTitle());

        }
    }
}
