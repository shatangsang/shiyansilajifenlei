package com.example.shiyansilajifenlei;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class add extends AppCompatActivity implements View.OnClickListener {
    String danci,beizhu,simpleDate;
    Button addquxiao,addbaocun;
    EditText adddanciet,addbianjiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addquxiao = (Button)findViewById(R.id.addquxiao);
        addbaocun = (Button)findViewById(R.id.addbaocun);
        addbianjiet = findViewById(R.id.addbianjiet);
        adddanciet = findViewById(R.id.adddanciet);
        addquxiao.setOnClickListener(this);
        addbaocun.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        database database = new database(this,"Note.db",null,1);
        SQLiteDatabase db = database.getWritableDatabase();
        switch (v.getId()) {
            case R.id.addbaocun:
                Date date = new Date();
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //配置时间格式
                simpleDate = simpleDateFormat.format(date);
                ContentValues values = new ContentValues();
                danci = String.valueOf(adddanciet.getText());         //获取需要储存的值
                beizhu = String.valueOf(addbianjiet.getText());
                Log.d("Title",danci);
                if(danci.length()==0){               //标题为空给出提示
                    Toast.makeText(this, "请输入一个标题", Toast.LENGTH_LONG).show();
                }else {
                    values.put("title", danci);
                    values.put("content", beizhu);
                    values.put("date", simpleDate);
                    db.insert("Note", null, values);                 //将值传入数据库中
                    add.this.setResult(RESULT_OK, getIntent());
                    add.this.finish();
                }
                break;

            case R.id.addquxiao:
                add.this.setResult(RESULT_OK,getIntent());
                add.this.finish();

                break;
        }


    }
}
