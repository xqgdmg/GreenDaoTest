package com.example.administrator.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import utils.DBUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name_et;
    private EditText age_et;
    private ListView listView;
    private List<String> datas = new ArrayList<>();
    private User user;
    private ArrayAdapter<String> adapter;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView(){
        name_et = (EditText) findViewById(R.id.et_name);
        age_et = (EditText) findViewById(R.id.et_age);
        listView = (ListView) findViewById(R.id.listview);

        // 获取表中所有用户
        List<User> list = DBUser.getInstance(this).queryAllUser();

        // 遍历表中全部数据，添加到集合 datas，这里的集合只是用来显示，显示格式可自定义
        for(int i = 0; i < list.size(); i++){
            datas.add("name = " + list.get(i).getName()+" , age = "+list.get(i).getAge());//定义显示方式
        }

        // 初始化 listview
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);

        // 添加点击事件
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btnOrderByName).setOnClickListener(this);
        findViewById(R.id.btnLimitAndOffset).setOnClickListener(this);

    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View view) {

        // 获得输入的内容
        String name = name_et.getText().toString().trim();
        String age = age_et.getText().toString().trim();

        // 添加到 bean
        user = new User(name,age);

        // 实现 增删改查 通过 DBUser 工具类
        switch (view.getId()){
            case R.id.btn_insert:
                DBUser.getInstance(this).insertUser(user);
                // 显示
                showAllList();
                break;
            case R.id.btn_delete:
                DBUser.getInstance(this).deleteByName(name_et.getText().toString().trim());
                // 显示
                showAllList();
                break;
            case R.id.btn_update:
                DBUser.getInstance(this).update(user);
                // 显示
                showAllList();
                break;
            case R.id.btn_clear:
                DBUser.getInstance(this).clear();
                // 显示
                showAllList();
                break;
            case R.id.btnOrderByName:
                List<User> userList = DBUser.getInstance(this).orderByName("1");
                datas.clear();
                for (int i = 0; i < userList.size(); i++) {
                    Log.e("TAG","Age=" + userList.get(i).getAge() + "Name=" + userList.get(i).getName());
                    datas.add("name = " + userList.get(i).getName()+" , age = "+userList.get(i).getAge());
                }
                // listview 更新界面
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnLimitAndOffset:
                List<User> userList1 = DBUser.getInstance(this).limitAndOffset(offset);
                datas.clear();
                for (int i = 0; i < userList1.size(); i++) {
                    Log.e("TAG","Age=" + userList1.get(i).getAge() + "Name=" + userList1.get(i).getName());
                    datas.add("name = " + userList1.get(i).getName()+" , age = "+userList1.get(i).getAge());
                }

                if (datas.size() <= 0){
                    Toast.makeText(this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                    return;
                }
                // listview 更新界面
                adapter.notifyDataSetChanged();

                offset += 1;
                break;
        }

    }

    /**
     * 更新 listview 中显示的数据
     */
    public void showAllList(){
        List<User> list = DBUser.getInstance(this).queryAllUser();
        datas.clear();// 先清空原来的数据

        // 遍历重新装在 listview 的集合
        for(int i = 0; i < list.size(); i++){
            datas.add("name = " + list.get(i).getName()+" , age = "+list.get(i).getAge());
        }

        // listview 更新界面
        adapter.notifyDataSetChanged();
    }
}
