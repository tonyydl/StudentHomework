package tony.studenthomework;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyCheckBoxAdapter extends BaseAdapter {
    private static final String TAG = MyCheckBoxAdapter.class.getSimpleName();

    private class ViewHolder {
        private ImageView img;
        private TextView title;
        private CheckBox checkBox;
    }

    static SparseBooleanArray isSelected = new SparseBooleanArray();
    private LayoutInflater mInflater;
    private List<Map<String, String>> listData;

    MyCheckBoxAdapter(Context context, List<Map<String, String>> listData) {
        this.mInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    void update(String result) {
        Log.i(TAG, "result: " + result);
        String[] results = result.split(",");
        for (int i = 0; i < listData.size(); i++) {
            if (results[i].equals("1")) {
                isSelected.put(i, true);
            } else {
                isSelected.put(i, false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position).get("itemTitle");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.review_list_item, parent, false);
        holder.img = convertView.findViewById(R.id.img);
        holder.title = convertView.findViewById(R.id.title);
        holder.checkBox = convertView.findViewById(R.id.checkbox);
        holder.checkBox.setOnClickListener(v -> {
            if (isSelected.get(position)) {
                isSelected.put(position, false);
            } else {
                isSelected.put(position, true);
            }
        });

        holder.img.setBackgroundResource(R.drawable.ic_launcher);
        holder.title.setText(listData.get(position).get("itemTitle"));
        holder.checkBox.setChecked(isSelected.get(position));

        convertView.setTag(holder);
        return convertView;
    }
}
