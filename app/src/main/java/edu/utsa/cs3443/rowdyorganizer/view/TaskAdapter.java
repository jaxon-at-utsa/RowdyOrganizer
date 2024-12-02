package edu.utsa.cs3443.rowdyorganizer.view;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.model.TaskManager;
import edu.utsa.cs3443.rowdyorganizer.model.TaskModel;

/**
 * TaskAdapter binds tasks to a RecyclerView for display and handles marking tasks as completed.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<TaskModel> taskList;
    private final TaskManager taskManager;

    /**
     * Constructor for TaskAdapter.
     *
     * @param taskList    The list of tasks to display.
     * @param taskManager The TaskManager for managing tasks.
     */
    public TaskAdapter(List<TaskModel> taskList, TaskManager taskManager) {
        this.taskList = taskList;
        this.taskManager = taskManager;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each task item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Bind data to the views
        TaskModel task = taskList.get(position);
        holder.taskTextView.setText(task.getTask());
        holder.dateTextView.setText(task.getDate());
        holder.timeTextView.setText(task.getTime());

        // Update UI based on completion status
        if (task.isCompleted()) {
            holder.taskTextView.setPaintFlags(holder.taskTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.dateTextView.setPaintFlags(holder.dateTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.timeTextView.setPaintFlags(holder.timeTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.taskTextView.setPaintFlags(holder.taskTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.dateTextView.setPaintFlags(holder.dateTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.timeTextView.setPaintFlags(holder.timeTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // Handle mark-as-completed action
        holder.completeButton.setOnClickListener(v -> {
            taskManager.markTaskAsCompleted(task.getId()); // Mark the task as completed in the database
            task.setCompleted(true); // Update the task's completed status
            notifyItemChanged(position); // Refresh the item's UI
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * Updates the task list and notifies the adapter.
     *
     * @param newTaskList The new list of tasks.
     */
    public void updateTaskList(List<TaskModel> newTaskList) {
        taskList.clear();
        taskList.addAll(newTaskList);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for managing task item views.
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView, dateTextView, timeTextView;
        ImageButton completeButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            taskTextView = itemView.findViewById(R.id.textTask);
            dateTextView = itemView.findViewById(R.id.textDate);
            timeTextView = itemView.findViewById(R.id.textTime);
            completeButton = itemView.findViewById(R.id.deleteButton); // Reused as the "Mark as Completed" button
        }
    }
}
