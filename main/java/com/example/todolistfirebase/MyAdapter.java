package com.example.todolistfirebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by best tech on 8/1/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<RecycleViewHolder> {


    private List<Task> tasks;
    private Context context;

    public MyAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }


    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecycleViewHolder recycleViewHolder = null;
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist, parent, false);
        recycleViewHolder = new RecycleViewHolder(layout, tasks);

        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.Tv.setText(tasks.get(position).getTask());


    }


    @Override
    public int getItemCount() {
        return this.tasks.size();
    }


}


