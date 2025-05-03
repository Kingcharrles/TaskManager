import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
// inti task
        System.out.println("🗂️ Welcome to the Task Creator!");
        while (true) {
            System.out.print("\nType 'new' to create a task or 'exit' to quit: ");
            String command = kb.nextLine().trim().toLowerCase();
//task creation loop
            if (command.equals("exit")) {
                break;
            } else if (!command.equals("new")) {
                System.out.println("Unknown command.");
                continue;
            }

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

            System.out.print("Enter task deadline date (DD-MM-YYYY): ");
            String dateInput = kb.nextLine();
            System.out.print("Enter task deadline time (HH:MM): ");
            String timeInput = kb.nextLine();

            LocalDateTime deadline;
            try {
                deadline = LocalDateTime.parse(dateInput + " Time: " + timeInput);
            } catch (Exception e) {
                System.out.println("Invalid datetime format. Setting deadline to 24 hours from now.");
                deadline = LocalDateTime.now().plusDays(1);
            }
//task save
            Task task = new Task(id, title, description, priority, deadline, false, new ArrayList<>());
            taskManager.createTask(task);
//task recept
            System.out.println("✅ Task created!");
            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Deadline: " + task.getDeadline());
            System.out.println("Created at: " + task.getTimeCreated());
        }// end task creation

        System.out.println("\n📝 Final Task List:");
        for (Task t : taskManager.getTasksByCompletion(false)) {
            System.out.println("- " + t.getTitle() + " (Due: " + t.getDeadline() + ")");
        } //print total task recept

        kb.close();
    }
}
