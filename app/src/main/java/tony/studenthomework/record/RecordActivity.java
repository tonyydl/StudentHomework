package tony.studenthomework.record;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tony.studenthomework.R;
import tony.studenthomework.model.Record;
import tony.studenthomework.student.StudentEndpoint;
import tony.studenthomework.model.RecordedHomework;
import tony.studenthomework.model.StudentDetail;

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();
    public static final String EXTRA_STUDENT_ID = "student_id";

    private int studentId;
    private Button saveBtn;

    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            studentId = bundle.getInt(EXTRA_STUDENT_ID);
        }
        RecyclerView homeworkRecyclerView = findViewById(R.id.recyclerview_homework);
        homeworkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecordAdapter(this);
        homeworkRecyclerView.setAdapter(adapter);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setEnabled(false);
        StudentEndpoint studentEndpoint = StudentEndpoint.getInstance();
        studentEndpoint.getStudentDetail(studentId, new Callback<StudentDetail>() {
            @Override
            public void onResponse(Call<StudentDetail> call, Response<StudentDetail> response) {
                Log.d(TAG, "onResponse: " + response.body());
                StudentDetail studentDetail = response.body();
                if (studentDetail == null) {
                    return;
                }
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(studentDetail.getName());
                    actionBar.setSubtitle(studentDetail.getNumber());
                }
                adapter.updateAll(studentDetail.getRecordedHomework());
                saveBtn.setOnClickListener(onSaveClickListener);
                saveBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<StudentDetail> call, Throwable t) {
                Log.e(TAG, "onFailure: request for data failed.", t);
            }
        });
    }

    private View.OnClickListener onSaveClickListener = v -> {
        List<Record> updateList = new ArrayList<>();
        List<RecordedHomework> recordedHomeworkList = adapter.getRecordedHomeworkList();
        SparseIntArray listStatus = adapter.getListStatus();
        for (int i = 0; i < recordedHomeworkList.size(); i++) {
            RecordedHomework recordedHomework = recordedHomeworkList.get(i);
            updateList.add(new Record(studentId, recordedHomework.getHomework().getId(), listStatus.get(i)));
        }
        RecordEndpoint.getInstance().updateRecords(updateList, new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                Log.d(TAG, "onResponse: " + response.body());
                Toast.makeText(getApplicationContext(), "Update records success.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                Log.e(TAG, "onFailure: update records failed.", t);
                Toast.makeText(getApplicationContext(), "Update records fail...", Toast.LENGTH_SHORT).show();
            }
        });
    };
}
