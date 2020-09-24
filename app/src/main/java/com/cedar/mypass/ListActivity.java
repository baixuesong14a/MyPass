package com.cedar.mypass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.cedar.mypass.Adapter.ItemAdapter;
import com.cedar.mypass.entity.Data;
import com.cedar.mypass.localSQL.MyDBOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity{
    private SearchView searchview;
    private ListView listview;
    private FloatingActionButton addData;
    private Button Adddata;

    private EditText pop_name;
    private EditText pop_user;
    private EditText pop_pass;

    int userid;

    MyDBOpenHelper myHelper;
    ItemAdapter  itemadapter;
    ArrayAdapter adapter;
    //private List<String> dataList = new ArrayList<>();//存储数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //获取当前用户id
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",0);

        //注册组件
        Activityinit();
        //String data[] = {"用户名：xxx\n密码：564564654xxx","用户名：xxx\n密码：564564654xxx"};

        //获取数据并封装进入list
        //String data[] = GetListData();
        if(myHelper.selectlistData(userid) != null) {
            //System.out.println(myHelper.selectlistData().size());
            //for(Data a :myHelper.selectlistData(userid)){//System.out.println(a.getId());}
            itemadapter = new ItemAdapter(this,R.layout.item_show,myHelper.selectlistData(userid));
            //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, GetListData());//新建并配置ArrayAapeter
            listview.setAdapter(itemadapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }


    private void Activityinit(){
        searchview = (SearchView) findViewById(R.id.search);
        searchview.setSubmitButtonEnabled(true);
        searchview.setIconifiedByDefault(true);

        listview = (ListView) findViewById(R.id.list_data);
        //addData = (FloatingActionButton)findViewById(R.id.addData);
        Adddata = (Button)findViewById(R.id.Adddata);
        Adddata.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("新建数据");
                //builder.setIcon(R.drawable.ic)
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.pop_input,null);
                builder.setView(layout);
                pop_name = (EditText)layout.findViewById(R.id.pop_etname);
                pop_pass = (EditText)layout.findViewById(R.id.pop_etpass);
                pop_user  = (EditText)layout.findViewById(R.id.pop_etusername);
                //myHelper.insertData(new Data(0,pop_name.getText().toString(),pop_pass.getText().toString()));
                //builder.setView(new EditText(ListActivity.this));
                builder.setPositiveButton("是",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        //System.out.println("弹出框信息" + pop_name.getText().toString() + "\n"+pop_pass.getText().toString());
                        myHelper.insertData(new Data(0,pop_name.getText().toString(),pop_user.getText().toString(),pop_pass.getText().toString(),userid));
                        onResume();
                    }
                });
                builder.setNegativeButton("否",null);
                builder.show();
            }
        });
        //item_del = (Button)findViewById(R.id.item_del);

        myHelper = new MyDBOpenHelper(this);
        //addData.setOnClickListener(new MyOnClickListener());
        //Adddata.setOnClickListener(new MyOnClickListener());
        searchview.setOnQueryTextListener(new MyOnQueryTextListener());
        //listview.setOnItemLongClickListener(new MyOnItemLongClickListener());
    }

    private class  MyOnQueryTextListener implements SearchView.OnQueryTextListener {
        private String TAG = getClass().getSimpleName();

        @Override
        public boolean onQueryTextSubmit(String s) {
            //System.out.println("搜索框输入" + s);
            if(myHelper.selectlistData(userid,s) != null) {
                itemadapter = new ItemAdapter(ListActivity.this, R.layout.item_show, myHelper.selectlistData(userid, s));
                listview.setAdapter(itemadapter);
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            //System.out.println("搜索框输入" + s + "\n" + s.length());
            if(s.length() == 0){
                //System.out.println("s == 0");
                if(myHelper.selectlistData(userid) != null) {
                    itemadapter = new ItemAdapter(ListActivity.this, R.layout.item_show, myHelper.selectlistData(userid));
                    listview.setAdapter(itemadapter);
                }
            }
            return false;
        }
    }
}