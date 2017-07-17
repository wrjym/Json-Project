package com.yongmac.login_project;

import android.widget.ImageView;

/**
 * Created by yongmac on 2017. 7. 16..
 */

public class Infor {

    String rank; //rank
    String Nm; //number
    String url; //url
    ImageView iv;


    public Infor(String name, String mobile, String image) {
        this.rank = name;
        this.Nm = mobile;
        this.url = image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getNm() {
        return Nm;
    }

    public void setNm(String nm) {
        Nm = nm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Infor{" +
                "name='" + rank + '\'' +
                ", mobile='" + Nm + '\'' +
                ", image='" + url + '\'' +
                ", iv=" + iv +
                '}';
    }


}
