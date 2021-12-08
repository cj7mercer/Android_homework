package diary.example.com.diary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private List<Diary> diaryList = new ArrayList<Diary>();
    private  ListView listView;
    private DiaryAdapter adapter;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //当前上下文，布局id，适配数据
        adapter = new DiaryAdapter(this, R.layout.diary_item, diaryList);
        //从数据库读入
        initdiaryList();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                intent = new Intent(MainActivity.this,SecondActive.class);
                intent.putExtra("index",diaryList.get(i).getId());
                intent.putExtra("isCreate",false);

                startActivityForResult(intent,1);
            }
        });
        listView.setAdapter(adapter);

        Button btn=(Button)findViewById(R.id.addBtn);
        btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //这一部分写增加数据库中记录，还要负责跳转
                Intent intent = null;
                intent = new Intent(MainActivity.this,SecondActive.class);
                intent.putExtra("isCreate",true);
                Log.d("MainActivity","到这");

                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        listView.setAdapter(adapter);

    }

    private void initdiaryList(){
        //创建数据库
        dbHelper = new MyDatabaseHelper(this, "MyDatabase", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id,month,day,name from diary ",null);
        if (cursor.moveToFirst()) {
            do {
             int id= cursor.getInt(cursor.getColumnIndex("id"));
             int month=cursor.getInt(cursor.getColumnIndex("month"));
             int day=cursor.getInt(cursor.getColumnIndex("day"));
             String name = cursor.getString(cursor.getColumnIndex("name"));
             Diary diary_temp=new Diary(id,month,day,name);
             diaryList.add(diary_temp);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("name");
                    int returnId= data.getIntExtra("id",-1);
                    int returnDay=data.getIntExtra("day",-1);
                    int returnMonth=data.getIntExtra("month",-1);
                    int delete_id=data.getIntExtra("delete_id",-1);
                    if(delete_id==-1){
                        boolean isCreate=data.getBooleanExtra("isCreate",false);
                        //创建新记录
                        if(isCreate)
                        {
                            Diary diary_temp=new Diary();
                            diary_temp.setDay(returnDay);
                            diary_temp.setMonth(returnMonth);
                            diary_temp.setId(returnId);
                            diary_temp.setName(returnedData);
                            diaryList.add(diary_temp);
                        }
                        //修改name
                        else{
                            Diary diary_temp = diaryList.get(returnId);
                            diary_temp.setName(returnedData);
                            diaryList.set(returnId, diary_temp);
                        }
                    }
                    else
                    {
                        for(int m=0;m<diaryList.size();m++) {
                            if(diaryList.get(m).getId()==delete_id)
                            diaryList.remove(m);
                        }
                    }
                }
                break;
            default:
        }
    }

}