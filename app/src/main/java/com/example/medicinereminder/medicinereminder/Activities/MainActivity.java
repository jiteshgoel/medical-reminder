package com.example.medicinereminder.medicinereminder.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicinereminder.medicinereminder.Adapters.TaskAdapter;
import com.example.medicinereminder.medicinereminder.Models.Task;
import com.example.medicinereminder.medicinereminder.R;
import com.example.medicinereminder.medicinereminder.Utils.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int mYear, mMonth, mDay;
    int mHour, mMinute;
    String timeString, dateString;
    TextInputLayout dialogText;
    DatabaseHelper dbHelp;
    RecyclerView recyclerView;
    Button dd;
    ArrayList<Task> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList=new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new TaskAdapter(arrayList,MainActivity.this));

        dbHelp = new DatabaseHelper(this);
        dd=findViewById(R.id.dd);

       /* alist=findViewById(R.id.newlist);

        alist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i).toString();

            }
        });
*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getData();
        dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData("first");
            }
        });

        Calendar ccc = Calendar.getInstance();
        System.out.println("Current time => " + ccc.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(ccc.getTime());

        Log.d("CCC", "onCreate: "+formattedDate);

        Date date1=null;

        try {
            date1 = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Date finalDate = date1;


        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_layout);
                dialogText = dialog.findViewById(R.id.details);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

//==================================================== Select Date =============================================================

                dialog.findViewById(R.id.selectDate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        final Button btnDate = dialog.findViewById(R.id.selectDate);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        dateString = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                        btnDate.setText(dateString);

                                        String pattern = "dd-MM-yyyy";
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                                        Date date2=null;
                                        try {
                                            date2 = simpleDateFormat.parse(dateString);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d("CCC", "date1 : "+finalDate+"date2 : "+date2);

                                        if(finalDate.after(date2)){
                                            Log.d("CCC", "onCreate: "+"Date1 is after Date2");
                                        }
                                        // before() will return true if and only if date1 is before date2
                                        if(finalDate.before(date2)){
                                            Log.d("CCC", "onCreate: "+"Date2 is after Date1");
                                        }

                                        //equals() returns true if both the dates are equal
                                        if(finalDate.equals(date2)){
                                            Log.d("CCC", "onCreate: "+"Date1 is equal to Date2");
                                        }


                                        Log.d("WWW", "onDateSet: " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();

                    }
                });

//==================================================== Select Time =============================================================

                dialog.findViewById(R.id.selectTime).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get Current Time

                        final Button btnTime = dialog.findViewById(R.id.selectTime);

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        timeString = hourOfDay + ":" + minute;
                                        btnTime.setText(timeString);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });


                dialog.findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addNew(dialogText.getEditText().getText().toString(),dateString,timeString);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Task Added..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNew(String task,String date,String time) {
        String s1 = task;
        String s2 = "0";
        String s3 = date;
        String s4 = time;
        dbHelp.addData(s1,s2,s3,s4);
        getData();
    }

    public void getData()
    {
        Cursor cursor = dbHelp.fetchData();
        arrayList=new ArrayList<>();
        while (cursor.moveToNext())
        {
            String taskName = cursor.getString(cursor.getColumnIndex("task"));
            String taskStatus = cursor.getString(cursor.getColumnIndex("status"));
            String taskDate = cursor.getString(cursor.getColumnIndex("date"));
            String taskTime = cursor.getString(cursor.getColumnIndex("time"));
            Task task=new Task(taskName,taskStatus,taskDate,taskTime);
            arrayList.add(task);
        }
        recyclerView.setAdapter(new TaskAdapter(arrayList,this));
        /*ListAdapter myAdapter = new SimpleCursorAdapter(this,R.layout.list_item,
                cursor,
                new String[]{dbHelp.COLUMN_1,dbHelp.COLUMN_3,dbHelp.COLUMN_4},
                new int[]{R.id.reminderDetails,R.id.whichDay,R.id.whatTime},0);
        alist.setAdapter(myAdapter);*/
    }

    public void deleteData(String s)
    {
        dbHelp.deleteTask(s);
        //recyclerView.setAdapter(new TaskAdapter(arrayList,this));
    }
}
