package com.example.todolistfirebase;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by best tech on 8/1/2017.
 */

public class RecycleViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = RecycleViewHolder.class.getSimpleName();
    public ImageView iconm;
    public TextView Tv;
    public ImageView deleteicon;
    private List<Task> taskObject;


    public RecycleViewHolder(final View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;
        iconm = (ImageView) itemView.findViewById(R.id.task_icon);
        Tv = (TextView) itemView.findViewById(R.id.task_title);
        deleteicon = (ImageView) itemView.findViewById(R.id.task_delete);
        deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Delete button has clicked", Toast.LENGTH_SHORT).show();
                String taskTitle = taskObject.get(getAdapterPosition()).getTask();
                Log.d(TAG, "Task Title" + taskTitle);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                Query query = databaseReference.orderByChild("task").equalTo(taskTitle);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot applesnapshot : dataSnapshot.getChildren()) {
                            applesnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });


            }
        });

    }
}
