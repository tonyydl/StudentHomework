package tony.studenthomework.data.dto;

public class Student {
    private final int id;

    private final String number;

    private final String name;

    public Student(int id, String number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
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
}
