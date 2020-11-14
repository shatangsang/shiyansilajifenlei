package com.example.shiyansilajifenlei;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyansilajifenlei.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Integer> IDList = new ArrayList<>();
    private List<String> TADList = new ArrayList<>();
    ArrayAdapter simpleAdapter;
    Button ButtonSeek;
    EditText EditTextSeek;
    String EditTextSeekString ;
    private void InitNote() {       //进行数据填装
        database database = new database(this,"Note.db",null,1);
        SQLiteDatabase db = database.getWritableDatabase();     //通过dbhelper获得可写文件
        Cursor cursor  = db.rawQuery("select * from Note",null);
        IDList.clear();
        TADList.clear();        //清空两个list
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            IDList.add(id);
            TADList.add(title+"\n"+ date);      //对两个list填充数据
        }
    }

    public void RefreshTADList(){       //返回该界面时刷新的方法
        int size = TADList.size();
        //if(size>0){
        TADList.removeAll(TADList);
        IDList.removeAll(IDList);
        simpleAdapter.notifyDataSetChanged();       //清空两个list中的值
        //}
        database dbHelper = new database(this,"Note.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();         //实例化SQLitedatabase
        Cursor cursor  = db.rawQuery("select * from Note",null);
        while(cursor.moveToNext()){         //对两个list重新赋予值
            int id=cursor.getInt(cursor.getColumnIndex("id"));

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            IDList.add(id);
            TADList.add(title+"\n"+ date);      //将title和时间分开显示
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        RefreshTADList();       //调用刷新方法
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitNote();

        Button ButtonAdd;
        ButtonAdd = (Button)findViewById(R.id.add);
        ButtonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, com.example.shiyansilajifenlei.add.class);
                startActivity(intent);
            }
        });

        simpleAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,TADList);       //配置适配器
        ListView ListView = (ListView)findViewById(R.id.lv);
        ListView.setAdapter(simpleAdapter);                 //将两个list中的值通过ArrayList显示出来


        ButtonSeek = findViewById(R.id.chazhao);
        EditTextSeek = findViewById(R.id.seaet);
        ButtonSeek.setOnClickListener(new View.OnClickListener(){       //点击跳转查询界面
            @Override
            public void onClick(View v){
                EditTextSeekString="";
                EditTextSeekString = String.valueOf(EditTextSeek.getText());
                //Log.d("title is ",EditTextSeekString);
                if(EditTextSeekString.length()==0){             //查询为空，给出提示信息
                    RefreshTADList();
                    Toast.makeText(MainActivity.this,"查询值不能为空",Toast.LENGTH_LONG).show();
                }
                else{           //否则通过intent给查询界面传入查询的title
                    Intent intent = new Intent(MainActivity.this, com.example.shiyansilajifenlei.search.class);
                    //intent.putExtra("tranTitle",EditTextSeekString);
                    intent.putExtra("tranTitletoRE",EditTextSeekString);
                    startActivity(intent);

                }
            }
        });

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){      //配置ArrayList点击按钮
            @Override
            public void  onItemClick(AdapterView<?> parent, View view , int position , long id){
                int tran = IDList.get(position);        //点击不同的行，返回不同的id
                Intent intent = new Intent(MainActivity.this, com.example.shiyansilajifenlei.edit.class);
                intent.putExtra("tran",tran);
                startActivity(intent);      //通过intent传输
            }
        });


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){

        }

    }

}
