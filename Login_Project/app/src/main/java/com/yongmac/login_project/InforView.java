package com.yongmac.login_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by yongmac on 2017. 7. 16..
 */

public class InforView extends LinearLayout {
    TextView textView;
    TextView textView2;
    ImageView imageView;

    public InforView(Context context) {
        super(context);

        init(context);
    }

    public InforView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //
        inflater.inflate(R.layout.activity_jsonitem, this, true);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.iv);
    }

    //데이터 설정을 위해 만듬
    public void setRank(String name){
        textView.setText(name);
    }
    public void setNm(String mobile){
        textView2.setText(mobile);
    }
    public void setUrl(String image){
        new DownloadImageTask(imageView).execute(image);
    }



//ListView의 ImageView에 넣을 이미지를 변환시켜주기 위해 만들 class
    //url값이 String 값이기 때문에 Bitmap형태로 변환시켜주는 역할을 한다
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
