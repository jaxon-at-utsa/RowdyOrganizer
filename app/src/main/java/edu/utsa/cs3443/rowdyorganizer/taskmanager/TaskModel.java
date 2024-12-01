package my.era.taskmanager;


public class TaskModel {
    private int id;
    private String task, date, time;

    public TaskModel(int id, String task, String date, String time) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
