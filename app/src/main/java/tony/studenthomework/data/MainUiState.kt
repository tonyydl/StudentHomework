package tony.studenthomework.data

import tony.studenthomework.data.dto.Student
import tony.studenthomework.data.dto.StudentDetail

data class MainUiState(
    val studentList: List<Student> = listOf(),
    val selectedStudentId: Int = 0,
    val selectedStudentDetail: StudentDetail? = null
)