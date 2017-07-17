package com.yongmac.login_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    String data;
    TextView t1;
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //ListView에 필요한 ImageView, TextView를 활용할 수 있게 생성해준다
        ImageView iv = (ImageView) findViewById(R.id.iv);
        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);

        Button button = (Button) findViewById(R.id.back); //뒤로 가기 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Main.class);
                startActivity(intent);
            }
        });


        ListView listView = (ListView) findViewById(R.id.listView); //listview생성
        ReqThread thread = new ReqThread(); //Thread를 상속받은 ReqThread 생성

        thread.start(); //스레드 시작
        try {
            thread.join();
        } catch (Exception e) {
        } //스레드 상태 설정

        String wow = thread.data; //ReqThread에서 Thread를 활용하여 얻어온 url에 있는 내용들을 사용할 수 있게 가져온다

        JSAdapter adapter = new JSAdapter(); //BaseAdapter 상속한 Singer
        try {
            JSONObject object = new JSONObject(wow);
            //Json 객체 생성

            JSONObject jOb = (JSONObject) object.get("testCase"); //testCase 객체로 자른다
            JSONArray arr = (JSONArray) jOb.get("testList"); //testList의 배열로 자른다

            //각각에 자른 객체를 String값에 넣기 위해 만듬
            String rank;
            String Nm;
            String url;

            // listView에 데이터를 넣기 위한 for문으로 각 값에 해당하는 rank,Nm,url값을 string값으로 받아서 각각 해당하는 값으로 전환해서 넣어준다
            for (int i = 0; i < arr.length(); i++) {
                JSONObject insideObj = arr.getJSONObject(i);

                rank = (String) insideObj.get("rank"); //rank값 받아오기
                Nm = (String) insideObj.get("Nm"); //Nm값 받아오기
                url = (String) insideObj.get("url"); //url값 받아오기

                adapter.addItem(new Infor(rank,Nm,url));
                //BaseAdapter를 상속받은 JSAapter를 활용하여 값을 넣어준다

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(adapter); //listView 생성을 위해 만들어준다
    }


    //기존 어탭터 상속해서 필요한 만큼 상속
    class JSAdapter extends BaseAdapter { //일반적으로 BaseAdapter 상속
        ArrayList<Infor> items = new ArrayList<Infor>(); //여러개의 데이터가 들어갈 수 있다

        @Override
        public int getCount() {//몇개의 크기가 있나
            return items.size();
        }

        public void addItem(Infor item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) { //
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position; //특정하게 만들어서 리턴
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InforView view = new InforView(getApplicationContext());

            Infor item = items.get(position);
            view.setRank(item.getRank()); //string 값 넣기
            view.setNm(item.getNm()); //string 값 넣기
            view.setUrl(item.getUrl()); //string 값 넣기
            return view; //view형으로 리턴
        }
        //Infro와 InfroView를 통해 만든 get/set함수를 통해서 해당하는 값의 데이터형태로 저장할 때 사용한다
        //리턴형은 View형으로 설정해주고 Adapter를 통해 각 값을 보여준다


    }
}

