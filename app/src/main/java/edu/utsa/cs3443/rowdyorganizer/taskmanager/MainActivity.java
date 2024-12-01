package my.era.taskmanager;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private ArrayList<TaskModel> taskList;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        database = openOrCreateDatabase("TaskDB", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, date TEXT, time TEXT)");

        taskList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskList, database);
        recyclerView.setAdapter(adapter);

        loadTasks();

        findViewById(R.id.addTaskButton).setOnClickListener(view -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        // Inflate the custom layout for the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);

        // Initialize UI elements
        EditText taskEditText = dialogView.findViewById(R.id.editTask);
        EditText dateEditText = dialogView.findViewById(R.id.editDate);
        EditText timeEditText = dialogView.findViewById(R.id.editTime);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Date picker
        dateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, day) -> {
                dateEditText.setText(String.format("%d-%02d-%02d", year, month + 1, day));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        // Time picker
        timeEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(this, (view, hour, minute) -> {
                timeEditText.setText(String.format("%02d:%02d", hour, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePicker.show();
        });

        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        // Save button functionality
        btnSave.setOnClickListener(v -> {
            String task = taskEditText.getText().toString();
            String date = dateEditText.getText().toString();
            String time = timeEditText.getText().toString();

            if (TextUtils.isEmpty(task) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save task to the database
            database.execSQL("INSERT INTO tasks(task, date, time) VALUES(?, ?, ?)", new Object[]{task, date, time});
            loadTasks(); // Reload the task list
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss(); // Close the dialog
        });

        // Cancel button functionality
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }


    private void loadTasks() {
        taskList.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                taskList.add(new TaskModel(id, task, date, time));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
