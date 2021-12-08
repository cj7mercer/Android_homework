package diary.example.com.diary;

import android.app.Activity;
import android.os.Bundle;
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


    }
}
