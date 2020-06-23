package com.interhandelsgmbh.storeroom.Class;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interhandelsgmbh.storeroom.Activity.CompanyInfoActivity;
import com.interhandelsgmbh.storeroom.Activity.DescriptionActivity;
import com.interhandelsgmbh.storeroom.Activity.ExitSheetPackageActivity;
import com.interhandelsgmbh.storeroom.Activity.ImportActivity;
import com.interhandelsgmbh.storeroom.Activity.LanguageImportActivity;
import com.interhandelsgmbh.storeroom.Activity.ReportActivity;
import com.interhandelsgmbh.storeroom.Activity.SplashActivity;
import com.interhandelsgmbh.storeroom.Adapter.HelpListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.LanguageListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.PreCountingSheetAdapter;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AllData;
import com.interhandelsgmbh.storeroom.Model.AllDataLastIndex;
import com.interhandelsgmbh.storeroom.Model.AllDataString;
import com.interhandelsgmbh.storeroom.Model.AllModel;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.Model.EntranceSheet;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.Model.Language;
import com.interhandelsgmbh.storeroom.Model.ReturnSheet;
import com.interhandelsgmbh.storeroom.Model.SettingData;
import com.interhandelsgmbh.storeroom.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ToolBar {

    Context context;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    public ToolBar(final Context context, final NavigationView navigationView, final DrawerLayout drawerLayout) {

        this.context = context;
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                View headerLayout = navigationView.getHeaderView(0);
                SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
                String coName = settings.getString("CompanyName", "");
                String CompanyLogoPath = settings.getString("CompanyLogoPath", "");


                TextView tv_coName = headerLayout.findViewById(R.id.tv_coName);
                tv_coName.setText(coName + "");

                final ImageView imv_coLogo = headerLayout.findViewById(R.id.imv_coLogo);
                if (CompanyLogoPath != null && CompanyLogoPath.length() > 0) {
                    File logo = new File(CompanyLogoPath);
                    if (logo.exists()) {
                        Log.e("CompanyLogoPath: ", CompanyLogoPath + "");
                        Picasso.with(context).load(logo)

                                .into(imv_coLogo, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {
//                                        imv_coLogo.setImageResource(R.mipmap.ic_launcher);
                                    }
                                });

                    }
                }


            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        ((Activity) context).findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer
                drawerLayout.openDrawer(GravityCompat.END);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bt_help:
                        dialog_help();
                        break;
                    case R.id.bt_info:
                        editInfo();
                        break;
                    case R.id.bt_import:
                        goToImport();
                        break;
                    case R.id.bt_description:
                        goToDescription();
                        break;
                    case R.id.bt_upload:
                        goToUpload();
                        break;
                    case R.id.bt_allDataUpload:
                        goToUploadAllData();
                        break;
                    case R.id.bt_selectLanguage:
                        selectLanguage();
                        break;
                    case R.id.bt_importLanguage:
                        importLanguage();
                        break;
                    case R.id.bt_clearData:
                        deleteData();
                        break;
                    case R.id.bt_setting:
                        setting();
                        break;
                    case R.id.bt_logout:
                        logOut();

                }
                return false;
            }
        });

    }


    public void goToUpload() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_uploaddata);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        TextView tv_uploadDate = dialog.findViewById(R.id.tv_uploadDate);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        final AllData allData = dataBaseHandler.getAllUnsendData();
        final AllDataString allDataString = dataBaseHandler.getAllUnsendDataJson();

        int s1 = allData.GoodsArray.size();
        int s2 = allData.CountingSheetArray.size();
        int s3 = allData.CountingSheetItemArray.size();
        int s4 = allData.ExitSheetArray.size();
        int s5 = allData.ExitSheetPackageArray.size();
        int s6 = allData.ExitSheetPackageGoodsArray.size();
        int s7 = allData.EntranceSheetArray.size();
        int s8 = allData.EntranceSheetPackageArray.size();
        int s9 = allData.EntranceSheetPackageItemArray.size();
        int s10 = allData.ReturnSheetArray.size();
        int s11 = allData.ReturnSheetImageArray.size();
        int s12 = allData.ReturnSheetPackageArray.size();
        int s13 = allData.ReturnSheetPackageItemArray.size();

        int LastIndexGoods = 1;
        int LastIndexCountingSheet = 1;
        int LastIndexCountingSheetItem = 1;
        int LastIndexExitSheet = 1;
        int LastIndexExitSheetPackage = 1;
        int LastIndexExitSheetPackageGoods = 1;
        int LastIndexEntranceSheet = 1;
        int LastIndexEntranceSheetPackage = 1;
        int LastIndexEntranceSheetPackageItem = 1;
        int LastIndexReturnSheet = 1;
        int LastIndexReturnSheetPackage = 1;
        int LastIndexReturnSheetImage = 1;
        int LastIndexReturnSheetPackageItem = 1;
        int LastIndexSettingScanner= 1;
        if (s1 > 0) {
            LastIndexGoods = allData.GoodsArray.get(s1 - 1).Id;
        }
        if (s2 > 0) {
            LastIndexCountingSheet = allData.CountingSheetArray.get(s2 - 1).Id;
        }
        if (s3 > 0) {
            LastIndexCountingSheetItem = allData.CountingSheetItemArray.get(s3 - 1).Id;
        }
        if (s4 > 0) {
            LastIndexExitSheet = allData.ExitSheetArray.get(s4 - 1).Id;
        }
        if (s5 > 0) {
            LastIndexExitSheetPackage = allData.ExitSheetPackageArray.get(s5 - 1).Id;
        }
        if (s6 > 0) {
            LastIndexExitSheetPackageGoods = allData.ExitSheetPackageGoodsArray.get(s6 - 1).Id;
        }
        if (s7 > 0) {
            LastIndexEntranceSheet = allData.EntranceSheetArray.get(s7 - 1).Id;
        }
        if (s8 > 0) {
            LastIndexEntranceSheetPackage = allData.EntranceSheetPackageArray.get(s8 - 1).Id;
        }
        if (s9 > 0) {
            LastIndexEntranceSheetPackageItem = allData.EntranceSheetPackageItemArray.get(s9 - 1).Id;
        }
        if (s10 > 0) {
            LastIndexReturnSheet = allData.ReturnSheetArray.get(s10 - 1).Id;
        }
        if (s11 > 0) {
            LastIndexReturnSheetPackage = allData.ReturnSheetImageArray.get(s11 - 1).Id;
        }
        if (s12 > 0) {
            LastIndexReturnSheetImage = allData.ReturnSheetPackageArray.get(s12 - 1).Id;
        }
        if (s13 > 0) {
            LastIndexReturnSheetPackageItem = allData.ReturnSheetPackageItemArray.get(s13 - 1).Id;
        }
        LastIndexSettingScanner = 1;
        final AllDataLastIndex allDataLastIndex = new AllDataLastIndex(LastIndexGoods, LastIndexCountingSheet,
                LastIndexCountingSheetItem,
                LastIndexExitSheet,
                LastIndexExitSheetPackage,
                LastIndexExitSheetPackageGoods,
                LastIndexEntranceSheet,
                LastIndexEntranceSheetPackage,
                LastIndexEntranceSheetPackageItem,
                LastIndexReturnSheet,
                LastIndexReturnSheetPackage,
                LastIndexReturnSheetImage,
                LastIndexReturnSheetPackageItem,LastIndexSettingScanner);

        AllModel allModel = new AllModel(allData.CountingSheetArray,allData.ExitSheetArray
                , allData.EntranceSheetArray,
                allData.ReturnSheetArray,allData.SettingData
        );
        ArrayList<MultipartBody.Part> files= new ArrayList<>();
        for ( CountingSheet o:allModel.CountingSheetArray){
            files.add(prepareFilePart("cPDF"+o.Id, o.PdfPath));
        }
        for ( EntranceSheet o:allModel.EntranceSheetArray){
            files.add(prepareFilePart("enPDF"+o.Id, o.PdfPath));
        }
        for ( ExitSheet o:allModel.ExitSheetArray){
            files.add(prepareFilePart("exPDF"+o.Id, o.PdfPath));
        }
        for ( ReturnSheet o:allModel.ReturnSheetArray){
            files.add(prepareFilePart("rPDF"+o.Id, o.PdfPath));
        }
        int all = files.size();
        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        TextView tv_white = dialog.findViewById(R.id.tv_white);
        LinearLayout ll_title = dialog.findViewById(R.id.ll_title);
        LinearLayout ll_url = dialog.findViewById(R.id.ll_url);
        final EditText et_url = dialog.findViewById(R.id.et_url);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        Button bt_upload = dialog.findViewById(R.id.bt_upload);


        if (settings.contains("postUrl")) {
            if (settings.getString("postUrl", "").length() > 0) {

                String url = settings.getString("postUrl", "");
                et_url.setText(url);
            }
        }


        if (all == 0) {
            tv_white.setVisibility(View.GONE);
            bt_upload.setVisibility(View.GONE);
            ll_title.setVisibility(View.GONE);
            ll_url.setVisibility(View.GONE);
            bt_cancel.setVisibility(View.VISIBLE);
            bt_cancel.setText("OK");
            tv_uploadDate.setText("All Data ha been send before");
        } else {
            tv_white.setVisibility(View.VISIBLE);
            bt_upload.setVisibility(View.VISIBLE);
            ll_title.setVisibility(View.VISIBLE);
            ll_url.setVisibility(View.VISIBLE);
            bt_cancel.setVisibility(View.VISIBLE);
            bt_cancel.setText("Cancel");

            if (settings.contains("lastUploadDate")) {
                String date = settings.getString("lastUploadDate", "");
                String text = "Last Upload at: " + date + " \n" + "unSend Data size: " + all;
                tv_uploadDate.setText(text);
            } else {
                String text = "First Time Uploading" + " \n" + "unSend Data size: " + all;
                tv_uploadDate.setText(text);
            }
        }

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = et_url.getText().toString();
                if (url.length() > 0) {
//                    String url = "http://admin.inter-gmbh.de/receive_storage_notification.php";
                    sendData(url, allDataString, allData, allDataLastIndex, dialog);
                } else {
                    Toast.makeText(context, "Please Enter Url for receiving data", Toast.LENGTH_LONG).show();
                }

            }
        });


        dialog.setCancelable(true);
        dialog.show();
    }


    public void goToUploadAllData() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_uploaddata);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        TextView tv_uploadDate = dialog.findViewById(R.id.tv_uploadDate);
        TextView tv_Title = dialog.findViewById(R.id.tv_Title);
        tv_Title.setText("Upload All Data");
        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        final AllData allData = dataBaseHandler.getAllData();
        final AllDataString allDataString = dataBaseHandler.getAllDataJson();

        int s1 = allData.GoodsArray.size();
        int s2 = allData.CountingSheetArray.size();
        int s3 = allData.CountingSheetItemArray.size();
        int s4 = allData.ExitSheetArray.size();
        int s5 = allData.ExitSheetPackageArray.size();
        int s6 = allData.ExitSheetPackageGoodsArray.size();
        int s7 = allData.EntranceSheetArray.size();
        int s8 = allData.EntranceSheetPackageArray.size();
        int s9 = allData.EntranceSheetPackageItemArray.size();
        int s10 = allData.ReturnSheetArray.size();
        int s11 = allData.ReturnSheetImageArray.size();
        int s12 = allData.ReturnSheetPackageArray.size();
        int s13 = allData.ReturnSheetPackageItemArray.size();
        int s14 =0;
        int LastIndexGoods = 1;
        int LastIndexCountingSheet = 1;
        int LastIndexCountingSheetItem = 1;
        int LastIndexExitSheet = 1;
        int LastIndexExitSheetPackage = 1;
        int LastIndexExitSheetPackageGoods = 1;
        int LastIndexEntranceSheet = 1;
        int LastIndexEntranceSheetPackage = 1;
        int LastIndexEntranceSheetPackageItem = 1;
        int LastIndexReturnSheet = 1;
        int LastIndexReturnSheetPackage = 1;
        int LastIndexReturnSheetImage = 1;
        int LastIndexReturnSheetPackageItem = 1;
        int LastIndexSettingScanner= 1;

        if (s1 > 0) {
            LastIndexGoods = allData.GoodsArray.get(s1 - 1).Id;
        }
        if (s2 > 0) {
            LastIndexCountingSheet = allData.CountingSheetArray.get(s2 - 1).Id;
        }
        if (s3 > 0) {
            LastIndexCountingSheetItem = allData.CountingSheetItemArray.get(s3 - 1).Id;
        }
        if (s4 > 0) {
            LastIndexExitSheet = allData.ExitSheetArray.get(s4 - 1).Id;
        }
        if (s5 > 0) {
            LastIndexExitSheetPackage = allData.ExitSheetPackageArray.get(s5 - 1).Id;
        }
        if (s6 > 0) {
            LastIndexExitSheetPackageGoods = allData.ExitSheetPackageGoodsArray.get(s6 - 1).Id;
        }
        if (s7 > 0) {
            LastIndexEntranceSheet = allData.EntranceSheetArray.get(s7 - 1).Id;
        }
        if (s8 > 0) {
            LastIndexEntranceSheetPackage = allData.EntranceSheetPackageArray.get(s8 - 1).Id;
        }
        if (s9 > 0) {
            LastIndexEntranceSheetPackageItem = allData.EntranceSheetPackageItemArray.get(s9 - 1).Id;
        }
        if (s10 > 0) {
            LastIndexReturnSheet = allData.ReturnSheetArray.get(s10 - 1).Id;
        }
        if (s11 > 0) {
            LastIndexReturnSheetPackage = allData.ReturnSheetImageArray.get(s11 - 1).Id;
        }
        if (s12 > 0) {
            LastIndexReturnSheetImage = allData.ReturnSheetPackageArray.get(s12 - 1).Id;
        }
        if (s13 > 0) {
            LastIndexReturnSheetPackageItem = allData.ReturnSheetPackageItemArray.get(s13 - 1).Id;
        }

        LastIndexSettingScanner = 1;


        final AllDataLastIndex allDataLastIndex = new AllDataLastIndex(LastIndexGoods, LastIndexCountingSheet,
                LastIndexCountingSheetItem,
                LastIndexExitSheet,
                LastIndexExitSheetPackage,
                LastIndexExitSheetPackageGoods,
                LastIndexEntranceSheet,
                LastIndexEntranceSheetPackage,
                LastIndexEntranceSheetPackageItem,
                LastIndexReturnSheet,
                LastIndexReturnSheetPackage,
                LastIndexReturnSheetImage,
                LastIndexReturnSheetPackageItem,
                LastIndexSettingScanner);
        AllModel allModel = new AllModel(allData.CountingSheetArray,allData.ExitSheetArray
                , allData.EntranceSheetArray,
                allData.ReturnSheetArray,allData.SettingData
        );
        ArrayList<MultipartBody.Part> files= new ArrayList<>();
        for ( CountingSheet o:allModel.CountingSheetArray){
            files.add(prepareFilePart("cPDF"+o.Id, o.PdfPath));
        }
        for ( EntranceSheet o:allModel.EntranceSheetArray){
            files.add(prepareFilePart("enPDF"+o.Id, o.PdfPath));
        }
        for ( ExitSheet o:allModel.ExitSheetArray){
            files.add(prepareFilePart("exPDF"+o.Id, o.PdfPath));
        }
        for ( ReturnSheet o:allModel.ReturnSheetArray){
            files.add(prepareFilePart("rPDF"+o.Id, o.PdfPath));
        }
        int all = files.size();
        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        TextView tv_white = dialog.findViewById(R.id.tv_white);
        LinearLayout ll_title = dialog.findViewById(R.id.ll_title);
        LinearLayout ll_url = dialog.findViewById(R.id.ll_url);
        final EditText et_url = dialog.findViewById(R.id.et_url);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        Button bt_upload = dialog.findViewById(R.id.bt_upload);
        Content.grtUrl(context);

        if (settings.contains("postUrl")) {
            if (settings.getString("postUrl", "").length() > 0) {

                String url = settings.getString("postUrl", "");
                et_url.setText(url);
            }
        }


        if (all == 0) {
            tv_white.setVisibility(View.GONE);
            bt_upload.setVisibility(View.GONE);
            ll_title.setVisibility(View.GONE);
            ll_url.setVisibility(View.GONE);
            bt_cancel.setVisibility(View.VISIBLE);
            bt_cancel.setText("OK");
            tv_uploadDate.setText("No data found");
        } else {
            tv_white.setVisibility(View.VISIBLE);
            bt_upload.setVisibility(View.VISIBLE);
            ll_title.setVisibility(View.VISIBLE);
            ll_url.setVisibility(View.VISIBLE);
            bt_cancel.setVisibility(View.VISIBLE);
            bt_cancel.setText("Cancel");

            if (settings.contains("lastUploadDate")) {
                String date = settings.getString("lastUploadDate", "");
                String text = "Last Upload at: " + date + " \n" + "all Data size: " + all;
                tv_uploadDate.setText(text);
            } else {
                String text = "First Time Uploading" + " \n" + "all Data size: " + all;
                tv_uploadDate.setText(text);
            }
        }

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = et_url.getText().toString();
                if (url.length() > 0) {
//                    String url = "http://admin.inter-gmbh.de/receive_storage_notification.php";

                    sendData(url, allDataString, allData, allDataLastIndex, dialog);
                } else {
                    Toast.makeText(context, "Please Enter Url for receiving data", Toast.LENGTH_LONG).show();
                }

            }
        });


        dialog.setCancelable(true);
        dialog.show();
    }


    public void sendData(final String url, AllDataString allDataString, AllData allData, final AllDataLastIndex allDataLastIndex, final Dialog dialog) {

        SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        if (settings.contains("CompanyName")) {
            String coName = settings.getString("CompanyName", "");
            String coAddress = settings.getString("CompanyAddress", "");

            Map<String, String> param0 = new HashMap<String, String>();

            param0.put("CompanyName", coName + "");
            param0.put("CompanyAddress", coAddress + "");
            Gson gson = new GsonBuilder().create();
            String jsonGoodsArray = gson.toJson(allData.GoodsArray);
            String jsonCountingSheetArray = gson.toJson(allData.CountingSheetArray);
            String jsonCountingSheetItemArray = gson.toJson(allData.CountingSheetItemArray);
            String jsonExitSheetArray = gson.toJson(allData.ExitSheetArray);
            String jsonExitSheetPackageArray = gson.toJson(allData.ExitSheetPackageArray);
            String jsonExitSheetPackageGoodsArray = gson.toJson(allData.ExitSheetPackageGoodsArray);
            String jsonEntranceSheetArray = gson.toJson(allData.EntranceSheetArray);
            String jsonEntranceSheetPackageArray = gson.toJson(allData.EntranceSheetPackageArray);
            String jsonEntranceSheetPackageItemArray = gson.toJson(allData.EntranceSheetPackageItemArray);
            String jsonReturnSheetArray = gson.toJson(allData.ReturnSheetArray);
            String jsonReturnSheetImageArray = gson.toJson(allData.ReturnSheetImageArray);
            String jsonReturnSheetPackageArray = gson.toJson(allData.ReturnSheetPackageArray);
            String jsonReturnSheetPackageItemArray = gson.toJson(allData.ReturnSheetPackageItemArray);

            String jsonSettingScanner= gson.toJson(allData.SettingData);




            param0.put("GoodsArray", jsonGoodsArray + "");
            param0.put("CountingSheetArray", jsonCountingSheetArray + "");
            param0.put("CountingSheetItemArray", jsonCountingSheetItemArray + "");
            param0.put("ExitSheetArray", jsonExitSheetArray + "");
            param0.put("ExitSheetPackageArray", jsonExitSheetPackageArray + "");
            param0.put("ExitSheetPackageGoodsArray", jsonExitSheetPackageGoodsArray + "");
            param0.put("EntranceSheetArray", jsonEntranceSheetArray + "");
            param0.put("EntranceSheetPackageArray", jsonEntranceSheetPackageArray + "");
            param0.put("EntranceSheetPackageItemArray", jsonEntranceSheetPackageItemArray + "");
            param0.put("ReturnSheetArray", jsonReturnSheetArray + "");
            param0.put("ReturnSheetImageArray", jsonReturnSheetImageArray + "");
            param0.put("ReturnSheetPackageArray", jsonReturnSheetPackageArray + "");
            param0.put("ReturnSheetPackageItemArray", jsonReturnSheetPackageItemArray + "");
            param0.put("SettingScanner", jsonSettingScanner + "");

            AllModel allModel = new AllModel(allData.CountingSheetArray,allData.ExitSheetArray
                    , allData.EntranceSheetArray,
                    allData.ReturnSheetArray,allData.SettingData
            );

            ArrayList<MultipartBody.Part> files= new ArrayList<>();
            for ( CountingSheet o:allModel.CountingSheetArray){
                files.add(prepareFilePart("cPDF"+o.Id, o.PdfPath));
            }
            for ( EntranceSheet o:allModel.EntranceSheetArray){
                files.add(prepareFilePart("enPDF"+o.Id, o.PdfPath));
            }
            for ( ExitSheet o:allModel.ExitSheetArray){
                files.add(prepareFilePart("exPDF"+o.Id, o.PdfPath));
            }
            for ( ReturnSheet o:allModel.ReturnSheetArray){
                files.add(prepareFilePart("rPDF"+o.Id, o.PdfPath));
            }


            Gson g = new GsonBuilder().create();
            Object object = allModel;
            String json= g.toJson(object);

            Log.e("HAMID===>",json);
            JSONObject j = null;
            try {
                j = new JSONObject(json);
                Log.e("HAMID===>",j.toString());
                uploadFile(files,json,url,allDataLastIndex,dialog);

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            ToolBar.postData(new postData.OnCompletedCallBack() {
//                @Override
//                public void onCompletedCallBack(boolean isSucceed, JSONObject returnedObject) throws JSONException {
//
//                    if (isSucceed) {
//                        JSONObject resultJson = returnedObject;
//                        if (true) {
//
//                            System.out.println("#########" + returnedObject.toString());
//
//                            Log.e("aa#####" ,"***"+resultJson.toString());
//                            if (resultJson.toString().length() > 0) {
//                                Log.e("Result", resultJson.toString());
//                                Toast.makeText(context, "Upload Successfully done", Toast.LENGTH_LONG).show();
//                                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
//                                dataBaseHandler.updateDataAfterUpload(allDataLastIndex);
//                                final String date = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
//                                final String strDate = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8) +
//                                        " " + date.substring(9, 11) + ":" + date.substring(11, 13);
//                                final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
//                                SharedPreferences.Editor editor = settings.edit();
//                                editor.putString("lastUploadDate", strDate);
//                                editor.putString("postUrl", url);
//                                editor.apply();
//                                editor.commit();
//                                Content.grtUrl(context);
//
//                                dialog.dismiss();
//                                dialog.cancel();
//
//                            } else {
//                                Toast.makeText(context, "Error! ", Toast.LENGTH_LONG).show();
//                            }
//
//                        } else {
//                            dialog.dismiss();
//                            Toast.makeText(context, "Error! ", Toast.LENGTH_LONG).show();
//                        }
//                    }else{
//                        Toast.makeText(context, "error while posting data.. Please check your url", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }, j, url, context);


        }

    }


    public static void postData(final postData.OnCompletedCallBack callback, JSONObject jsonObject, String url, Context context) {

        // Call Web Service
        try {
            postData a = new postData(url, jsonObject,context);
            a.addOnCompletedCallBack(new postData.OnCompletedCallBack() {
                @Override
                public void onCompletedCallBack(boolean isSucceed, JSONObject returnedObject) throws JSONException, ParseException {
                    if (callback != null)
                        callback.onCompletedCallBack(isSucceed, returnedObject);
                }
            });
            a.execute();

        } catch (Exception e) {
            Log.e("WebService", "RegisterUser: Web Service (try catch) Exception.");
            Toast.makeText(context, "error while posting data.. Please check your url", Toast.LENGTH_LONG).show();
            if (e != null)
                if (e.getMessage() != null)
                    Log.e("WebService", e.getMessage());

        }
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File( fileUri);

        // create RequestBody instance from file

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        fileUri
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
    private void uploadFile(ArrayList<MultipartBody.Part> fileUri, String json, final String addresss, final AllDataLastIndex allDataLastIndex, final Dialog dialog) {
        // create upload service client
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);

        Log.e("TRUE=======>",fileUri.size()+"");


        // create list of file parts (photo, video, ...)


        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // create RequestBody instance from file


        // MultipartBody.Part is used to send also the actual file name


        // add another part within the multipart request
        String descriptionString = json;
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("application/octet-stream"), descriptionString);

        // finally, execute the request
        service.upload(description, fileUri,addresss)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                          JSONObject j = new JSONObject(responseBody.string());
                            Log.e("TRUE=======>",j.toString());

//



                            Log.e("TRUE=======>",responseBody.string().toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("FALSE",e.getMessage());


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("FALSE",e.getMessage());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("FALSE",e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Upload Successfully done", Toast.LENGTH_LONG).show();
                        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                        dataBaseHandler.updateDataAfterUpload(allDataLastIndex);
                        final String date = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
                        final String strDate = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8) +
                                " " + date.substring(9, 11) + ":" + date.substring(11, 13);
                        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("lastUploadDate", strDate);
                        editor.putString("postUrl", addresss);
                        editor.apply();
                        editor.commit();
                        Content.grtUrl(context);

                        dialog.dismiss();
                        dialog.cancel();


                    }
                });

    }


    public void deleteData() {
        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        final Dialog logOut = new Dialog(context);
        logOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logOut.setContentView(R.layout.dialog_delete_data);
        logOut.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        Button bt_noexit = logOut.findViewById(R.id.bt_noexit);
        Button bt_okexit = logOut.findViewById(R.id.bt_okexit);

        bt_noexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.dismiss();
            }
        });

        bt_okexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                logOut.dismiss();
                logOut.cancel();
                Content.grtUrl(context);

                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                dataBaseHandler.resetAllDatabase();
                Intent intent = new Intent(context, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });

        logOut.setCancelable(true);
        logOut.show();
    }

    public void selectLanguage() {
        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_language);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));
        ListView lv_language = dialog.findViewById(R.id.lv_language);
        ArrayList<Language> languages = new ArrayList<>();
        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        languages = dataBaseHandler.getAllLanguage();

        LanguageListAdapter languageListAdapter = new LanguageListAdapter(context, languages, dialog);
        lv_language.setAdapter(languageListAdapter);

        dialog.setCancelable(true);
        dialog.show();
    }

    public void setting() {
        final Dialog Store = new Dialog(context);
        Store.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Store.setContentView(R.layout.dialog_setting);
        Store.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        Button bt_noexit = Store.findViewById(R.id.bt_setting_no);
        Button bt_okexit = Store.findViewById(R.id.bt_setting_yes);

        bt_okexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_nameScanner = (EditText) Store.findViewById(R.id.et_nameScanner);
                EditText et_namestore = Store.findViewById(R.id.et_nameStore);
                String scanner=et_nameScanner.getText().toString();
                String store=et_namestore.getText().toString();;
                SettingData settingData = new SettingData(1,scanner,store,0);
                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                dataBaseHandler.settingScanner(settingData);
                Log.e("scanner",settingData.NameStore);
                Store.dismiss();
            }
        });

        bt_noexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Store.dismiss();

            }
        });

        Store.setCancelable(true);
        Store.show();
    }
    public void logOut() {
        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        final Dialog logOut = new Dialog(context);
        logOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logOut.setContentView(R.layout.dialog_logout);
        logOut.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        Button bt_noexit = logOut.findViewById(R.id.bt_noexit);
        Button bt_okexit = logOut.findViewById(R.id.bt_okexit);

        bt_noexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.dismiss();
            }
        });

        bt_okexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                Content.grtUrl(context);

//                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
//                dataBaseHandler.deleteDatabase(context);
                logOut.cancel();
                logOut.dismiss();
                Intent intent = new Intent(context, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });

        logOut.setCancelable(true);
        logOut.show();
    }


    public void importLanguage() {
        Intent intent = new Intent(context, LanguageImportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void goToDescription() {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void editInfo() {
        Intent intent = new Intent(context, CompanyInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void goToImport() {
        Intent intent = new Intent(context, ImportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    public static class HelpItem {
        public int Id;
        public String helpText;

        public HelpItem(int _id, String _helpText) {
            Id = _id;
            helpText = _helpText;
        }
    }

    public void dialog_help() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_help);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));


        ListView lv_helpItems = dialog.findViewById(R.id.lv_helpItems);

        ArrayList<HelpItem> helpItems = new ArrayList<>();
        HelpItem helpItem1 = new HelpItem(1, "Company Information");
        HelpItem helpItem2 = new HelpItem(2, "Import Items");
        HelpItem helpItem3 = new HelpItem(3, "Action Page");
        HelpItem helpItem4 = new HelpItem(4, "Entrance Process");
        HelpItem helpItem5 = new HelpItem(5, "Outgoing Item");
        HelpItem helpItem6 = new HelpItem(6, "Item Counting");
        HelpItem helpItem7 = new HelpItem(7, "Return");
        HelpItem helpItem8 = new HelpItem(8, "Application menu");
        helpItems.add(helpItem1);
        helpItems.add(helpItem2);
        helpItems.add(helpItem3);
        helpItems.add(helpItem4);
        helpItems.add(helpItem5);
        helpItems.add(helpItem6);
        helpItems.add(helpItem7);
        helpItems.add(helpItem8);
        HelpListAdapter helpListAdapter = new HelpListAdapter(context, helpItems);
        lv_helpItems.setAdapter(helpListAdapter);


        Button bt_deny = dialog.findViewById(R.id.bt_deny);
        bt_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }


}
