package tony.studenthomework.data.dto;

import java.util.List;

public class StudentDetail {
    private final int id;
    private final String number;
    private final String name;
    private final List<RecordedHomework> recordedHomework;

    public StudentDetail(int id, String number, String name, List<RecordedHomework> recordedHomework) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.recordedHomework = recordedHomework;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<RecordedHomework> getRecordedHomework() {
        return recordedHomework;
    }
}
