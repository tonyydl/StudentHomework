package tony.studenthomework.service

import retrofit2.http.Body
import retrofit2.http.PUT
import tony.studenthomework.data.dto.Record

interface RecordService {

    @PUT("/record")
    suspend fun updateRecords(@Body recordList: List<@JvmSuppressWildcards Record>): List<Record>
}