package tony.studenthomework.data.dto;

public enum RecordStatusEnum {
    NOT_YET(0),
    PROCESSING(1),
    DONE(2);

    private int status;

    RecordStatusEnum(int status) {
        this.status = status;
    }
}
