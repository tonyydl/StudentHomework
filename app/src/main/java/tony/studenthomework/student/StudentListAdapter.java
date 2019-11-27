package tony.studenthomework.student;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tony.studenthomework.R;
import tony.studenthomework.model.Student;

public class StudentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_EMPTY = 0;

    interface OnStudentClickListener {
        void onStudentClick(int position, Student student);
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;

        StudentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            number = itemView.findViewById(R.id.tv_number);
        }
    }

    private Context context;

    private List<Student> studentList = new ArrayList<>();

    private OnStudentClickListener onStudentClickListener;

    StudentListAdapter(Context context, OnStudentClickListener onStudentClickListener) {
        this.context = context;
        this.onStudentClickListener = onStudentClickListener;
    }

    void updateAll(List<Student> studentList) {
        this.studentList.clear();
        this.studentList.addAll(studentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.empty_view, parent, false)) {
            };
        }
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {
            StudentViewHolder studentViewHolder = (StudentViewHolder) holder;
            Student student = studentList.get(position);
            studentViewHolder.name.setText(student.getName());
            studentViewHolder.number.setText(student.getNumber());
            studentViewHolder.itemView.setOnClickListener(v -> onStudentClickListener.onStudentClick(position, student));
        }
    }

    @Override
    public int getItemCount() {
        if (studentList.size() == 0) {
            return 1;
        }
        return studentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (studentList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }
}