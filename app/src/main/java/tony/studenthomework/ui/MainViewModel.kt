package tony.studenthomework.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import tony.studenthomework.service.RetrofitManager
import tony.studenthomework.data.MainUiState
import tony.studenthomework.model.Record
import tony.studenthomework.model.StudentDetail
import tony.studenthomework.repository.RecordRepository
import tony.studenthomework.repository.StudentRepository

class MainViewModel: ViewModel() {

    private val defaultExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.d("$coroutineContext $throwable")
    }

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val studentRepository by lazy { StudentRepository(RetrofitManager.studentService) }

    private val recordRepository by lazy { RecordRepository(RetrofitManager.recordService) }

    fun fetchStudentList() {
        viewModelScope.launch(defaultExceptionHandler) {
            val studentList = studentRepository.listStudents()
            _uiState.update { currentState ->
                currentState.copy(
                    studentList = studentList
                )
            }
        }
    }

    fun setSelectedStudentId(studentId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedStudentId = studentId
            )
        }
    }

    fun fetchStudentDetailById(studentId: Int) {
        viewModelScope.launch(defaultExceptionHandler) {
            val studentDetail = studentRepository.getStudentDetailById(studentId)
            setSelectedStudentDetail(studentDetail)
        }
    }

    fun setSelectedStudentDetail(studentDetail: StudentDetail) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedStudentDetail = studentDetail
            )
        }
    }

    fun updateRecords() {
        viewModelScope.launch(defaultExceptionHandler) {
            val studentId = uiState.value.selectedStudentDetail?.id ?: 0
            val recordedHomeworkList = uiState.value.selectedStudentDetail?.recordedHomework
            val recordList = recordedHomeworkList?.map {
                Record(studentId, it.homework.id, it.status.id)
            }?.toList() ?: emptyList()
            recordRepository.updateRecords(recordList)
        }
    }
}