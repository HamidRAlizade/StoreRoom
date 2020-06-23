package com.interhandelsgmbh.storeroom.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.interhandelsgmbh.storeroom.Class.Content;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.Language;
import com.interhandelsgmbh.storeroom.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import io.fabric.sdk.android.Fabric;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@RuntimePermissions
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @NeedsPermission({android.Manifest.permission.CAMERA, android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void checkPermission() {
        // allow Permission : Code here
        start();
    }

    @OnPermissionDenied({android.Manifest.permission.CAMERA, android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        // don't allow Permission : Code here
        Toast.makeText(this, "Camera and storage permissions are required!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(SplashActivity.this, requestCode, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setViewText();
        Fabric.with(this, new Crashlytics());
        SplashActivityPermissionsDispatcher.checkPermissionWithPermissionCheck(SplashActivity.this);
    }

    public void setViewText(){
        TextView tv_splash = findViewById(R.id.tv_splash);
        String storeRoom = getResources().getString(R.string.storeroom_app);

        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if(setting.contains("LanguageId")){
            String LanguageIdStr = setting.getString("LanguageId","1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(SplashActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            }catch (Exception e){
                languageId = 1;
            }
            //&& storeRoom = 1
            AppText text = dataBaseHandler.getAppText(1,languageId);
            if(text!=null){
                tv_splash.setText(fixEncoding(text.text));
            }else{
                tv_splash.setText(storeRoom);
            }
        }else{
            tv_splash.setText(storeRoom);
        }

    }

    public static String fixEncoding(String latin1) {
        try {
            byte[] bytes = latin1.getBytes("ISO-8859-1");
            if (!validUTF8(bytes))
                return latin1;
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Impossible, throw unchecked
            throw new IllegalStateException("No Latin1 or UTF-8: " + e.getMessage());
        }

    }

    public static boolean validUTF8(byte[] input) {
        int i = 0;
        // Check for BOM
        if (input.length >= 3 && (input[0] & 0xFF) == 0xEF
                && (input[1] & 0xFF) == 0xBB & (input[2] & 0xFF) == 0xBF) {
            i = 3;
        }

        int end;
        for (int j = input.length; i < j; ++i) {
            int octet = input[i];
            if ((octet & 0x80) == 0) {
                continue; // ASCII
            }

            // Check for UTF-8 leading byte
            if ((octet & 0xE0) == 0xC0) {
                end = i + 1;
            } else if ((octet & 0xF0) == 0xE0) {
                end = i + 2;
            } else if ((octet & 0xF8) == 0xF0) {
                end = i + 3;
            } else {
                // Java only supports BMP so 3 is max
                return false;
            }

            while (i < end) {
                i++;
                octet = input[i];
                if ((octet & 0xC0) != 0x80) {
                    // Not a valid trailing byte
                    return false;
                }
            }
        }
        return true;
    }


    public void insertEnglishText(){
        SharedPreferences settings0 = getSharedPreferences("UserInfo", 0);
        if(settings0.contains("RomanianTextInsert")){
            if(!settings0.getString("RomanianTextInsert","").equals("Yes")){
                try {
                    InputStreamReader fileReaderState = new InputStreamReader(getAssets().open("csvstoreroom.csv"));
                    BufferedReader buffer = new BufferedReader(fileReaderState);
                    String line = "";

                    DataBaseHandler dataBaseHandler = new DataBaseHandler(SplashActivity.this);
                    while ((line = buffer.readLine()) != null) {
                        String[] str = line.split(",");
                        String Id = str[0];
                        int c = str.length;
                        StringBuilder s = new StringBuilder();
                        for (int k = 1; k < c; k++) {
                            s.append(str[k]);
                        }
                        String text = s.toString();
                        int id = 0;
                        try{
                            id = Integer.parseInt(Id);
                        }catch (Exception e){
                            id = 0;
                        }
                        AppText appText = new AppText(id,0,1,text);
                        dataBaseHandler.addEnText(appText);
                    }



                    InputStreamReader germanfileReaderState = new InputStreamReader(getAssets().open("german.csv"),"ISO-8859-1");
                    BufferedReader gbuffer = new BufferedReader(germanfileReaderState);
                    String gline = "";

                    while ((gline = gbuffer.readLine()) != null) {
                        String[] str = gline.split(",");
                        String Id = str[0];
                        int c = str.length;
                        StringBuilder s = new StringBuilder();
                        for (int k = 1; k < c; k++) {
                            s.append(str[k]);
                        }
                        String text = s.toString();

                        int id = 0;
                        try{
                            id = Integer.parseInt(Id);
                        }catch (Exception e){
                            id = 0;
                        }
                        AppText appText = new AppText(id+200,id,2,text);
                        dataBaseHandler.addDuText(appText);
                    }

                    InputStreamReader romanianFileReaderState = new InputStreamReader(getAssets().open("romanian.csv"),"ISO-8859-1");
                    BufferedReader rbuffer = new BufferedReader(romanianFileReaderState);
                    String rline = "";

                    while ((rline = rbuffer.readLine()) != null) {
                        String[] str = rline.split(",");
                        String Id = str[0];
                        int c = str.length;
                        StringBuilder s = new StringBuilder();
                        for (int k = 1; k < c; k++) {
                            s.append(str[k]);
                        }
                        String text = s.toString();
                        int id = 0;
                        try{
                            id = Integer.parseInt(Id);
                        }catch (Exception e){
                            id = 0;
                        }
                        AppText appText = new AppText(id+400,id,3,text);
                        dataBaseHandler.addDuText(appText);
                    }


                    Language language3 = new Language(3,"Romanian");
                    dataBaseHandler.addLanguage(language3);

                    Language language2 = new Language(2,"German");
                    dataBaseHandler.addLanguage(language2);

                    Language language1 = new Language(1,"English");
                    dataBaseHandler.addLanguage(language1);


                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("RomanianTextInsert", "Yes");
                    editor.commit();
                    editor.apply();

                    SharedPreferences setting = getSharedPreferences("UserInfo", 0);
                    if (setting.contains("CompanyName") && setting.contains("CompanyAddress") && setting.contains("CompanyLogoPath")) {

                        Intent intent = new Intent(SplashActivity.this, ReportActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    else {
                        //get activity_company_info info
//                    SharedPreferences setting2 = getSharedPreferences("UserInfo", 0);
//                    SharedPreferences.Editor editor = setting2.edit();
//                    editor.putString("LanguageId", "1");
//                    editor.commit();
//                    editor.apply();
                        Intent intent = new Intent(SplashActivity.this, CompanyInfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                }catch (Exception e){ }
            }
            else{
                SharedPreferences setting = getSharedPreferences("UserInfo", 0);
                if (setting.contains("CompanyName") && setting.contains("CompanyAddress") && setting.contains("CompanyLogoPath")) {
                    Content.grtUrl(this);

                    Intent intent = new Intent(SplashActivity.this, ReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                else {
                    //get activity_company_info info
//                    SharedPreferences setting2 = getSharedPreferences("UserInfo", 0);
//                    SharedPreferences.Editor editor = setting2.edit();
//                    editor.putString("LanguageId", "1");
//                    editor.commit();
//                    editor.apply();
                    Intent intent = new Intent(SplashActivity.this, CompanyInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }
        else{
            try {
                InputStreamReader fileReaderState = new InputStreamReader(getAssets().open("csvstoreroom.csv"));
                BufferedReader buffer = new BufferedReader(fileReaderState);
                String line = "";

                DataBaseHandler dataBaseHandler = new DataBaseHandler(SplashActivity.this);
                while ((line = buffer.readLine()) != null) {
                    String[] str = line.split(",");
                    String Id = str[0];
                    int c = str.length;
                    StringBuilder s = new StringBuilder();
                    for (int k = 1; k < c; k++) {
                        s.append(str[k]);
                    }
                    String text = s.toString();
                    int id = 0;
                    try{
                        id = Integer.parseInt(Id);
                    }catch (Exception e){
                        id = 0;
                    }
                    AppText appText = new AppText(id,0,1,text);
                    dataBaseHandler.addEnText(appText);
                }

                InputStreamReader germanfileReaderState = new InputStreamReader(getAssets().open("german.csv"),"ISO-8859-1");
                BufferedReader gbuffer = new BufferedReader(germanfileReaderState);
                String gline = "";

                while ((gline = gbuffer.readLine()) != null) {
                    String[] str = gline.split(",");
                    String Id = str[0];
                    int c = str.length;
                    StringBuilder s = new StringBuilder();
                    for (int k = 1; k < c; k++) {
                        s.append(str[k]);
                    }
                    String text = s.toString();
                    int id = 0;
                    try{
                        id = Integer.parseInt(Id);
                    }catch (Exception e){
                        id = 0;
                    }
                    AppText appText = new AppText(id+200,id,2,text);
                    dataBaseHandler.addDuText(appText);
                }



                InputStreamReader romanianFileReaderState = new InputStreamReader(getAssets().open("romanian.csv"),"ISO-8859-1");
                BufferedReader rbuffer = new BufferedReader(romanianFileReaderState);
                String rline = "";

                while ((rline = rbuffer.readLine()) != null) {
                    String[] str = rline.split(",");
                    String Id = str[0];
                    int c = str.length;
                    StringBuilder s = new StringBuilder();
                    for (int k = 1; k < c; k++) {
                        s.append(str[k]);
                    }
                    String text = s.toString();
                    int id = 0;
                    try{
                        id = Integer.parseInt(Id);
                    }catch (Exception e){
                        id = 0;
                    }
                    AppText appText = new AppText(id+400,id,3,text);
                    dataBaseHandler.addDuText(appText);
                }


                Language language3 = new Language(3,"Romanian");
                dataBaseHandler.addLanguage(language3);

                Language language2 = new Language(2,"German");
                dataBaseHandler.addLanguage(language2);

                Language language1 = new Language(1,"English");
                dataBaseHandler.addLanguage(language1);

                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("RomanianTextInsert", "Yes");
                editor.commit();
                editor.apply();
                SharedPreferences setting = getSharedPreferences("UserInfo", 0);
                if (setting.contains("CompanyName") && setting.contains("CompanyAddress") && setting.contains("CompanyLogoPath")) {

                    Intent intent = new Intent(SplashActivity.this, ReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                else {
                    //get activity_company_info info
//                    SharedPreferences setting2 = getSharedPreferences("UserInfo", 0);
//                    SharedPreferences.Editor editor = setting2.edit();
//                    editor.putString("LanguageId", "1");
//                    editor.commit();
//                    editor.apply();
                    Intent intent = new Intent(SplashActivity.this, CompanyInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }catch (Exception e){ }
        }


    }

    public void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO change the intent second activity to kap_login activity

                DataBaseHandler dataBaseHandler0 = new DataBaseHandler(SplashActivity.this);
                if(dataBaseHandler0.isTableExists()){
                    Log.e("data is exist before:"," yes");
                }else{
                    Log.e("data is exist before:"," no");
                    dataBaseHandler0.resetAllDatabase();
                }

                insertEnglishText();




            }
        }, 3 * 1000);
    }

    @Override
    public void onBackPressed() {

    }
}
