package cn.edu.swufe.thus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView out;
    float F;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(this);

        out=findViewById(R.id.textView2);
    }
    @Override
    public void onClick(View v) {
        try{
            EditText editText=findViewById(R.id.editText);
            String str=editText.getText().toString();
            float C=Float.parseFloat(str);
            F=(float)C*9/5+32;
            DecimalFormat df = new DecimalFormat( "0.00");
            String  f=df.format(F);
            F=Float.parseFloat(f);
            Log.i("convert","converted");
            out.setText("华氏度为:"+F+"°F");
        }
        catch(NumberFormatException e){
            out.setText("请输入表示温度的数字！");
        }

    }
}
