package tony.studenthomework;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tony.studenthomework.student.StudentListActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    public void initViews() {
        EditText idEt = findViewById(R.id.id_et);
        EditText pwEt = findViewById(R.id.pw_et);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            if (idEt.getText().toString().equals("adm") && pwEt.getText().toString().equals("159889")) {
                Intent it = new Intent();
                it.setClass(LoginActivity.this, StudentListActivity.class);
                Toast.makeText(LoginActivity.this, "登入成功！", Toast.LENGTH_SHORT).show();
                startActivity(it);
            } else {
                Toast.makeText(LoginActivity.this, "帳號或密碼錯誤！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}