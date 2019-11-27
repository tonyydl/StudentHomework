package tony.studenthomework.record;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_EMPTY = 0;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.empty_view, parent, false)) {
            };
        }
        return new HomeworkViewHolder(LayoutInflater.from(context).inflate(
                R.layout.review_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeworkViewHolder) {
            HomeworkViewHolder homeworkViewHolder = (HomeworkViewHolder) holder;
            RecordedHomework recordedHomework = recordedHomeworkList.get(position);
            RecordStatus recordStatus = recordedHomework.getStatus();
            homeworkViewHolder.title.setText(recordedHomework.getHomework().getTitle());
            homeworkViewHolder.img.setBackgroundResource(R.drawable.ic_launcher);
            listStatus.put(position, recordStatus.getId());
            if (recordStatus.getId() == RecordStatusEnum.DONE.ordinal()) {
                homeworkViewHolder.checkBox.setChecked(true);
            } else {
                homeworkViewHolder.checkBox.setChecked(false);
            }
            homeworkViewHolder.checkBox.setOnClickListener(v -> {
                if (listStatus.get(position) == RecordStatusEnum.DONE.ordinal()) {
                    listStatus.put(position, RecordStatusEnum.NOT_YET.ordinal());
                } else {
                    listStatus.put(position, RecordStatusEnum.DONE.ordinal());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (recordedHomeworkList.size() == 0) {
            return 1;
        }
        return recordedHomeworkList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (recordedHomeworkList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
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
