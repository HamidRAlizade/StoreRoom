package com.interhandelsgmbh.storeroom.Class;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interhandelsgmbh.storeroom.Activity.ReportActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AllData;
import com.interhandelsgmbh.storeroom.Model.AllDataLastIndex;
import com.interhandelsgmbh.storeroom.Model.AllDataString;
import com.interhandelsgmbh.storeroom.Model.AllModel;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.Model.EntranceSheet;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.Model.ReturnSheet;
import com.interhandelsgmbh.storeroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by parsa on 4/25/17.
 */
public class UpdateReceiver extends BroadcastReceiver {

Context context;
        @Override
        public void onReceive( Context context, Intent intent )
        {
            this.context=context;
            Log.e("change : " ,"%%%%% change" );

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(     ConnectivityManager.TYPE_MOBILE );
            if ( activeNetInfo != null )
            {
                Log.e("Active Network Type : " ,"%%%%%"+ activeNetInfo.getTypeName() );
            }
            if( mobNetInfo != null )
            {
                Log.e( "Mobile Network Type : " ,"%%%%%"+ mobNetInfo.getTypeName());
            }

            boolean isOnline = IsOnline(context);
            Log.e("isOnline",isOnline+"&&&&****");
            if(isOnline){
                goToUpload(context);
            }


        }




    public boolean IsOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public void goToUpload(final Context context) {

        final SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        if(settings.contains("postUrl")) {
            if (settings.getString("postUrl", "").length() > 0) {

                String url = Content.URL;

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
                int LastIndexScannerSeting = 1;
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
                        LastIndexScannerSeting);
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
                if (all > 0) {
                    if (url.length() > 0) {
//                    String url = "http://admin.inter-gmbh.de/receive_storage_notification.php";
                        sendData(context, url, allDataString, allData, allDataLastIndex);
                    }
                }
            }
        }


    }


    public void sendData(final Context context, String url, AllDataString allDataString,
                         AllData allData, final AllDataLastIndex allDataLastIndex) {

        SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        if (settings.contains("CompanyName")) {

            Log.e("sendData","&&&&& send 1");

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
            String jsonSettingScanner = gson.toJson(allData.ReturnSheetPackageItemArray);


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
            param0.put("jsonSettingScanner", jsonSettingScanner + "");

            Log.e("params0000", param0 + "");
            JSONObject jj = new JSONObject(param0);
            Log.e("paramsjj", jj + "");


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
                uploadFile(files,json,url,allDataLastIndex);
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
    private void uploadFile(ArrayList<MultipartBody.Part> fileUri, String json, final String addresss, final AllDataLastIndex allDataLastIndex) {
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



                    }
                });

    }


}