package com.example.shiyansilajifenlei;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class edit extends AppCompatActivity implements View.OnClickListener {
    Button shanchu,baocun,quxiao;
    EditText danciet,bianjiet;
    int tran = 0;
    database database = new database(this,"Note.db",null,1);

    private void InitNote() {       //进行数据填装
        database dbHelper = new database(this,"Note.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor  = db.query("Note",new String[]{"id","title","content"},"id=?",new String[]{tran+""},null,null,null);
        if(cursor.moveToNext()) {       //根据mainactivity传来的id值选择数据库中对应的行，将值返回
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
        setContentView(R.layout.activity_edit);
        bianjiet = (EditText)findViewById(R.id.bianjiet);
        danciet = (EditText)findViewById(R.id.danciet) ;
        baocun = (Button)findViewById(R.id.baocun);
        quxiao = (Button)findViewById(R.id.quxiao);
        shanchu = (Button)findViewById(R.id.shanchu);

        quxiao.setOnClickListener(this);
        baocun.setOnClickListener(this);
        shanchu.setOnClickListener(this);

        Intent intent = getIntent();
        tran = intent.getIntExtra("tran",-1);       //取出mainactivity传来的id值

        InitNote();

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.shanchu:     //将对应的id行删除

                SQLiteDatabase db = database.getWritableDatabase();
                db.delete("Note","id=?",new String[]{tran+""});
                edit.this.setResult(RESULT_OK,getIntent());
                edit.this.finish();
                break;
            case R.id.baocun:       //保存该界面的数据
                SQLiteDatabase db1 = database.getWritableDatabase();
                ContentValues values = new ContentValues();
                String Title = String.valueOf(danciet.getText());
                String Content = String.valueOf(bianjiet.getText());
                if(Title.length()==0){
                    Toast.makeText(this, "请输入一个标题", Toast.LENGTH_LONG).show();
                }else {
                    values.put("title", Title);
                    values.put("content", Content);
                    db1.update("Note", values, "id=?", new String[]{tran + ""});        //对数据进行更新
                    edit.this.setResult(RESULT_OK, getIntent());
                    edit.this.finish();
                }
                break;


            case R.id.quxiao:
                edit.this.setResult(RESULT_OK,getIntent());
                edit.this.finish();
                break;

        }

    }
}
