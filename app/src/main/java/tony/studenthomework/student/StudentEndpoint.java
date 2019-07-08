package tony.studenthomework.student;

import android.util.Log;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tony.studenthomework.Constants;
import tony.studenthomework.model.Student;
import tony.studenthomework.model.StudentDetail;

public class StudentEndpoint {

    private static final String TAG = StudentEndpoint.class.getSimpleName();

    private static StudentEndpoint instance;

    public static StudentEndpoint getInstance() {
        if (instance == null) {
            synchronized (StudentEndpoint.class) {
                instance = new StudentEndpoint(Constants.BASE_URL);
            }
        }
        return instance;
    }

    private StudentServerApi serverAPI;

    private StudentEndpoint(String endpointUrl) {
        Log.i(TAG, "Initialize StudentEndpoint...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(StudentServerApi.class);
    }

    public void listStudents(Callback<List<Student>> callback) {
        Log.i(TAG, "Calling listStudents API...");
        serverAPI.listStudents().enqueue(callback);
    }

    public void getStudent(int studentId, Callback<Student> callback) {
        Log.i(TAG, "Calling getStudent API...");
        serverAPI.getStudent(studentId).enqueue(callback);
    }

    public void getStudentDetail(int studentId, Callback<StudentDetail> callback) {
        Log.i(TAG, "Calling getStudentDetail API...");
        serverAPI.getStudentDetail(studentId).enqueue(callback);
    }
}
