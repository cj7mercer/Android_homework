package diary.example.com.diary;

import android.content.Context;;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ASUS on 2021/11/16.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_DIARY = "create table diary ("
            + "id integer primary key, "
            + "author text, "
            + "month integer, "
            + "day integer, "
            + "name text, "
            + "content text,"
            + "image blob)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        if(mContext==)
        db.execSQL(CREATE_DIARY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
