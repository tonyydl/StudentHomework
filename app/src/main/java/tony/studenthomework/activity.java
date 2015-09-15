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

    //總共有64個學生
    private static String[] mName = new String[]
            {
                    "蔡思瑩","施巧翎","廖婉彤","劉文景","黃鵬翰","張宜靖","許添宗","陳柏霖","王韋竣","鄭名佑",
                    "黃琮華","王于庭","方嘉翎","方珮諭","王嘉苓","林靖雯","許家和","黃士育","夏睿豐","陳孟菱",
                    "陳巧口","蘇家緯","羅于婷","何旻政","李韋霖","楊祈峰","蘇柏彥","李佳穎","雛宜真","林佳陵",
                    "余欣燕","何彩萍","董丞雅","蔡千慧","郭芷良","王柏貴","楊于萱","莊承駿","楊宗翰","王柏盛",
                    "李建億","李杰陽","林宏陽","王孟儒","蘇奕綸","范梓洋","陳俊憲","吳允中","陳柏翰","吳侑澤",
                    "葉峻霖","陳沂鈴","林惠姿","朱亞萱","田偉廷","許品彥","莊詠婷","呂睿峰","蘇冠維","吳仲賢",
                    "江承憲","郭婷雯","劉瓊廣","張文濤"
            };
    private static String[] mNumber = new String[]
            {
                    "4001C005",
                    "4010C001","4010C003","4010C004","4010C005","4010C006",
                    "4010C007","4010C008","4010C009","4010C010","4010C011",
                    "4010C012","4010C013","4010C014","4010C015","4010C016",
                    "4010C017","4010C018","4010C019","4010C020","4010C021",
                    "4010C022","4010C023","4010C024","4010C025","4010C026",
                    "4010C027","4010C028","4010C029","4010C030","4010C031",
                    "4010C032","4010C033","4010C034","4010C035","4010C036",
                    "4010C037","4010C038","4010C040","4010C041","4010C042",
                    "4010C043","4010C044","4010C045","4010C046","4010C047",
                    "4010C048","4010C049","4010C051","4010C052","4010C053",
                    "4010C054","4010C055","4010C056","4010C057","4010C059",
                    "4010C060","4010C061","4010C062","4010C064","4010C065",
                    "4010C066","4013C204","4013C203"
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