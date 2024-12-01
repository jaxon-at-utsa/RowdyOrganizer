package my.era.taskmanager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> taskList;
    private SQLiteDatabase database;

    public TaskAdapter(ArrayList<TaskModel> taskList, SQLiteDatabase database) {
        this.taskList = taskList;
        this.database = database;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.taskTextView.setText(task.getTask());
        holder.dateTextView.setText(task.getDate());
        holder.timeTextView.setText(task.getTime());

        holder.deleteButton.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition(); // Get the actual position
            if (currentPosition != RecyclerView.NO_POSITION) {
                database.execSQL("DELETE FROM tasks WHERE id = ?", new Object[]{task.getId()});
                taskList.remove(currentPosition); // Remove item from the list
                notifyItemRemoved(currentPosition); // Notify RecyclerView of the removal
            }
        });
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView, dateTextView, timeTextView;
        View deleteButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.textTask);
            dateTextView = itemView.findViewById(R.id.textDate);
            timeTextView = itemView.findViewById(R.id.textTime);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
