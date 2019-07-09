package tony.studenthomework.student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tony.studenthomework.R;
import tony.studenthomework.model.Student;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

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
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.number.setText(student.getNumber());
        holder.itemView.setOnClickListener(v -> onStudentClickListener.onStudentClick(position, student));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}