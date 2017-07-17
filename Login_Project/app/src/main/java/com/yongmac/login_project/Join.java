package com.yongmac.login_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.yongmac.login_project.R.id.j_et_id;
import static com.yongmac.login_project.R.id.j_et_pw;

public class Join extends AppCompatActivity {

    SQLiteDatabase db;
    String CID;
    int count = 0, count1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        //테이블 생성
        final DBManger dbManger = new DBManger(getApplicationContext(), "join.db", null, 1);

        //테이블 초기화
        //dbManger.delete("DELETE FROM LOGIN_JOIN");

        //EditText 값 받기
        final EditText jetid = (EditText) findViewById(j_et_id);
        final EditText jetpw = (EditText) findViewById(j_et_pw);
        final EditText jetname = (EditText) findViewById(R.id.j_et_name);
        final EditText jetpn = (EditText) findViewById(R.id.j_et_pn);

        Button button = (Button) findViewById(R.id.bt_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Join.this, Main.class);
                startActivity(intent);
            }
        });
        //입력해서 삽입하는 부분의 버튼
        Button button1 = (Button) findViewById(R.id.bt_insert);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = jetid.getText().toString();
                String password = jetpw.getText().toString();
                String name = jetname.getText().toString();
                String phonenumber = jetpn.getText().toString();

                db = dbManger.getReadableDatabase(); //읽기전용으로 설정

                if (db != null) {
                    Cursor cursor = db.rawQuery("select * from LOGIN_JOIN", null);
                    while (cursor.moveToNext()) {
                        CID = cursor.getString(0); //ID값 저장
                        if (id.equals(CID)) { //문자 비교해서 맞으면 Login 창으로
                            Toast.makeText(Join.this, "중복된 ID입니다", Toast.LENGTH_SHORT).show();
                        } else {
                            count = 1;
                        }
                    }
                }


                if (id.length() < 4) {
                    Toast.makeText(Join.this, "ID를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(Join.this, "Password를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (name.length() < 2) {
                    Toast.makeText(Join.this, "이름을 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (phonenumber.length() < 10) {
                    Toast.makeText(Join.this, "전화번호를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    count1 = 1;
                    /*
                    String str = dbManger.PrintData();
                    Toast.makeText(getApplicationContext(), "" + str, Toast.LENGTH_SHORT).show();
                    //테이블안에 값 들어가는 지 확인하는 토스문
                    */

                }

                if (count == 1 && count1 == 1)
                    dbManger.insert("insert into LOGIN_JOIN values('" + id + "', '" + password +
                            "', '" + name + "', '" + phonenumber + "');");

                //회원가입 완료
                Toast.makeText(Join.this, id + "님 가입을 축하드립니다..", Toast.LENGTH_SHORT).show();
                dbManger.close();

                Intent internt = new Intent(Join.this, Main.class);
                internt.putExtra("Join_to_ID", jetid.getText().toString()); //ID값 넘겨주기 (회원가입 하면 바로 ID에 적을 수 있도록)
                startActivity(internt);
            }

        });


    }


    class DBManger extends SQLiteOpenHelper {
        DBManger(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL("CREATE TABLE LOGIN_JOIN(ID TEXT, Password TEXT , Name TEXT, phonenumber TEXT);");
            //테이블 구조 생성
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        }

        public void insert(String _query) { //삽입 //사실상 사용 안함
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(_query);
            db.close();
        }

        public void delete(String _query) { //삭제
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(_query);
            db.close();
        }

        public String PrintData() { //출력
            SQLiteDatabase db = getReadableDatabase();
            String str = "";
            Cursor cursor = db.rawQuery("select * from LOGIN_JOIN", null);
            while (cursor.moveToNext()) {
                str += "ID : " + cursor.getString(0)
                        + "\n Password : " + cursor.getString(1)
                        + "\n Name : " + cursor.getString(2)
                        + "\n Phonenumber : " + cursor.getString(3) + "\n";
            }
            return str;
        }
    }
}
