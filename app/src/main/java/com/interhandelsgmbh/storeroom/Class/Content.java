package com.interhandelsgmbh.storeroom.Class;

import android.content.Context;
import android.content.SharedPreferences;

public class Content {
    public  static  String URL="";
    public static String grtUrl(Context con){
        final SharedPreferences settings = con.getSharedPreferences("UserInfo", 0);
        if (settings.contains("postUrl")) {
            if (settings.getString("postUrl", "").length() > 0) {
                URL=settings.getString("postUrl", "");
              return settings.getString("postUrl", "");
            }
        }
      return "";
    }

}
