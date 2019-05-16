package cn.edu.swufe.thus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class teamActivity extends AppCompatActivity {

    TextView text12;
    TextView text22;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team1);

        text12 = (TextView) findViewById(R.id.text12 );
        text22 = (TextView) findViewById(R.id.text22 );

    }
    //保存分数
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea=((TextView) findViewById(R.id.text12 )).getText().toString();
        String scoreb=((TextView) findViewById(R.id.text22 )).getText().toString();
        Log.i("onSaveInstanceState","");
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }
//还原分数

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea=savedInstanceState.getString("teama_score");
        String scoreb=savedInstanceState.getString("teamb_score");
        Log.i("onRestoreInstanceState","");
        ((TextView) findViewById(R.id.text12 )).setText(scorea);
        ((TextView) findViewById(R.id.text22 )).setText(scoreb);

    }

    protected void onStart(){
        super.onStart();
        Log.i("second","onStart:");
    }
    protected void onResume(){
        super.onResume();
        Log.i("second","onResume:");
    }
    protected void onRestart(){
        super.onRestart();
        Log.i("second","onRestart:");
    }
    protected void onPause(){
        super.onPause();
        Log.i("second","onPause:");
    }



    public void btnAdd1 (View btn) {
        if(btn.getId()==R.id.btn11 ) {
            show(1);
        }
        else{
            showing(1);
        }

    }

    public void btnAdd2 (View btn) {
        if(btn.getId()==R.id.btn12 ) {
            show(2);
        }
        else{
            showing(2);
        }
    }

    public void btnAdd3 (View btn) {
        if(btn.getId()==R.id.btn13 ) {
            show(3);
        }
        else{
            showing(3);
        }
    }

    public void reset (View btn) {
        text12.setText("0");
        text22.setText("0");
    }
    private void show(int in){
        Log.i("show","in="+in);
        String olds = (String)text12.getText();
        int news = Integer.parseInt(olds)+in;
        text12.setText(""+news);
    }
    private void showing(int in){
        Log.i("show","in="+in);
        String olds = (String)text22.getText();
        int news = Integer.parseInt(olds)+in;
        text22.setText(""+news);
    }

}
