package tony.studenthomework.student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tony.studenthomework.model.Student;
import tony.studenthomework.model.StudentDetail;

public interface StudentServerApi {
    @GET("student")
    Call<List<Student>> listStudents();

    @GET("student/{student_id}")
    Call<Student> getStudent(@Path("student_id") int studentId);

    @GET("student/{student_id}/detail")
    Call<StudentDetail> getStudentDetail(@Path("student_id") int studentId);
}
