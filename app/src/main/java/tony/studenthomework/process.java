package tony.studenthomework;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

/**
 * 2015/9/15修改
 * 將HttpPost改為使用HttpURLConnection
 */

public class process extends AppCompatActivity {
    private static final String ACTUAL_MACHINE_IP = "10.0.2.2";
    private static final String HOST_NAME = ACTUAL_MACHINE_IP.concat(":5001");
    private static final String URL = "http://".concat(HOST_NAME).concat("/ns_android.php");

    //全域設定
    private TextView title;
    private Button save;

    int no;
    String number;

    private static final String ACTIVITY_TAG="LogDemo";

    //SharedPreferences
    private SharedPreferences settings;
    private static final String data = "DATA";

    //ListView
    private ListView list;
    private static final String[] Title={
            "物件梯形","power","prime","prime質數","遞迴1+..n","費氏數列","triangle","max()","Date"
            ,"m1作業","Circle","cs繼承"	,"十點半","物件擲骰子","抽象類別","介面作業","大整數+-gcd","500!"
            ,"自畫像","三角圖","三角圓圖","UI梯形","會考畫面","車子移動","AZ上下","簡易畫板","鼠左右鍵上下"
            ,"滑鼠狀態","猜拳","猜數字","按鈕汽車","擲骰子","畫板筆觸","速度與方向","按鈕文字","帳號密碼"
            ,"彈跳梯形","滑動三角形","亂數檔案","檔案合併","讀檔計算","矩陣","圖檔轉黑白","時鐘"
    };

    String[] Status=new String[44];

    //mThread
    protected static final int REFRESH_DATA = 0x00000001;
    protected static final int GET_DATA = 0x00000002;

    /** 建立UI Thread使用的Handler，來接收其他Thread來的訊息 */
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case GET_DATA:
                    String webresult = null;
                    if (msg.obj instanceof String)
                        webresult = (String) msg.obj;
                    if (webresult != null){
                        //Toast.makeText(process.this, webresult, Toast.LENGTH_LONG).show();
                        init(); //設定初始
                        MyCheckBoxAdapter.init(webresult);

                    }
                    break;

                // 顯示網路上抓取的資料
                case REFRESH_DATA:
                    String result = null;
                    if (msg.obj instanceof String)
                        result = (String) msg.obj;
                    if (result != null){
                        // 印出網路回傳的文字
                        Toast.makeText(process.this, number + "修改完成!", Toast.LENGTH_SHORT).show();
                        finish(); //關掉這個activity(馬上返回上一頁)
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process);

        Bundle process = this.getIntent().getExtras();
        no = process.getInt("no");
        number = process.getString("number");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class", "pgt");
                params.put("mode", "4");
                params.put("stu_no", String.valueOf(no));

                String result = "";
                try {
                    result = submitPostData(URL,params,"utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.obtainMessage(GET_DATA, result).sendToTarget(); //改變UI
            }
        }).start();
    }

    public void init() {
        title = (TextView)findViewById(R.id.title);
        title.setText(number);
        //ListView
        list = (ListView) findViewById(R.id.MyListView);
        MyCheckBoxAdapter adapter = new MyCheckBoxAdapter(this,getData());
        list.setAdapter(adapter);

        //Button
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save(){
        String ans = "";
        for(java.util.Map.Entry<Integer, Boolean> entry : MyCheckBoxAdapter.isSelected.entrySet()) {
            if(entry.getValue()) {
                ans+=1;
            } else {
                ans+=0;
            }
        }
        final String finalAns = ans;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class", "pgt");
                params.put("mode", "3");
                params.put("stu_no", String.valueOf(no));
                params.put("ans", finalAns);
                String result = "";
                try {
                    result = submitPostData(URL,params,"utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget(); //改變UI
            }
        }).start();
    }

    List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();

    private List<Map<String, String>> getData() {
        for (int i = 0; i < Title.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("itemTitle", Title[i]);
            mylist.add(map);
        }
        return mylist;
    }

    /*
    * Function  :   發送Post請求到伺服器
    * Param     :   params請求體內容，encode編碼格式
    * Author    :   博客園-依舊淡然
    */
    public static String submitPostData(String urlstr,Map<String, String> params, String encode) throws Exception{
        URL url = new URL(urlstr);
        byte[] data = getRequestData(params, encode).toString().getBytes(); //獲得請求體
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
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
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //處理伺服器的回應結果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * Function  :   封裝請求體資訊
     * Param     :   params請求體內容，encode編碼格式
     * Author    :   博客園-依舊淡然
     */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存儲封裝好的請求體資訊
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
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

    /*
     * Function  :   處理伺服器的回應結果（將輸入流轉化成字串）
     * Param     :   inputStream伺服器的回應輸入流
     * Author    :   博客園-依舊淡然
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存儲處理結果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
