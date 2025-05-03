import java.util.*;

public class TaskManager {
    private Map<String, Task> tasks = new HashMap<>();

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void deleteTask(String id) {
        tasks.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public List<Task> getTasksByPriority(Task.Priority priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks.values()) {
            if (t.getPriority() == priority) result.add(t);
        }
        return result;
    }

    public List<Task> getTasksByCompletion(boolean completed) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks.values()) {
            if (t.isCompleted() == completed) result.add(t);
        }
        return result;
    }

    // Add filtering by due date, etc.
}
