package tony.studenthomework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText id,pw;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }
    public void init(){
        id=(EditText)findViewById(R.id.editText1);
        pw=(EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals("adm") && pw.getText().toString().equals("159889")){
                    Intent it = new Intent();
                    it.setClass(login.this, activity.class);

                    //Bundle bundle = new Bundle();
                    //bundle.putInt("no", position); //position第幾個
                    //bundle.putString("number", mNumber[position]);

                    //it.putExtras(bundle);
                    Toast.makeText(login.this, "登入成功！", Toast.LENGTH_SHORT).show();
                    startActivity(it);
                } else {
                    Toast.makeText(login.this, "帳號或密碼錯誤！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}