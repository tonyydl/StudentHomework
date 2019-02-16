package tony.studenthomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentListActivity extends AppCompatActivity {

    //總共有3個學生
    private static String[] mName = new String[]
            {
                    "王小明", "陳小春", "郭台銘"
            };
    private static String[] mNumber = new String[]
            {
                    "A3345678", "A3345679", "A3345680"
            };

    private ArrayList<HashMap<String, String>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用main中的layout
        setContentView(R.layout.activity_students);
        init();
    }

    //做一個初始化的方法
    public void init() {
        ListView lv1 = findViewById(R.id.student_listview);

        //把資料加入ArrayList中
        for (int i = 0; i < mName.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("name", mName[i]);
            item.put("number", mNumber[i]);
            list.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,
                R.layout.student_list_item,
                new String[]{"name", "number"},
                new int[]{R.id.title, R.id.subTitle});
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(listener);
    }

    private AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
        Intent it = new Intent();
        it.setClass(StudentListActivity.this, ReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("no", position); //position第幾個
        bundle.putString("number", mNumber[position]);
        it.putExtras(bundle);
        startActivity(it);
    };
}