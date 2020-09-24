package com.cedar.mypass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cedar.mypass.ListActivity;
import com.cedar.mypass.R;
import com.cedar.mypass.entity.Data;
import com.cedar.mypass.localSQL.MyDBOpenHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends ArrayAdapter {
    List<Data> infos;
    MyDBOpenHelper myHelper;
    public ItemAdapter(@NonNull Context context, int resource, List<Data> objects) {
        super(context, resource,objects);
        myHelper = new MyDBOpenHelper(context);
        this.infos = objects;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final Data linkeMain = (Data)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_show, null);

        Button item_del = (Button)view.findViewById(R.id.item_del);
        item_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("删除数据:" + linkeMain.getId());
                myHelper.delData(linkeMain.getId());
                //linkeMain = (Data)getItem(position);
                infos.remove(position);//从数据队列中清除数据
                notifyDataSetChanged();//刷新界面
            }
        });

        TextView softname = (TextView)view.findViewById(R.id.item_value_username);
        TextView name = (TextView)view.findViewById(R.id.item_value_name);
        TextView pass = (TextView)view.findViewById(R.id.item_value_pass);

        name.setText(linkeMain.getSoftname());
        pass.setText(linkeMain.getPassword());
        softname.setText(linkeMain.getUsername());

        //RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder();

        return view;

    }
}
