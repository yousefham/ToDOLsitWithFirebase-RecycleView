package com.example.todolistfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {
    private static final String TAG = Main.class.getSimpleName();
    private RecyclerView recycleView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter adapter;
    private EditText addTaskbox;
    private DatabaseReference databaseReference;
    private List<Task> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = new ArrayList<Task>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        addTaskbox = (EditText) findViewById(R.id.add_task_box);
        recycleView = (RecyclerView) findViewById(R.id.task_list);
        Button addtask = (Button) findViewById(R.id.add_task_button);
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = addTaskbox.getText().toString();
                if (TextUtils.isEmpty(task)) {
                    Toast.makeText(getApplicationContext(), "You must Enter the Task First", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (task.length() < 7) {
                    Toast.makeText(getApplicationContext(), "Task count must be more than 7", Toast.LENGTH_SHORT).show();
                    return;
                }
                Task taskopject = new Task(task);
                databaseReference.push().setValue(taskopject);
                addTaskbox.setText("");
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                TaskDel(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getAllTask(DataSnapshot dataSnapshot) {
        for (DataSnapshot singlesnap : dataSnapshot.getChildren()) {
            String taskT = singlesnap.getValue(String.class);
            taskList.add(new Task(taskT));
            adapter = new MyAdapter(taskList, Main.this);
            recycleView.setAdapter(adapter);
        }

    }

    private void TaskDel(DataSnapshot dataSnapshot) {
        for (DataSnapshot singlesnap : dataSnapshot.getChildren()) {
            String TaskT = singlesnap.getValue(String.class);
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).getTask().equals(taskList)) {
                    taskList.remove(i);
                }
            }
            Log.d(TAG, "Task tile " + TaskT);
            adapter.notifyDataSetChanged();
            adapter= new MyAdapter(taskList, Main.this);
            recycleView.setAdapter(adapter);

        }
    }
}