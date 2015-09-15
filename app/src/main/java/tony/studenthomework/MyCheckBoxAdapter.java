package tony.studenthomework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyCheckBoxAdapter extends BaseAdapter {

    private static final String ACTIVITY_TAG="LogDemo";

    public static LinkedHashMap<Integer, Boolean> isSelected;
    private LayoutInflater mInflater;
    private static List<Map<String, String>> listData;

    private class ViewHolder {
        public ImageView img;
        public TextView title;
        public CheckBox checkBox;
    }

    public MyCheckBoxAdapter(Context context, List<Map<String, String>> listData) {
        this.mInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    public static void init(String result) {
        //分析字串

        String[] results = result.split(",");
        //加入isSelected 將1變成true，0變成false

        isSelected = new LinkedHashMap<Integer, Boolean>();
        for (int i = 0; i < listData.size(); i++) {
            if(results[i].equals("1")){
                isSelected.put(i, true);

            } else {
                isSelected.put(i, false);
            }
            //偵查log
            //Log.d(MyCheckBoxAdapter.ACTIVITY_TAG, results[i]);
        }


    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        //return listData.get(position);
        return listData.get(position).get("itemTitle");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        holder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.list_items, null);
        final View view = convertView;
        holder.img = (ImageView) convertView.findViewById(R.id.img);
        holder.title = (TextView) convertView.findViewById(R.id.itemTitle);
        holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(view.getContext(),"第"+isSelected.get(position)+"已勾選",Toast.LENGTH_LONG).show();

                if(isSelected.get(position)) {
                    isSelected.put(position, false);
                } else {
                    isSelected.put(position, true);
                }
                //Toast.makeText(view.getContext(),position+","+isSelected.get(position),Toast.LENGTH_LONG).show();
                //Log.d(MyCheckBoxAdapter.ACTIVITY_TAG, position+","+isSelected.get(position));
            }
        });

        convertView.setTag(holder);

        holder.img.setBackgroundResource(R.drawable.ic_launcher);
        holder.title.setText(listData.get(position).get("itemTitle"));

        //MyCheckBoxAdapter.checkBox.
        holder.checkBox.setChecked(isSelected.get(position));
        return convertView;
    }
}
