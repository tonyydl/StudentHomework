package tony.studenthomework.model;

public class RecordedHomework {
    private final Homework homework;
    private final RecordStatus status;

    public RecordedHomework(Homework homework, RecordStatus status) {
        this.homework = homework;
        this.status = status;
    }

    public Homework getHomework() {
        return homework;
    }

    public RecordStatus getStatus() {
        return status;
    }
}
