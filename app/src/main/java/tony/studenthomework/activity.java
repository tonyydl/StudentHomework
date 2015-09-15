package tony.studenthomework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class activity extends Activity {
    //Set全域globle    箱子 & 配置器 & 資料
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter adapter;
    private ListView lv1;
    private EditText et1;

    //總共有3個學生
    private static String[] mName = new String[]
    {
        "王小明", "陳小春", "郭台銘"
    };
    private static String[] mNumber = new String[]
    {
        "A3345678", "A3345679", "A3345680"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用main中的layout
        setContentView(R.layout.main);
        init();
    }

    //做一個初始化的方法
    public void init() {
        //找到listview組件
        lv1 = (ListView) findViewById(R.id.listView1);

        //把資料加入ArrayList中
        for(int i=0; i<mName.length; i++){
            HashMap<String,String> item = new HashMap<String,String>();
            item.put( "name", mName[i]);
            item.put( "number",mNumber[i] );
            list.add( item );
        }

        //新增SimpleAdapter
        adapter = new SimpleAdapter(
                this,
                list,
                R.layout.list,
                new String[] { "name","number" },
                new int[] { R.id.textView2, R.id.textView1 } );

        //ListActivity設定adapter
        lv1.setAdapter(adapter);

        //設定觸發事件
        lv1.setOnItemClickListener(lv1_lis);
        //啟用按鍵過濾功能，這兩行資料都會進行過濾
        //getListView().setTextFilterEnabled(true);
    }
    private AdapterView.OnItemClickListener lv1_lis = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent it = new Intent();
            it.setClass(activity.this, process.class);
            Bundle bundle = new Bundle();
            bundle.putInt("no", position); //position第幾個
            bundle.putString("number", mNumber[position]);
            it.putExtras(bundle);
            startActivity(it);
        }

    };

}