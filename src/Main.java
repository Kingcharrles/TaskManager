import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
// inti task
        System.out.println("🗂️ Welcome to the Task Creator!");
        label:
        while (true) {
            System.out.print("\nType 'new' to create, 'remove' to delete, 'edit' to modify, 'view' to see tasks, or 'exit' to quit: ");
            String command = kb.nextLine().trim().toLowerCase();

            switch (command) {
                case "exit":
                    break label;
                case "new": {
                    //CREATE TASK
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
                        deadline = LocalDateTime.parse(dateInput + DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                    } catch (Exception e) {
                        System.out.println("Invalid date. Using 24 hours from now.");
                        deadline = LocalDateTime.now().plusDays(1);
                    }

                    Task task = new Task(id, title, description, priority, deadline, false, new ArrayList<>());
                    taskManager.createTask(task);
                    System.out.println("✅ Task created!");
                    System.out.println("Deadline: " + task.getDeadline().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

                    break;
                }
                case "remove":
                    // === REMOVE TASK ===
                    System.out.print("Enter the Task ID to delete: ");
                    String taskIdToRemove = kb.nextLine().trim();

                    if (taskManager.getTasksByCompletion(false).stream().anyMatch(t -> t.getId().equals(taskIdToRemove))) {
                        taskManager.deleteTask(taskIdToRemove);
                        System.out.println("🗑️ Task with ID '" + taskIdToRemove + "' has been removed.");
                    } else {
                        System.out.println("⚠️ Task ID not found.");
                    }
                    break;
                case "edit": {
                    System.out.print("Enter the Task ID to modify: ");
                    String taskId = kb.nextLine().trim();

                    Optional<Task> taskOpt = taskManager.getTasksByCompletion(false).stream()
                            .filter(t -> t.getId().equals(taskId)).findFirst();

                    if (taskOpt.isEmpty()) {
                        System.out.println("⚠️ Task ID not found.");
                        continue;
                    }

                    Task task = taskOpt.get();

                    System.out.println("Leave blank to keep current values.");

                    System.out.print("New Title [" + task.getTitle() + "]: ");
                    String newTitle = kb.nextLine();
                    if (!newTitle.isBlank()) task.setTitle(newTitle);

                    System.out.print("New Description [" + task.getDescription() + "]: ");
                    String newDesc = kb.nextLine();
                    if (!newDesc.isBlank()) task.setDescription(newDesc);

                    System.out.print("New Priority (HIGH, MEDIUM, LOW) [" + task.getPriority() + "]: ");
                    String newPriority = kb.nextLine().toUpperCase();
                    if (!newPriority.isBlank()) {
                        try {
                            task.setPriority(Task.Priority.valueOf(newPriority));
                        } catch (IllegalArgumentException e) {
                            System.out.println("⚠️ Invalid priority. Keeping previous value.");
                        }
                    }

                    System.out.print("New Deadline (MM-DD-YYYY) [" +
                            task.getDeadline().toLocalDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "]: ");
                    String newDeadline = kb.nextLine();
                    if (!newDeadline.isBlank()) {
                        try {
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                            LocalDateTime newDate = LocalDateTime.parse(newDeadline + "T00:00:00",
                                    DateTimeFormatter.ofPattern("MM-dd-yyyy'T'HH:mm:ss"));
                            task.setDeadline(newDate);
                        } catch (Exception e) {
                            System.out.println("⚠️ Invalid date. Keeping previous deadline.");
                        }
                    }

                    taskManager.updateTask(task);
                    System.out.println("✅ Task updated!");
                    break;
                }
                case "view": //view tasks
                    List<Task> allTasks = taskManager.getTasksByCompletion(false);

                    if (allTasks.isEmpty()) {
                        System.out.println("📭 No tasks available.");
                    } else {
                        System.out.println("\n📋 Your Current Tasks:");
                        DateTimeFormatter dateOnlyFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                        for (Task task : allTasks) {
                            System.out.println("- " + task.getTitle() +
                                    "\n Description: " + task.getDescription() +
                                    "\n Due: " + task.getDeadline().toLocalDate().format(dateOnlyFormat) +
                                    "\n Priority: " + task.getPriority() +
                                    "\n Task ID: " + task.getId() + "\n");
                        }
                    }
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }// end task creation

        DateTimeFormatter dateOnlyFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            System.out.println("\n📝 Final Task List:");
            for (Task task : taskManager.getTasksByCompletion(false)) {
                System.out.println("----------------------" + "\n- " + task.getTitle() +
                        "\n Description: " + task.getDescription() +
                        "\n Due: " + task.getDeadline().format(dateOnlyFormat) +
                        "\n Priority: " + task.getPriority() +
                        "\n Task ID: " + task.getId() + " Assigned");
            } //print total task recept

            kb.close();
        }
    }
