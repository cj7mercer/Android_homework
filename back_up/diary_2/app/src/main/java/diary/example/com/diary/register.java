package diary.example.com.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ASUS on 2021/11/18.
 */

public class register extends Activity {
   private EditText user;
   private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        user= (EditText) findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.startBtn);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String text=pref.getString("user_name","");
        user.setText(text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=user.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                if(!text.equals(""))
                    editor.putString("user_name",text );
                //默认为cj
                else
                    editor.putString("user_name","cj" );
                editor.commit();
                Intent intent = null;
                intent = new Intent(register.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
