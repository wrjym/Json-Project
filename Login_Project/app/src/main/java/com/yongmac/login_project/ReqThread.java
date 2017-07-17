package com.yongmac.login_project;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yongmac on 2017. 7. 17..
 */

public class ReqThread extends Thread {
    String urlStr;
    String data;

    @Override
    public void run() {
        try{
            urlStr = "http://ec2-52-26-144-160.us-west-2.compute.amazonaws.com:3000/jiminzzang"; //url값
                URL url = new URL(urlStr); //URL값 넣기
                HttpURLConnection con = (HttpURLConnection) url.openConnection(); //http통신을 통해 url 열기
                con.setRequestMethod("GET");

                InputStream in = null;
                ByteArrayOutputStream out = null;
            try{
                    in = con.getInputStream();
                    out = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024*8];
                    int i =0;
                    while ((i=in.read(buffer))!= -1){
                        out.write(buffer,0,i); //내용 받아오기
                    }
                    data = new String(out.toByteArray(),"UTF-8"); //한글로 변환

                }finally { //닫
                    if(in!=null) in.close();
                    if(out!=null) out.close();
                }
        }catch (Exception e) {
            e.printStackTrace();
        }
          Log.v("d",data);

    }

}

