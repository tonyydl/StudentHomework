package tony.studenthomework;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tony.studenthomework.model.Student;

public class StudentEndpoint {
    private static final String TAG = StudentEndpoint.class.getSimpleName();
    private StudentServerAPI serverAPI;

    StudentEndpoint(String endpointUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(StudentServerAPI.class);
    }

    public void listStudents(Callback<List<Student>> callback) {
        Call<List<Student>> call = serverAPI.listStudents();
        call.enqueue(callback);
    }
}
