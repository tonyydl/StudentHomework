package tony.studenthomework.record;

import android.util.Log;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tony.studenthomework.BuildConfig;
import tony.studenthomework.model.Record;

class RecordEndpoint {

    private static final String TAG = RecordEndpoint.class.getSimpleName();

    private static RecordEndpoint instance;

    static RecordEndpoint getInstance() {
        if (instance == null) {
            synchronized (RecordEndpoint.class) {
                instance = new RecordEndpoint(BuildConfig.BASE_URL);
            }
        }
        return instance;
    }

    private RecordServerApi serverAPI;

    private RecordEndpoint(String endpointUrl) {
        Log.i(TAG, "Initialize RecordEndpoint...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(RecordServerApi.class);
    }

    void updateRecords(List<Record> recordList, Callback<List<Record>> callback) {
        Log.i(TAG, "Update records...");
        serverAPI.updateRecords(recordList).enqueue(callback);
    }
}
