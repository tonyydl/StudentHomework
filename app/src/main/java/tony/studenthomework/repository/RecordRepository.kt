package tony.studenthomework.repository

import tony.studenthomework.service.RecordService
import tony.studenthomework.model.Record

class RecordRepository(private val recordService: RecordService) {
    suspend fun updateRecords(recordList: List<Record>) = recordService.updateRecords(recordList)
}