package tony.studenthomework.record;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import tony.studenthomework.model.Record;

public interface RecordServerApi {
    @PUT("record")
    Call<List<Record>> updateRecords(@Body List<Record> recordList);
}
