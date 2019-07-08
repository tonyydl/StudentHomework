package tony.studenthomework.student;

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
import tony.studenthomework.R;
import tony.studenthomework.model.Student;
import tony.studenthomework.record.RecordActivity;


public class StudentListActivity extends AppCompatActivity {
    private static final String TAG = StudentListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_student);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StudentListAdapter studentListAdapter = new StudentListAdapter(this, onStudentClickListener);
        recyclerView.setAdapter(studentListAdapter);

        StudentEndpoint studentEndpoint = StudentEndpoint.getInstance();
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
                Log.e(TAG, "onFailure: request for data failed.", t);
            }
        });
    }

    private StudentListAdapter.OnStudentClickListener onStudentClickListener = (position, student) -> {
        Log.i(TAG, String.format("position: %d, student: %s", position, student.toString()));
        Intent it = new Intent();
        it.setClass(StudentListActivity.this, RecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RecordActivity.EXTRA_STUDENT_ID, student.getId());
        it.putExtras(bundle);
        startActivity(it);
    };
}