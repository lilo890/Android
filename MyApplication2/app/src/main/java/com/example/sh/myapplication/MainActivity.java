package com.example.sh.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String editText;
    String isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        ArrayList<String> arrayList = new ArrayList<String>();

        for(int i= 0; i < 29; i++){
            arrayList.add("리스트 추가");
        }

        ListView lv = (ListView)findViewById(R.id.lv);
        TextView tv = (TextView)findViewById(R.id.tv);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(arrayAdapter);

        tv.setText("리스트 갯수는" + lv.getCount());
*/
        final EditText text =(EditText) findViewById(R.id.edit_text);
        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox);
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editText = text.getText().toString();
                isChecked = checkBox.isChecked()?"1":"0";
                AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://192.168.0.7:8080/FriendServer/insertFriend.jsp");

                        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                        nameValuePairList.add(new BasicNameValuePair("name",editText));
                        nameValuePairList.add(new BasicNameValuePair("ischecked",isChecked));

                        try{
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }

                        try{
                            HttpResponse response = httpClient.execute(httpPost);
                            Log.d("Http Post response", response.toString());

                        }catch (ClientProtocolException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                task.execute();
            }
        });

    }
}
