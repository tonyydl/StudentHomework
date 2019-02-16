package tony.studenthomework;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    private static final String URL = "http://10.0.2.2:5001/ns_android.php";
    private static final String[] TITLES = {
            "物件梯形", "power", "prime", "prime質數", "遞迴1+..n", "費氏數列", "triangle", "max()", "Date", "m1作業", "Circle", "cs繼承", "十點半", "物件擲骰子", "抽象類別", "介面作業", "大整數+-gcd", "500!", "自畫像", "三角圖", "三角圓圖", "UI梯形", "會考畫面", "車子移動", "AZ上下", "簡易畫板", "鼠左右鍵上下", "滑鼠狀態", "猜拳", "猜數字", "按鈕汽車", "擲骰子", "畫板筆觸", "速度與方向", "按鈕文字", "帳號密碼", "彈跳梯形", "滑動三角形", "亂數檔案", "檔案合併", "讀檔計算", "矩陣", "圖檔轉黑白", "時鐘"
    };

    private static final int REFRESH_DATA = 0x00000001;
    private static final int GET_DATA = 0x00000002;

    private MyCheckBoxAdapter adapter;

    private int no;
    private String number;

    /**
     * 建立UI Thread使用的Handler，來接收其他Thread來的訊息
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA:
                    if (msg.obj instanceof String) {
                        String webResult = (String) msg.obj;
                        if (webResult != null) {
                            adapter.update(webResult);
                        }
                    }
                    break;
                case REFRESH_DATA:
                    String result = null;
                    if (msg.obj instanceof String)
                        result = (String) msg.obj;
                    if (result != null) {
                        Toast.makeText(ReviewActivity.this, number + "修改完成!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initParameters();
        initViews();
        thread.start();
    }

    private void initParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            no = bundle.getInt("no");
            number = bundle.getString("number");
        }
    }

    private void initViews() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText(number);
        ListView homeworkListView = findViewById(R.id.homework_listview);
        adapter = new MyCheckBoxAdapter(this, getData());
        homeworkListView.setAdapter(adapter);
        Button save = findViewById(R.id.save_btn);
        save.setOnClickListener(v -> save());
    }

    private void save() {
        StringBuilder ans = new StringBuilder();
        SparseBooleanArray selectedItems = MyCheckBoxAdapter.isSelected;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(selectedItems.keyAt(i))) {
                ans.append(1);
            } else {
                ans.append(0);
            }
        }
        final String finalAns = ans.toString();
        new Thread(() -> {
            Map<String, String> params = new HashMap<>();
            params.put("class", "pgt");
            params.put("mode", "3");
            params.put("stu_no", String.valueOf(no));
            params.put("ans", finalAns);
            String result = "";
            try {
                result = submitPostData(URL, params, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget(); //改變UI
        }).start();
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Map<String, String> params = new HashMap<>();
            params.put("class", "pgt");
            params.put("mode", "4");
            params.put("stu_no", String.valueOf(no));

            String result = "";
            try {
                result = submitPostData(URL, params, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.obtainMessage(GET_DATA, result).sendToTarget(); //改變UI
        }
    });

    List<Map<String, String>> mylist = new ArrayList<>();

    private List<Map<String, String>> getData() {
        for (String aTitle : TITLES) {
            Map<String, String> map = new HashMap<>();
            map.put("itemTitle", aTitle);
            mylist.add(map);
        }
        return mylist;
    }

    public static String submitPostData(String urlstr, Map<String, String> params, String encode) throws Exception {
        URL url = new URL(urlstr);
        byte[] data = getRequestData(params, encode).toString().getBytes(); //獲得請求體
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);          //設置連接逾時時間
            httpURLConnection.setDoInput(true);                 //打開輸入流，以便從伺服器獲取資料
            httpURLConnection.setDoOutput(true);                //打開輸出流，以便向伺服器提交資料
            httpURLConnection.setRequestMethod("POST");         //設置以Post方式提交資料
            httpURLConnection.setUseCaches(false);              //使用Post方式不能使用緩存
            //設置請求體的類型是文本類型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //設置請求體的長度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //獲得輸出流，向伺服器寫入資料
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //獲得伺服器的回應碼
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //處理伺服器的回應結果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存儲封裝好的請求體資訊
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //刪除最後的一個"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
