package cn.edu.swufe.thus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class rateActivity extends AppCompatActivity implements Runnable {

    EditText rmb;
    TextView show;
    private float d=0.0f;
    private float e=0.0f;
    private float w=0.0f;
    private String updateDate="";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = (EditText) findViewById(R.id.rmb);
        show = (TextView) findViewById(R.id.show);

            //获取SP里保存的数据
        SharedPreferences sharedPreferences=getSharedPreferences("myrate",Activity.MODE_PRIVATE);

        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
        d=sharedPreferences.getFloat("dollar_rate",0.0f);
        e=sharedPreferences.getFloat("euro_rate",0.0f);
        w=sharedPreferences.getFloat("won_rate",0.0f);

        updateDate=sharedPreferences.getString("update_date","");
        //获取当前系统时间
        Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd");
        final String todayStr=sdf.format(today);
        Log.i("dollarrate","onCreate:sp dollar_rate="+d);
        Log.i("eurorate","onCreate:sp euro_rate="+e);
        Log.i("wonrate","onCreate:sp won_rate="+w);
        Log.i("updateDate","onCreate:sp updateDate"+updateDate);
        Log.i("onCreate","todayStr="+todayStr);
        //判断时间
        if(!todayStr.equals((updateDate))){
            Log.i("onCreate","需要更新");
            //开启子线程
            Thread t =new Thread(this);
            t.start();
        }
        else{
            Log.i("onCreate","不需要更新");
        }


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==5){
                    Bundle bdl=(Bundle) msg.obj;
                    d=bdl.getFloat("dollar-rate");
                    e=bdl.getFloat("euro-rate");
                    w=bdl.getFloat("won-rate");


                    Log.i("handleMessage:","dollar-rate="+d);
                    Log.i("handleMessage","euro-rate="+e);
                    Log.i("handleMessage","won-rate="+w);

                    //保存更新的日期
                    SharedPreferences sharedPreferences=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putFloat("dollar_rate",d);
                    editor.putFloat("euro_rate",e);
                    editor.putFloat("won_rate",w);
                    editor.putString("update_date",todayStr);
                    editor.apply();

                    Toast.makeText(rateActivity.this, "汇率已更新", Toast.LENGTH_SHORT).show();

                }
                super.handleMessage(msg);
            }
        };


    }

    public void onClick(View btn){
        String str = rmb.getText().toString();
        float r=0;
        float val=0;


        if(str.length()>0){
            r= Float.parseFloat(str);
        }
        else {
            Toast.makeText(this,"请输入金额" ,Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.btnD){
            show.setText(String.format("%.2f",r*d));
        }
        else if(btn.getId()==R.id.btnE){
            show.setText(String.format("%.2f",r*e));
        }
        else if(btn.getId()==R.id.btnW){
            show.setText(String.format("%.2f",r*w));
        }
    }
    public void openOne(View btn){
        openConfig();

    }

    private void openConfig() {
        Log.i("open", "openOne: ");
        Intent config= new Intent(this,configActivity.class);
        config.putExtra("dollar",d);
        config.putExtra("euro",e);
        config.putExtra("won",w);
        Log.i("dollar", "openOne: dollar"+d);
        Log.i("euro", "openOne: euro"+e);
        Log.i("won", "openOne: won"+w);
        //startActivity(config);
        startActivityForResult(config,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==1 && resultCode==2){
            Bundle bundle=data.getExtras();
            d=bundle.getFloat("dollar",0.1f);
            e=bundle.getFloat("euro",0.1f);
            w=bundle.getFloat("won",0.1f);

            Log.i("dollar", "onActivityResult: dollar"+d);
            Log.i("euro", "onActivityResult: euro"+e);
            Log.i("won", "onActivityResult: won"+w);


            //写入新设置的汇率到SP中
            SharedPreferences sharedPreferences=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putFloat("dollar_rate",d);
            editor.putFloat("euro_rate",e);
            editor.putFloat("won_rate",w);
            editor.commit();
            Log.i("onActivityResult","onActivityResult:数据已保存到sharedPreferences");
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menua,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set1){
            openConfig();
        }
        else if(item.getItemId()==R.id.open_list){
            //打开列表窗口
            //Intent list= new Intent(this,RateListActivity.class);
            Intent list= new Intent(this,MyList2Activity.class);

            startActivity(list);


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        Log.i("run","run:run...........");
        try {
                Thread.sleep(2000);
            }
        catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        //用于保存获取的汇率
        Bundle bundle;





        //获取网络数据

        /**URL url= null;
        try {
            url = new URL("http://www.boc.cn/sourcedb/whpj/");
            HttpURLConnection http= (HttpURLConnection) url.openConnection();
            InputStream in=http.getInputStream();

            String html=inputStream2String(in);
            Log.i("run","run:html="+html);


        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
**/

        bundle=getFromBOC();
        //bundle 中保存获取的汇率
        //获取MSG对象，用于返回主线程
        Message msg=handler.obtainMessage(5);
        //msg.what=5;
        //msg.obj="Hello!!!!";
        msg.obj=bundle;
        handler.sendMessage(msg);

    }
/**从bank of china 中获取数据*/
    private Bundle getFromBOC() {
        Bundle bundle=new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            Log.i("run:",doc.title());
            Elements tables = doc.getElementsByTag("table");
            /**for (Element table : tables) {
                Log.i("run:table["+i+"]=", String.valueOf(table));
                i++;

            }**/
            Element table1=tables.get(1);
            Log.i("run:table1=", String.valueOf(table1));
            //获取TD中的数据
            Elements tds=table1.getElementsByTag("td");
            /**for (Element td : tds) {
                Log.i("run:td["+i+"]=", String.valueOf(td));
                i++;

            }*/
            for(int i=0;i<tds.size();i+=8){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i("run:", td1.text()+"==>"+td2.text());
                String str1=td1.text();
                String val=td2.text();
                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }
                else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }
                if("韩国元".equals(str1)){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));
                }
            }

        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return bundle;
    }

    private  String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer =new char[bufferSize];
        final StringBuilder out =new StringBuilder();
        Reader in =new InputStreamReader(inputStream,"UTF-8");
        for(;;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0){
                break;
            }
            out.append(buffer,0,rsz);

        }
        return out.toString();
    }
}













