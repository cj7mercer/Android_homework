package weatherrepoter.example.com.weatherrepoter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.ProcessingInstruction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
//implements实现两个响应函数
public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener{

    private StringBuilder builder;
    private String pre_city_name;
    private Button addBtn;
    private Button btn;
    private Spinner spn1;
//    private TextView spinner_show;
    private List<String> province;
    private ListView listView;
    private Spinner spn2;
    private List<String> cityTempName;
    private List<String> cityTempCode;
    private List<String> cityName;//存储需要显示城市名字
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private HashMap<String,String> hashMap;
    private String city_code_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        pre_city_name=new String("");
        addBtn=(Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        btn=(Button)findViewById(R.id.btn_sub);
        btn.setOnClickListener(this);
        spn1=(Spinner)findViewById(R.id.spinner1);
        spn2=(Spinner)findViewById(R.id.spinner2);
//        spinner_show=(TextView)findViewById(R.id.pro_name_top);
        hashMap=new HashMap<String, String>();
        city_code_pre=new String();
        builder = new StringBuilder();
        cityTempName=new ArrayList<String>();
        cityTempCode=new ArrayList<String>();
        province=new ArrayList<String>();
        cityName=new ArrayList<String>();
        iniArrrayProince();//province初始化完毕
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, province);
        //设置样式
        //内部类使用外部类局部变量必须是常量  -
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cityName);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(adapter);//省级选项适配完毕
        //下边适配县级选项之前
        spn1.setOnItemSelectedListener(this);
        spn2.setOnItemSelectedListener(this);
        listView.setOnItemClickListener(this);
    }

    void iniArrrayProince()
    {
        try {
            //读入文件
            InputStream input=getResources().openRawResource(R.raw._city);
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(isr);
            String line;
            builder = new StringBuilder();
            while((line = br.readLine()) != null){
                builder.append(line);
            }
            br.close();
            isr.close();
            //直接传入JSONObject来构造一个实例
            JSONArray array =new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                if(jsonObject.getInt("pid")==0)//省名
                {
                    province.add(jsonObject.getString("city_name"));//可以知道province中城市的id=position+1
                }
            }//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sub) {
            Intent intent = null;
            intent = new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("city_code",city_code_pre);
            startActivity(intent);
        }
        if(view.getId()==R.id.addBtn)
        {
            if(cityTempName.size()!=0)
            for(int i=0;i<cityTempName.size();i++) {
                if(cityTempName.get(i).equals(pre_city_name))break;
                else if(i==cityTempName.size()-1) {
                    cityTempName.add(pre_city_name);
                    cityTempCode.add(city_code_pre);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            MainActivity.this, android.R.layout.simple_list_item_1, cityTempName);
                    listView.setAdapter(adapter);
                }
            }
            else{
                cityTempName.add(pre_city_name);
                cityTempCode.add(city_code_pre);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1, cityTempName);
                listView.setAdapter(adapter);
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView.getId()== R.id.spinner1) {
            cityName.clear();
            hashMap.clear();
            try {
                JSONArray array = new JSONArray(builder.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if (jsonObject.getInt("pid") == position + 1)//初始化city存储数据
                    {
                        cityName.add(jsonObject.getString("city_name"));
                        hashMap.put(jsonObject.getString("city_name"), jsonObject.getString("city_code"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                spn2.setAdapter(adapter2);//动态加载适配器
            }
        }
        if(adapterView.getId()== R.id.spinner2)
        {
            pre_city_name=cityName.get(position);
            city_code_pre=hashMap.get(pre_city_name);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(adapterView.getId()==R.id.listView)
        {
            Intent intent = null;
            intent = new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("city_code",cityTempCode.get(position));
            startActivity(intent);}
    }
}
