package tony.studenthomework.repository

import tony.studenthomework.service.RecordService
import tony.studenthomework.data.dto.Record

class RecordRepository(private val recordService: RecordService) {
    suspend fun updateRecords(recordList: List<Record>) = recordService.updateRecords(recordList)
}