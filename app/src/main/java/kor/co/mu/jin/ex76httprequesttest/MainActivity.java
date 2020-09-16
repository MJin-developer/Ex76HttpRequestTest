package kor.co.mu.jin.ex76httprequesttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText etname, etmsg;
    TextView tv;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etname = findViewById(R.id.et_name);
        etmsg = findViewById(R.id.et_msg);
        tv = findViewById(R.id.tv);
    }

    public void clickGET(View view) {
        new Thread(){
            @Override
            public void run() {
                // 보낼 데이터 얻어오기
                String name = etname.getText().toString();
                String msg = etmsg.getText().toString();

                // GET 방식으로 데이터를 보낼 서버의 주소
                String serverUrl = "http://kimmujin.dothome.co.kr/Android/getTest.php";
                // GET 방식은 보낼 데이터를 URL 뒤에 ? 하고 덧붙여서 보내는 방식

                // 데이터를 포함한 최종 요청 Url , 단 한글은 Url에 사용 불가( utf - 8 방식으로 인코딩 하여 사용해야함 )
                try {
                    name = URLEncoder.encode(name, "utf-8");
                    msg = URLEncoder.encode(msg, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String getUrl = serverUrl + "?name=" + name + "&msg=" + msg;

                try {
                    URL url = new URL(getUrl);
                    // URL 은 InputStream 만 오픈 가능, Output은 불가능 따라서 해주는 놈 필요
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    // 이제 스트림 만들기 가능
                    // URL 안에 이미 보낼 데이터가 전달되었기 때문에 write 작업 불필요 ..
                    OutputStream os = connection.getOutputStream();

                    //getTest.php 가 echo 해준 문자열을 읽어오기 위해 InputStream 필요
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    final StringBuffer buffer = new StringBuffer();

                    while (true){
                        String line = reader.readLine();
                        if(line == null) break;
                        buffer.append(line + "\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void clickPOST(View view) {
        new Thread(){
            @Override
            public void run() {

                String name = etname.getText().toString();
                String msg = etmsg.getText().toString();

                String serverUrl = "http://kimmujin.dothome.co.kr/Android/postTest.php";

                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    // 보낼 데이터
                    String query = "name=" + name + "&msg=" + msg;
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(os);
                    writer.write(query, 0, query.length());
                    writer.flush();
                    writer.close();

                    InputStream is = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader reader2 = new BufferedReader(reader);
                    final StringBuffer buffer = new StringBuffer();

                    while(true){
                        String line = reader2.readLine();
                        if(line == null) break;
                        buffer.append(line + "\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

    public void clickUpload(View view) {
        new Thread(){
            @Override
            public void run() {

                String name = etname.getText().toString();
                String msg = etmsg.getText().toString();

                String serverUrl = "http://kimmujin.dothome.co.kr/Android/insertDB.php";
                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    String query = "name=" + name + "&msg=" + msg;
                    OutputStream os = connection.getOutputStream();
                    os.write(query.getBytes()); // Strung >> Byte[]
                    os.flush();
                    os.close();

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    final StringBuffer buffer = new StringBuffer();

                    while (true){
                        String line = reader.readLine();
                        if(line == null) break;
                        buffer.append(line + "\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void clickload(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }
}
