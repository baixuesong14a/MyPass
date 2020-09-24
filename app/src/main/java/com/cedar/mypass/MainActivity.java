package com.cedar.mypass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cedar.mypass.localSQL.MyDBOpenHelper;


public class MainActivity extends AppCompatActivity {
    //界面元素
    private Button login;
    private Button regis;
    private EditText username;
    private EditText password;

    private MyDBOpenHelper mydbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydbHelper = new MyDBOpenHelper(this);

        //元素关联
        username = (EditText)findViewById(R.id.et_name);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.btn_login);
        regis = (Button)findViewById(R.id.btn_regis);



        //监视器
        View.OnClickListener mylistener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_regis:
                        //Toast.makeText(MainActivity.this,"注册",Toast.LENGTH_LONG);
                        startActivity(new Intent(MainActivity.this,RegisActivity.class));
                        break;
                    case R.id.btn_login:
                        //Toast.makeText(MainActivity.this,"登录",Toast.LENGTH_LONG);
                        String name = username.getText().toString();
                        String pass = password.getText().toString();
                        //登录验证
                        if(mydbHelper.selectUser(name) != null){
                            if(mydbHelper.selectUser(name).getPassword().equals(pass)) {
                                System.out.println("验证成功");
                                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                                intent.putExtra("userid",mydbHelper.selectUser(name).getId());
                                startActivity(intent);
                            }
                            else
                                System.out.println("验证失败");
                        }else{
                            System.out.println("用户不存在");
                            Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_LONG);
                        }

                        //if(true) startActivity(new Intent(MainActivity.this,ListActivity.class));
                        break;
                }
            }
        };
        login.setOnClickListener(mylistener);
        regis.setOnClickListener(mylistener);

    }
}
