package weatherrepoter.example.com.weatherrepoter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2021/12/8.
 */

public class SecondActivity extends Activity {
    public static final int SHOW_RESPONSE = 0;
    private String weatherInfo;
    ArrayList<String> stack;
    static int index=0;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    //这里将需要显示的内容交给weatherInfo
                    weatherInfo = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(weatherInfo);
                        date.setText(jsonObject.getString("time"));
                        JSONObject cityInfo=new JSONObject(jsonObject.getString("cityInfo"));
                        provinceName.setText(cityInfo.getString("parent"));
                        cityName.setText(cityInfo.getString("city"));
                        refreshDate.setText(cityInfo.getString("updateTime"));
                        JSONObject data=new JSONObject(jsonObject.getString("data"));
                        temperature.setText(data.getString("wendu")+"℃");
                        humidity.setText(data.getString("shidu"));
                        PM.setText(data.getString("pm25"));
                        SharedPreferences.Editor editor = getSharedPreferences("data_"+index,
                                MODE_PRIVATE).edit();
                        index=(index+1)%3;
                        editor.putString("date", jsonObject.getString("time"));
                        editor.putString("provinceName", cityInfo.getString("parent"));
                        editor.putString("cityName", cityInfo.getString("city"));
                        editor.putString("refreshDate", cityInfo.getString("updateTime"));
                        editor.putString("temperature", data.getString("wendu"));
                        editor.putString("humidity", data.getString("shidu"));
                        editor.putString("PM", data.getString("pm25"));
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private TextView provinceName;
    private TextView cityName;
    private TextView date;
    private TextView refreshDate;
    private TextView temperature;
    private TextView humidity;
    private TextView PM;
    private String city_code_pre;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showinfo);
        stack=new ArrayList<String>();
        stack.add("");
        stack.add("");
        stack.add("");
        weatherInfo=new String();
        provinceName=(TextView)findViewById(R.id.provinceName);
        cityName=(TextView)findViewById(R.id.cityName);
        date=(TextView)findViewById(R.id.date);
        refreshDate=(TextView)findViewById(R.id.refreshDate);
        temperature=(TextView)findViewById(R.id.temperature);
        humidity=(TextView)findViewById(R.id.humidity);
        PM=(TextView)findViewById(R.id.PM2_5);
        Intent intent=getIntent();
        city_code_pre=intent.getStringExtra("city_code");
        for(int i=0;i<3;i++)
        {
            if(city_code_pre.equals(stack.get(i)))
            {
                SharedPreferences pref = getSharedPreferences("data_"+i,
                        MODE_PRIVATE);
                date.setText(pref.getString("date",""));
                provinceName.setText(pref.getString("provinceName",""));
                cityName.setText(pref.getString("cityName",""));
                refreshDate.setText(pref.getString("refreshDate",""));
                temperature.setText(pref.getString("temperature","")+"℃");
                humidity.setText(pref.getString("shidu",""));
                PM.setText(pref.getString("pm25",""));
                break;
            }
            else if(i==2){
                if(stack.size()<3)
                {
                    stack.add(city_code_pre);
                }
                else {
                    stack.set(index,city_code_pre);
                }
                sendRequestWithHttpURLConnection(city_code_pre);
            }
        }




    }
    //这里可能有问题，关于内部类能不能获取外部类的变量
    private void sendRequestWithHttpURLConnection(final String city_code_pre) {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    //测试通过url没有问题
                    URL url = new URL("http://t.weather.itboy.net/api/weather/city/"+city_code_pre);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
// 下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }//至此全部信息读入到response
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}

