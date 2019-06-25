package tony.studenthomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tony.studenthomework.model.Student;


public class StudentListActivity extends AppCompatActivity {
    private static final String TAG = StudentListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        RecyclerView recyclerView = findViewById(R.id.student_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StudentListAdapter studentListAdapter = new StudentListAdapter(this, onStudentClickListener);
        recyclerView.setAdapter(studentListAdapter);

        StudentEndpoint studentEndpoint = new StudentEndpoint("http://10.0.2.2:5001/");
        studentEndpoint.listStudents(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                Log.d(TAG, "onResponse: " + response.body());
                List<Student> studentList = response.body();
                if (studentList != null && studentList.size() > 0) {
                    studentListAdapter.updateAll(studentList);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.d(TAG, "onFailure: request for data failed.");
            }
        });
    }

    private StudentListAdapter.OnStudentClickListener onStudentClickListener = (position, student) -> {
        Log.i(TAG, String.format("position: %d, student: %s", position, student.toString()));
        Intent it = new Intent();
        it.setClass(StudentListActivity.this, ReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("no", position);
        bundle.putString("number", student.getNumber());
        it.putExtras(bundle);
        startActivity(it);
    };
}