package com.cedar.mypass;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cedar.mypass.entity.User;
import com.cedar.mypass.localSQL.MyDBOpenHelper;

public class RegisActivity extends Activity {
    private EditText regis_name;
    private EditText regis_pass;
    private EditText regis_repass;
    private Button regis_regbtn;
    private MyDBOpenHelper mydbHelper;
    private SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        mydbHelper = new MyDBOpenHelper(this);
        //mDatabase = mydbHelper.getWritableDatabase();
        Activityinit();



    }

    private void  Activityinit(){
        regis_name = (EditText)findViewById(R.id.regis_name);
        regis_pass = (EditText)findViewById(R.id.regis_pass);
        regis_repass = (EditText)findViewById(R.id.regis_repass);
        regis_regbtn = (Button)findViewById(R.id.regis_regbtn);

        regis_regbtn.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.regis_regbtn:
                    String name = regis_name.getText().toString();
                    String pass = regis_pass.getText().toString();
                    String repass = regis_repass.getText().toString();
                    if (pass.equals(repass)){
                        //System.out.println("输入密码一致");
                        //Toast.makeText(RegisActivity.this,"输入密码一致",Toast.LENGTH_LONG);
                        User user = new User(0,name,pass);
                        mydbHelper.insertUser(user);
                        startActivity(new Intent(RegisActivity.this,MainActivity.class));
                    }else {
                        //System.out.println("输入密码不一致");
                        Toast.makeText(RegisActivity.this,"输入密码不一致",Toast.LENGTH_LONG);
                    }
                break;
            }
        }
    }
}
