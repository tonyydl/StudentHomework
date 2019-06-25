package tony.studenthomework;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tony.studenthomework.model.Student;

public interface StudentServerAPI {
    @GET("student")
    Call<List<Student>> listStudents();

    @GET("student/{student_id}")
    Call<List<Student>> getStudent(@Path("student_id") Integer studentID);

    @GET("student/{student_id}/detail")
    Call<List<Student>> getStudentDetail(@Path("student_id") Integer studentID);
}
