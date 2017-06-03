package com.bachelorshelter.tanvir.cutmytask.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bachelorshelter.tanvir.cutmytask.R;
import com.bachelorshelter.tanvir.cutmytask.model.Task;
import com.bachelorshelter.tanvir.cutmytask.helper.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    // Database Helper
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = new DatabaseHandler(getApplicationContext());
        //System.out.println("Date: "+getDateTime());//Date: 2017-06-01 03:57:56

        Task task1 = new Task("2017-06-03 01:00:00","Date 3","#f8bbd0",1);
        Task task2 = new Task("2017-06-01 22:59:59","Date 1","#dcedc8",0);
        Task task3 = new Task("2017-06-02 20:59:59","OS Class","#f8bbd0",0);
        Task task4 = new Task("2017-06-02 22:59:59","DBMS Class","#dcedc8",0);

        long task_id1 = db.addTask(task1);
        long task_id2 = db.addTask(task2);
        long task_id3 = db.addTask(task3);
        long task_id4 = db.addTask(task4);

        List<Task> result = db.getAllTask();
        for (Task task : result) {
            Log.d("Task:id: ", String.valueOf(task.getId()));
            Log.d("Task:task: ", task.getTaskDesc());
            Log.d("Task:date: ", task.getDate());
            Log.d("Task:back: ", task.getBackColor());
        }
        db.closeDB();


        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



}
