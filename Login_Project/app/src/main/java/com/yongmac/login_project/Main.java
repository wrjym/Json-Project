package com.yongmac.login_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import static com.yongmac.login_project.R.id.et_id;
import static com.yongmac.login_project.R.id.et_password;

public class Main extends Join {

    Button btn_tojoin;
    Button btn_tologin;
    EditText et_id1;
    EditText et_password1;
    SQLiteDatabase db;
    String CID; //테이블 안에 ID값
    String CPW; //테이블 안에 PW값
    String CNAME;
    String id; //et에 받을 id
    String pw; //et에 받을 pw
    int count =0;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                Toast.makeText(Main.this, "facebook으로 로그인 되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Main.this, Login.class);
                startActivity(intent1);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        final DBManger dbManger = new DBManger(getApplicationContext(), "join.db", null, 1);
        btn_tojoin = (Button) findViewById(R.id.btn_join);
        btn_tologin = (Button) findViewById(R.id.btn_login);
        et_id1 = (EditText) findViewById(et_id);
        et_password1 = (EditText) findViewById(et_password);

        Intent intent = getIntent();//회원가입 후 ID 미리 넣어두기
        et_id1.setText(intent.getStringExtra("Join_to_ID"));

        btn_tojoin.setOnClickListener(new Button.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent intent = new Intent(Main.this, Join.class); //회원가입(Join)으로 넘어감
                                              startActivity(intent);
                                              finish();
                                          }
                                      }
        );


        btn_tologin.setOnClickListener(new Button.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               db = dbManger.getReadableDatabase(); //읽기전용으로 설정
                                                count = 0;

                                               if (db != null) {
                                                   id = et_id1.getText().toString();
                                                   pw = et_password1.getText().toString();
                                                   Cursor cursor = db.rawQuery("select * from LOGIN_JOIN", null);
                                                   while (cursor.moveToNext()) {
                                                       CID = cursor.getString(0); //ID값 저장
                                                       CPW = cursor.getString(1); //PW값 저장
                                                       CNAME = cursor.getString(2);
                                                       if (id.equals(CID) && pw.equals(CPW)) { //문자 비교해서 맞으면 Login 창으로
                                                           count = 1;
                                                           Intent intent1 = new Intent(Main.this, Login.class);
                                                           startActivity(intent1);
                                                           Toast.makeText(Main.this, CNAME + "님 반갑습니다", Toast.LENGTH_SHORT).show();
                                                           finish();
                                                       }
                                                   }
                                               }
                                               if(count == 0) {
                                                   count = 1;
                                                   Toast.makeText(Main.this, "로그인 되지 않았습니다.\n 다시 입력하세요.", Toast.LENGTH_SHORT).show(); //테이블안에 없을 수 재요구
                                               }
                                           }
                                       }
        );

    }

    @Override
    protected void onResume() { //로그인후 이전화면으로 올때 값 초기화
        super.onResume();
        et_password1.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
