package my.era.taskmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private ArrayList<TaskModel> taskList;
    private TaskAdapter adapter;
    private Button add;
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Initialize database
        database = openOrCreateDatabase("TaskDB", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, date TEXT, time TEXT)");

        // Initialize UI elements
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recyclerView);
        add = findViewById(R.id.add);

        // Setup RecyclerView
        taskList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskList, database);
        recyclerView.setAdapter(adapter);

        // Set current date as selected date
        selectedDate = getCurrentDate();
        loadTasks(selectedDate);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalendarActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        // Handle date change event
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
            loadTasks(selectedDate);
        });
    }

    private void loadTasks(String date) {
        taskList.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM tasks WHERE date = ?", new String[]{date});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String taskDate = cursor.getString(2);
                String time = cursor.getString(3);
                taskList.add(new TaskModel(id, task, taskDate, time));
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No tasks for the selected date", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%d-%02d-%02d", year, month, day);
    }
}
