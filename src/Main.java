import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
// inti task
        System.out.println("🗂️ Welcome to the Task Creator!");
        while (true) {
            System.out.print("\nType 'new' to create, 'remove' to delete a task, or 'exit' to quit: ");
            String command = kb.nextLine().trim().toLowerCase();

            if (command.equals("exit")) {
                break;
            } else if (command.equals("new")) {
                // === CREATE TASK ===
                System.out.print("Enter task ID: ");
                String id = kb.nextLine();

                System.out.print("Enter task title: ");
                String title = kb.nextLine();

                System.out.print("Enter task description: ");
                String description = kb.nextLine();

                System.out.print("Enter task priority (HIGH, MEDIUM, LOW): ");
                String priorityInput = kb.nextLine().toUpperCase();
                Task.Priority priority;
                try {
                    priority = Task.Priority.valueOf(priorityInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid priority. Defaulting to MEDIUM.");
                    priority = Task.Priority.MEDIUM;
                }

                System.out.print("Enter task deadline date (MM-DD-YYYY): ");
                String dateInput = kb.nextLine();

                LocalDateTime deadline;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    deadline = LocalDateTime.parse(dateInput + "T00:00:00", DateTimeFormatter.ofPattern("MM-dd-yyyy'T'HH:mm:ss"));
                } catch (Exception e) {
                    System.out.println("Invalid date. Using 24 hours from now.");
                    deadline = LocalDateTime.now().plusDays(1);
                }

                Task task = new Task(id, title, description, priority, deadline, false, new ArrayList<>());
                taskManager.createTask(task);
                System.out.println("✅ Task created!");
                System.out.println("Deadline: " + task.getDeadline().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

            } else if (command.equals("remove")) {
                // === REMOVE TASK ===
                System.out.print("Enter the Task ID to delete: ");
                String taskIdToRemove = kb.nextLine().trim();

                if (taskManager.getTasksByCompletion(false).stream().anyMatch(t -> t.getId().equals(taskIdToRemove))) {
                    taskManager.deleteTask(taskIdToRemove);
                    System.out.println("🗑️ Task with ID '" + taskIdToRemove + "' has been removed.");
                } else {
                    System.out.println("⚠️ Task ID not found.");
                }
            } else {
                System.out.println("Unknown command.");
            }
        }// end task creation

        DateTimeFormatter dateOnlyFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        System.out.println("\n📝 Final Task List:");
        for (Task task : taskManager.getTasksByCompletion(false)) {
            System.out.println("- " + task.getTitle() +
                    "\n Description: " + task.getDescription() +
                    "\n Due: " + task.getDeadline().format(dateOnlyFormat) +
                    "\n Priority: " + task.getPriority() +
                    "\n Task ID: " + task.getId() + " Assigned");
        } //print total task recept

        kb.close();
    }
}
