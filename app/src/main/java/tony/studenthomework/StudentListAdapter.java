package tony.studenthomework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tony.studenthomework.model.Student;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    interface OnStudentClickListener {
        void onStudentClick(int position, Student student);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;

        MyViewHolder(View itemView) {
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

    void clear() {
        this.studentList.clear();
    }

    void update(Student student) {
        this.studentList.add(student);
        notifyDataSetChanged();
    }

    void updateAll(List<Student> studentList) {
        this.studentList.addAll(studentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(studentList.get(position).getName());
        holder.number.setText(studentList.get(position).getNumber());
        holder.itemView.setOnClickListener(v -> onStudentClickListener.onStudentClick(position, studentList.get(position)));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}