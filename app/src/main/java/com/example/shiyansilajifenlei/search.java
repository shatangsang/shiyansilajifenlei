package com.example.shiyansilajifenlei;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyansilajifenlei.R;

import java.util.Date;

public class search extends AppCompatActivity implements View.OnClickListener {
    Button shanchu,baocun,quxiao;
    EditText danciet,bianjiet;
    String danci;
    database database = new database(this,"Note.db",null,1);

    private void InitNote() {
        database database = new database(this,"Note.db",null,1);
        SQLiteDatabase db = database.getWritableDatabase();     //同上，获得可写文件
        Cursor cursor  = db.query("Note",new String[]{"id","title","content"},"title=?",new String[]{danci+""},null,null,null);

        if(cursor.moveToNext()) {       //逐行查找，得到匹配信息
            do {
                String Title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                bianjiet.setText(content);
                danciet.setText(Title);
            } while (cursor.moveToNext());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bianjiet = (EditText)findViewById(R.id.bianjiet);
        danciet = (EditText)findViewById(R.id.danciet) ;
        quxiao = (Button)findViewById(R.id.quxiao);
        baocun = (Button)findViewById(R.id.baocun);
        shanchu = (Button)findViewById(R.id.shanchu);

        quxiao.setOnClickListener(this);
        baocun.setOnClickListener(this);
        shanchu.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        danci = extras.getString("tranTitletoRE");      //接受主界面传来的title值

        InitNote();

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.shanchu:       //删除该title的日志
                Log.d("title is ",danci);

                SQLiteDatabase db = database.getWritableDatabase();
                db.delete("Note","title=?",new String[]{danci+""});     //进行字符串匹配
                search.this.setResult(RESULT_OK,getIntent());
                search.this.finish();
                break;
            case R.id.baocun:         //将文界面内容保存
                SQLiteDatabase db1 = database.getWritableDatabase();        //获取可写文件
                Date date = new Date();
                ContentValues values = new ContentValues();         //获取信息
                String Title = String.valueOf(danciet.getText());
                String Content = String.valueOf(bianjiet.getText());
                if(Title.length()==0){
                    Toast.makeText(this, "请输入一个标题", Toast.LENGTH_LONG).show();
                }else {
                    values.put("title", Title);         //填装信息
                    values.put("content", Content);
                    db1.update("Note", values, "title=?", new String[]{danci + ""});        //字符串匹配
                    search.this.setResult(RESULT_OK, getIntent());        //返回主界面
                    search.this.finish();
                }
                break;


            case R.id.quxiao:
                search.this.setResult(RESULT_OK,getIntent());
                search.this.finish();
                break;

        }

    }
}
