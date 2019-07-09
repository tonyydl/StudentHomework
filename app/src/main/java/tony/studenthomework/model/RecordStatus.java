package tony.studenthomework.model;

public class RecordStatus {
    private final int id;
    private final String status;

    public RecordStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
