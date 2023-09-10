package tony.studenthomework.data.dto

data class StudentDetail(
    val id: Int = 0,
    val number: String? = null,
    val name: String? = null,
    val recordedHomework: List<RecordedHomework>? = null
)