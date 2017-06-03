package com.bachelorshelter.tanvir.cutmytask.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.bachelorshelter.tanvir.cutmytask.R;
import com.bachelorshelter.tanvir.cutmytask.helper.DatabaseHandler;
import com.bachelorshelter.tanvir.cutmytask.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TaskEditActivity extends AppCompatActivity {

    private EditText time,date,task;
    private TextInputLayout taskLayout,timeLayout;
    private Calendar calendar;
    private DatabaseHandler db;
    private String[] color = {"#ffcdd2",
            "#f8bbd0",
            "#e1bee7",
            "#d1c4e9",
            "#c5cae9",
            "#bbdefb",
            "#b3e5fc",
            "#b2ebf2",
            "#b2dfdb",
            "#c8e6c9",
            "#dcedc8",
            "#f0f4c3",
            "#fff9c4",
            "#ffecb3",
            "#ffe0b2",
            "#ffccbc"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        calendar = Calendar.getInstance();

        //getSupportActionBar().setTitle("Register a Shelter");
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        timeLayout = (TextInputLayout)findViewById(R.id.textInputLayoutTime);
        taskLayout = (TextInputLayout)findViewById(R.id.textInputLayoutTask);
        task = (EditText)findViewById(R.id.task);
        date = (EditText)findViewById(R.id.date);
        time = (EditText)findViewById(R.id.time);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dates = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        date.setText(dates);
                    }


                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        time.setText(i + ":" + i1);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });


    }


    private String properDateTime(String date,String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date formated  = null;
        String unFormated = date + " " +time+":00";
        try {
            formated = dateFormat.parse(unFormated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(formated);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_done:
                if(validateTask()){

                    if(validateTime()){
                        //Generate number between 0-16
                        Random random = new Random();
                        int index = random.nextInt(16);


                        String newDate;
                        if(date.getText().toString().trim().equals("Today")){
                            newDate = getDateTime();
                        }
                        else {
                            newDate = date.getText().toString().trim();
                        }


                        Task task1 = new Task(properDateTime(newDate,time.getText().toString().trim()),task.getText().toString().trim(),color[index],0);
                        db = new DatabaseHandler(getApplicationContext());
                        long task_id1 = db.addTask(task1);
                        db.closeDB();
                        finish();
                    }
                }



                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateTask(){
        if (task.getText().toString().trim().isEmpty()) {
            taskLayout.setError(getString(R.string.err_msg_task));
            return false;
        } else {
            taskLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTime(){
        if (time.getText().toString().trim().isEmpty()) {
            timeLayout.setError(getString(R.string.err_msg_time));
            return false;
        } else {
            timeLayout.setErrorEnabled(false);
        }
        return true;
    }


    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



}
