package tony.studenthomework.data

import tony.studenthomework.model.Student
import tony.studenthomework.model.StudentDetail

data class MainUiState(
    val studentList: List<Student> = listOf(),
    val selectedStudentId: Int = 0,
    val selectedStudentDetail: StudentDetail? = null
)