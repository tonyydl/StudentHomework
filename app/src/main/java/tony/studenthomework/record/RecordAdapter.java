package tony.studenthomework.record;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tony.studenthomework.R;
import tony.studenthomework.model.RecordStatus;
import tony.studenthomework.model.RecordStatusEnum;
import tony.studenthomework.model.RecordedHomework;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.HomeworkViewHolder> {

    private Context context;
    private SparseIntArray listStatus = new SparseIntArray();
    private List<RecordedHomework> recordedHomeworkList = new ArrayList<>();

    RecordAdapter(Context context) {
        this.context = context;
    }

    void updateAll(List<RecordedHomework> recordedHomeworkList) {
        this.recordedHomeworkList.clear();
        this.recordedHomeworkList.addAll(recordedHomeworkList);
        notifyDataSetChanged();
    }

    List<RecordedHomework> getRecordedHomeworkList() {
        return this.recordedHomeworkList;
    }

    SparseIntArray getListStatus() {
        return this.listStatus;
    }

    @NonNull
    @Override
    public HomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new HomeworkViewHolder(LayoutInflater.from(context).inflate(
                R.layout.review_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkViewHolder holder, int position) {
        RecordedHomework recordedHomework = recordedHomeworkList.get(position);
        RecordStatus recordStatus = recordedHomework.getStatus();
        holder.title.setText(recordedHomework.getHomework().getTitle());
        holder.img.setBackgroundResource(R.drawable.ic_launcher);
        listStatus.put(position, recordStatus.getId());
        if (recordStatus.getId() == RecordStatusEnum.DONE.ordinal()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(v -> {
            if (listStatus.get(position) == RecordStatusEnum.DONE.ordinal()) {
                listStatus.put(position, RecordStatusEnum.NOT_YET.ordinal());
            } else {
                listStatus.put(position, RecordStatusEnum.DONE.ordinal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordedHomeworkList.size();
    }

    static class HomeworkViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private CheckBox checkBox;

        HomeworkViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
