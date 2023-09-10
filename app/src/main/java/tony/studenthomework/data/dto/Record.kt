package tony.studenthomework.data.dto;

public class Record {
    private final int id;
    private final int sid;
    private final int hid;
    private final int status;

    public Record(int sid, int hid, int status) {
        this(0, sid, hid, status);
    }

    private Record(int id, int sid, int hid, int status) {
        this.id = id;
        this.sid = sid;
        this.hid = hid;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getSid() {
        return sid;
    }

    public int getHid() {
        return hid;
    }

    public int getStatus() {
        return status;
    }
}
