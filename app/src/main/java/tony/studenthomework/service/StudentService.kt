package tony.studenthomework.service

import retrofit2.http.GET
import retrofit2.http.Path
import tony.studenthomework.data.dto.Student
import tony.studenthomework.data.dto.StudentDetail

interface StudentService {
    @GET("/student")
    suspend fun listStudents(): List<Student>

    @GET("/student/{student_id}/detail")
    suspend fun getStudentDetailById(@Path("student_id") studentId: Int): StudentDetail
}