package tony.studenthomework.repository

import tony.studenthomework.service.StudentService
import tony.studenthomework.data.dto.Student
import tony.studenthomework.data.dto.StudentDetail

class StudentRepository(private val studentService: StudentService) {
    suspend fun listStudents(): List<Student> = studentService.listStudents()

    suspend fun getStudentDetailById(studentId: Int): StudentDetail =
        studentService.getStudentDetailById(studentId)
}