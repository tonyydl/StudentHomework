package tony.studenthomework.data.dto

enum class RecordStatusEnum(private val status: Int) {
    NOT_YET(0),
    PROCESSING(1),
    DONE(2)
}