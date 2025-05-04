import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    public enum Priority { HIGH, MEDIUM, LOW }

    private String id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime deadline;
    private LocalDateTime timeCreated;
    private boolean completed;
    private List<Attachment> attachments;

    // Constructor
    public Task(String id, String title, String description, Priority priority,
                LocalDateTime deadline, boolean completed, List<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = completed;
        this.timeCreated = LocalDateTime.now();
        this.attachments = attachments != null ? attachments : new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }
}

