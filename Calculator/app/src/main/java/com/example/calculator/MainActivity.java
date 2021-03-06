package com.example.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_clear;
    private Button button_0;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_add;
    private Button button_divide;
    private Button button_equal;
    private Button button_factorial;
    private Button button_minus;
    private Button button_multiply;
    private Button button_point;
    private Button button_reverse;
    private Button button_surplus;
    private EditText input;


    private void initView(){
         button_clear=(Button)findViewById(R.id.button_clear);
         button_0=(Button)findViewById(R.id.button_0);
         button_1=(Button)findViewById(R.id.button_1);
         button_2=(Button)findViewById(R.id.button_2);
         button_3=(Button)findViewById(R.id.button_3);
         button_4=(Button)findViewById(R.id.button_4);
         button_5=(Button)findViewById(R.id.button_5);
         button_6=(Button)findViewById(R.id.button_6);
         button_7=(Button)findViewById(R.id.button_7);
         button_8=(Button)findViewById(R.id.button_8);
         button_9=(Button)findViewById(R.id.button_9);
         button_add=(Button)findViewById(R.id.button_add);
         button_divide=(Button)findViewById(R.id.button_divide);
         button_equal=(Button)findViewById(R.id.button_equal);
         button_factorial=(Button)findViewById(R.id.button_factorial);
         button_minus=(Button)findViewById(R.id.button_minus);
         button_multiply=(Button)findViewById(R.id.button_mutiply);
         button_point=(Button)findViewById(R.id.button_point);
         button_reverse=(Button)findViewById(R.id.button_reverse);
         button_surplus=(Button)findViewById(R.id.button_surplus);
         input=(EditText)findViewById(R.id.input);

         button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_clear.setOnClickListener(this);
        button_divide.setOnClickListener(this);
        button_equal.setOnClickListener(this);
        button_factorial.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        button_point.setOnClickListener(this);
        button_reverse.setOnClickListener(this);
        button_surplus.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    String reverse(String Str) {
        double result=Double.parseDouble(Str);
            return Double.toString(-result);
    }

    //????????????+ - ?? ?? %??????
    String calc(String str) {
        String first="",second="";
        char operator='0';
        int n=0,count=0;//count????????????????????????.?????????
        for(int i=0;i<str.length();i++)
        {
            if(i==0&&(str.charAt(0)=='+'||str.charAt(0)=='-'))
            first+=str.charAt(i);
            if(str.charAt(i)>='0'&&str.charAt(i)<='9'||str.charAt(i)=='.') {
                count++;
                first += str.charAt(i);
            }
            else if(i!=0&&(str.charAt(i)=='+'||str.charAt(i)=='-'||str.charAt(i)=='??'||str.charAt(i)=='??'||str.charAt(i)=='%'))
            {
                operator=str.charAt(i);
                n=i;
                break;
            }
        }
        if(count==str.length()||(str.charAt(0)=='-'&&count==str.length()-1))
             return str;
        for(int j=n+1;j<str.length();j++)
        {
            second+=str.charAt(j);
        }

        double f=Double.parseDouble(first);
        double s=Double.parseDouble(second);

        double result=0;
        switch (operator){
            case '+':
                result=f+s;
                break;
            case '-':
                result=f-s;
                break;
            case '??':
                result=f*s;
                break;
            case '??':
                result=f/s;
                break;
            case '%':
                result=f%s;
                break;
            default:
                input.setText("Operator analysis error");
                break;
        }
        String r =Double.toString(result);

        return r;
    }

    //???????????????????????????????????????(*,/,%)
    boolean check(String str){
        int count=0;
        for(int i=0;i<str.length();i++)
        {
            if((str.charAt(i)<'0'||str.charAt(i)>'9')&&str.charAt(i)!='.'&&str.charAt(i)!='+'&&str.charAt(i)!='-')
                count++;
            if(i!=0&&(str.charAt(i)=='+'||str.charAt(i)=='-')&&str.charAt(i-1)!='+'&&str.charAt(i-1)!='-')
                count++;
        }
        if(count>=1)
            return false;
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        String str=input.getText().toString();

        switch (view.getId()) {
            case R.id.button_0:
                if(str.length()!=0) {
                    str += "0";
                    input.setText(str);
                    break;
                }
                else
                    break;
            case R.id.button_1:
                str+="1";
                input.setText(str);
                break;
            case R.id.button_2:
                str+="2";
                input.setText(str);
                break;
            case R.id.button_3:
                str+="3";
                input.setText(str);
                break;
            case R.id.button_4:
                str+="4";
                input.setText(str);
                break;
            case R.id.button_5:
                str+="5";
                input.setText(str);
                break;
            case R.id.button_6:
                str+="6";
                input.setText(str);
                break;
            case R.id.button_7:
                str+="7";
                input.setText(str);
                break;
            case R.id.button_8:
                str+="8";
                input.setText(str);
                break;
            case R.id.button_9:
                str+="9";
                input.setText(str);
                break;
            case R.id.button_divide:
                if(str.length()!=0&&str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9'){ //str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9'??????????????????????????????????????????
                    if(check(str)) {
                        str += "??";
                        input.setText(str);
                    }
                    else
                        input.setText("You have input so many operators");
                }break;

            case R.id.button_mutiply:
                if(str.length()!=0&&str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9'){
                    if(check(str)) {
                        str += "??";
                        input.setText(str);
                    }
                    else
                        input.setText("You have input so many operators");
                }break;
            case R.id.button_point:
                if(str.length()!=0 && (str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9')){
                    str+=".";
                    input.setText(str);
                }break;
            case R.id.button_add:
                //+?????????????????????????????????????????????+???????????????-??????
                if(str.length()==0 || (str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9')||str.charAt(str.length()-1)=='+'||str.charAt(str.length()-1)=='-'||str.charAt(str.length()-1)=='??'||str.charAt(str.length()-1)=='??'){
                    //??????????????????????????????????????????
                    if(str.length()>=2) {
                        //?????????????????????????????????????????????????????????
                        if ((str.charAt(str.length() - 2) == '+' || str.charAt(str.length() - 2) == '-') && (str.charAt(str.length() - 1) == '+' || str.charAt(str.length() - 1) == '-')) {
                            input.setText("You have input so many operators");
                            break;
                        }
                    }
                    str+="+";
                    input.setText(str);
                }break;
            case R.id.button_minus:
                if(str.length()==0||(str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9')||str.charAt(str.length()-1)=='+'||str.charAt(str.length()-1)=='-'||str.charAt(str.length()-1)=='??'||str.charAt(str.length()-1)=='??'){
                    if(str.length()>=2){
                        if((str.charAt(str.length()-2)=='+'||str.charAt(str.length()-2)=='-')&&(str.charAt(str.length()-1)=='+'||str.charAt(str.length()-1)=='-'))
                        {
                            input.setText("You have input so many operators");
                            break;
                        }
                    }
                    str+="-";
                    input.setText(str);
                }break;
            case R.id.button_surplus:
                if(str.length()!=0&&str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9'){
                    if(check(str)) {
                        str += "%";
                        input.setText(str);
                    }
                    else
                        input.setText("You have input so many operators");
                }break;
            case R.id.button_equal:
                if(str.charAt(str.length()-1)>='0'&&str.charAt(str.length()-1)<='9'){
                input.setText(calc(str));
                }break;

            case R.id.button_clear:
                str="";
                input.setText(str);
                break;
            case R.id.button_reverse:
                input.setText(reverse(calc(str)));
                break;
            case R.id.button_factorial:
                double d=Double.parseDouble(calc(str));
                //??????calc???????????????????????????????????????????????????????????????????????????
                if(d<0)
                    input.setText("You must input correctly");
                else {
                    int d_1 = (int) d;
                    Log.d("MainActivity", Double.toString(d));
                    input.setText(Factorial(Integer.toString(d_1)));
                    break;
                }
        }
    }

    String Factorial(String Str) {
        for(int i=0;i<Str.length();i++)
        {
            if(Str.charAt(i)=='.')
                return "You must input correctly";
        }
        int result_i=Integer.parseInt(Str);

        int r=1;
        if(result_i>=13) {
            Toast.makeText(MainActivity.this, "Value Overflow", Toast.LENGTH_SHORT).show();
            return "";
        }
        if(result_i==1)
            return "1";
        if(result_i==0)
            return "0";
        while(true)
        {
            r = r * result_i;
            result_i--;
            if (result_i == 1)
                return Integer.toString(r);
        }

    }
}
