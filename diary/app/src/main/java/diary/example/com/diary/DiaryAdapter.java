package diary.example.com.diary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ASUS on 2021/11/16.
 */

public class DiaryAdapter extends ArrayAdapter<Diary> {
    private int resourceId;
    public DiaryAdapter(@NonNull Context context, int resource, List<Diary> objects ) {
        super(context, resource,objects);
        resourceId=resource;
    }


    //当子项被滚动到屏幕内时调用
    public View getView(int position, View convertView, ViewGroup parent) {
        Diary diary = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
       TextView diary_time= (TextView) view.findViewById(R.id.diary_time);
        TextView diary_name = (TextView) view.findViewById(R.id.diary_name);
        diary_name.setText(diary.getName());
        String time=diary.getMonth()+"月"+diary.getDay()+"日";
        diary_time.setText(time);
        return view;
    }
}
