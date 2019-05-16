package cn.edu.swufe.thus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class configActivity extends AppCompatActivity {
    EditText dollarText;
    EditText euroText;
    EditText wonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent =getIntent();
        float dollar2=intent.getFloatExtra("dollar",0.0f);
        float euro2=intent.getFloatExtra("euro",0.0f);
        float won2=intent.getFloatExtra("won",0.0f);
        Log.d("cgt", "onCreate: dollar2"+dollar2);
        Log.d("cgt", "onCreate: euro2"+euro2);
        Log.d("cgt", "onCreate: won2"+won2);

        dollarText=(EditText)findViewById(R.id.editD);
        euroText=(EditText)findViewById(R.id.editE);
        wonText=(EditText)findViewById(R.id.editW);

        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));


    }


    public void save(View btn) {
        Log.d("cfg", "save: ");
        //获取新的值
        float newdollar=Float.parseFloat(dollarText.getText().toString());
        float neweuro=Float.parseFloat(euroText.getText().toString());
        float newwon=Float.parseFloat(wonText.getText().toString());

        Log.d("cgt", "save: newdollar"+newdollar);
        Log.d("cgt", "save: neweuro"+neweuro);
        Log.d("cgt", "save: newwon"+newwon);
        //保存到bundle或者放入extra
        Intent intent =getIntent();
        Bundle bdl=new Bundle();
        bdl.putFloat("dollar",newdollar);
        bdl.putFloat("euro",neweuro);
        bdl.putFloat("won",newwon);
        intent.putExtras(bdl);
        setResult(2,intent);
        //返回调用页面
        finish();

    }
}
