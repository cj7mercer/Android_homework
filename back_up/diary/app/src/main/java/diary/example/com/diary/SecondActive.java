package diary.example.com.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by ASUS on 2021/11/16.
 */

public class SecondActive extends Activity {
    private MyDatabaseHelper dbHelper;
    //日记页面的内容
    private String content;
    //日记页面的标题
    private String name;
    private Button btn_sub;
    private Button btn_del;
    private EditText edit_content;
    private EditText edit_name;
    static int getindex=0;

    //存储未使用id号，防止下一次启动重复
    private void saveIndex()
    {
        SharedPreferences.Editor editor = getSharedPreferences("data",
                MODE_PRIVATE).edit();
        editor.putInt("getIndex", getindex);
        editor.commit();
    }

    private int readgetIndex()
    {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        getindex=pref.getInt("getIndex",0);
        return getindex;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        edit_name=(EditText)findViewById(R.id.edit);
        edit_content=(EditText)findViewById(R.id.edit2);
        btn_sub=(Button)findViewById(R.id.subBtn);
        btn_del=(Button)findViewById(R.id.delBtn);
        //创建数据库
        dbHelper = new MyDatabaseHelper(this, "MyDatabase", null, 1);
        //在这里调用 onCreate
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //接收上一个活动的数据index
        Intent intent=getIntent();
        //以传入index不同为别，分为新建和修改
        final int index=intent.getIntExtra("index",-1);
        final boolean isCreate=intent.getBooleanExtra("isCreate",false);
        //final int num=intent.getIntExtra("num",-1);//接收arrayList哥个数
        //根据index选择是否载入数据库
        //载入数据库
        if(!isCreate)
        {
            //可能出问题，好像是最新版的rawQuery
            Cursor cursor = db.rawQuery("select content,name from diary where id = '"+index+"'",null);
            if (cursor.moveToFirst()) {
                     content = cursor.getString(cursor.getColumnIndex("content"));
                     name = cursor.getString(cursor.getColumnIndex("name"));
            }
            cursor.close();
            edit_name.setText(name);
            edit_content.setText(content);
        }



        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此时说明该页面已存入数据库
                if(!isCreate){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("delete from diary where id='"+index+"'" );
                    Intent intent = new Intent();
                    intent.putExtra("delete_id",index);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    intent.putExtra("delete_id",-2);
                    finish();
                }
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改页面
                if(!isCreate)//内部类可以使用外部类的变量
                {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    name=edit_name.getText().toString();
                    content=edit_content.getText().toString();
                    db.execSQL("update diary set name='"+name+"', content='"+content+"'where id='"+index+"'");

                    //返回第一个页面
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("id",index);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                //新建页面
                else{
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    name=edit_name.getText().toString();
                    content=edit_content.getText().toString();

                    Calendar c = Calendar.getInstance();
                    int month=c.get(Calendar.MONTH);
                    int day=c.get(Calendar.DAY_OF_MONTH);
                    int id=readgetIndex();
                    getindex++;
                    saveIndex();
                    db.execSQL("insert into diary(id,author,month,day,name,content) values("+id+",'cj',"+month+","+day+",'"+name+"','"+content+"')");

                    //返回第一个页面
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("month",month);
                    intent.putExtra("day",day);
                    intent.putExtra("id",id);
                    intent.putExtra("isCreate",true);
                    //intent.putExtra("isCreate",true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
