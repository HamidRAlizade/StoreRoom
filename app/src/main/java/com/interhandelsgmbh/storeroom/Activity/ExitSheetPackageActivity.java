package com.interhandelsgmbh.storeroom.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.interhandelsgmbh.storeroom.Adapter.ExitReportListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ExitGoodsBarcodeListAdapter;
import com.interhandelsgmbh.storeroom.Class.CaptureSignatureView;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackageGoods;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExitSheetPackageActivity extends AppCompatActivity {


    EditText etPostCode, etOrderCode;
    ListView lvScan;

    ArrayList<ExitSheetPackage> exitSheetPackages = new ArrayList<>();
    ArrayList<String> postCodes = new ArrayList<>();
    ArrayList<String> goodsNumber0 = new ArrayList<>();
    ArrayList<String> goodsNumber = new ArrayList<>();


    String postBarCode, orderBarCode;
    Boolean canRemove13FromPost = true;
    Boolean canRemove13FromOrder = false;
    int exitSheetId = 0;
    String exitSheetDriverName, exitSheetVehicleNumber, exitSheetDate,exitSheetLogisticsCompany;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }





    String outgoing , delivery_number, order_barcode, items, done, please_sign_it,finish,
            scan_delivery_number,delivery_number_isreaden,error_open_file,
            if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done;

    public void setViewText() {
        TextView tv_outgoing = findViewById(R.id.tv_outgoing);
        EditText etPostCode = findViewById(R.id.etPostCode);
        EditText etOrderCode = findViewById(R.id.etOrderCode);
        TextView tvAddGoods = findViewById(R.id.tvAddGoods);
        TextView tvFinish = findViewById(R.id.tvFinish);
        TextView tv_pleaseSign = findViewById(R.id.tv_pleaseSign);
        TextView tvDoneSignture = findViewById(R.id.tvDoneSignture);


        outgoing = getResources().getString(R.string.outgoing);
        delivery_number = getResources().getString(R.string.delivery_number);
        order_barcode = getResources().getString(R.string.order_barcode);
        items = getResources().getString(R.string.items);
        done = getResources().getString(R.string.done);
        please_sign_it = getResources().getString(R.string.please_sign_it);
        finish = getResources().getString(R.string.finish);
        scan_delivery_number = getResources().getString(R.string.scan_delivery_number);
        delivery_number_isreaden = getResources().getString(R.string.delivery_number_isreaden);
        error_open_file = getResources().getString(R.string.error_open_file);
        if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = getResources().getString(R.string.if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done);


        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(ExitSheetPackageActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& outgoing = 36
            //&& delivery_number = 37
            //&& order_barcode = 38
            //&& items = 32
            //&& finish = 15
            //&& please_sign_it = 16
            //&& done = 17
            //&& scan_delivery_number = 39
            //&& error_open_file = 22
            //&& delivery_number_isreaden = 40
            //&& if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = 35

            AppText outgoingText = dataBaseHandler.getAppText(36, languageId);
            AppText delivery_numberText = dataBaseHandler.getAppText(37, languageId);
            AppText order_barcodeText = dataBaseHandler.getAppText(38, languageId);
            AppText itemsText = dataBaseHandler.getAppText(32, languageId);
            AppText finishText = dataBaseHandler.getAppText(15, languageId);
            AppText please_sign_itText = dataBaseHandler.getAppText(16, languageId);
            AppText doneText = dataBaseHandler.getAppText(17, languageId);
            AppText scan_delivery_numberText = dataBaseHandler.getAppText(39, languageId);
            AppText error_open_fileText = dataBaseHandler.getAppText(22, languageId);
            AppText delivery_number_isreadenText = dataBaseHandler.getAppText(40, languageId);
            AppText if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText = dataBaseHandler.getAppText(35, languageId);


            if (outgoingText != null) {
                outgoing = (outgoingText.text);
            }
            if (delivery_numberText != null) {
                delivery_number = (delivery_numberText.text);
            }
            if (order_barcodeText != null) {
                order_barcode = (order_barcodeText.text);
            }
            if (itemsText != null) {
                items = (itemsText.text);
            }
            if (finishText != null) {
                finish = (finishText.text);
            }
            if (please_sign_itText != null) {
                please_sign_it = (please_sign_itText.text);
            }
            if (doneText != null) {
                done = (doneText.text);
            }
            if (scan_delivery_numberText != null) {
                scan_delivery_number = (scan_delivery_numberText.text);
            }
            if (error_open_fileText != null) {
                error_open_file = (error_open_fileText.text);
            }
            if (delivery_number_isreadenText != null) {
                delivery_number_isreaden = (delivery_number_isreadenText.text);
            }
            if (if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText != null) {
                if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = (if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText.text);
            }



        }

        tv_outgoing.setText(outgoing);
        etPostCode.setHint(delivery_number);
        etOrderCode.setHint(order_barcode);
        tvAddGoods.setText(items);
        tvFinish.setText(finish);
        tv_pleaseSign.setText(please_sign_it);
        tvDoneSignture.setText(done);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing);
        Fabric.with(this, new Crashlytics());


        /**
         * find views by Id
         */
        final EditText etDriveName = findViewById(R.id.etDriverName);
        final EditText etVehicleNumber = findViewById(R.id.etVehicleNumber);
        EditText etDate = findViewById(R.id.etDate);
        etPostCode = findViewById(R.id.etPostCode);
        etOrderCode = findViewById(R.id.etOrderCode);
//        TextView tvNextScan = findViewById(R.id.tvNextScan);
        lvScan = findViewById(R.id.lvScan);
        TextView tvFinish = findViewById(R.id.tvFinish);


        setViewText();

        /**
         * get exit sheet header data from intent
         */
        exitSheetId = Integer.parseInt(getIntent().getStringExtra("exitSheetId"));
        exitSheetDriverName = getIntent().getStringExtra("exitSheetDriverName");
        exitSheetVehicleNumber = getIntent().getStringExtra("exitSheetVehicleNumber");
        exitSheetDate = getIntent().getStringExtra("exitSheetDate");
        exitSheetLogisticsCompany = getIntent().getStringExtra("exitSheetLogisticsCompany");
        final String date = exitSheetDate;

        etDate.setText(exitSheetDate);
        etDate.setEnabled(false);
        etDriveName.setText(exitSheetDriverName);
        etDriveName.setEnabled(false);
        etVehicleNumber.setText(exitSheetVehicleNumber);
        etVehicleNumber.setEnabled(false);
        etPostCode.requestFocus();


        /**
         * get pre exit package from this exitSheetId that saved in database before
         * db: TABLE_ExitSheetPackage
         */
        DataBaseHandler dataBaseHandler = new DataBaseHandler(ExitSheetPackageActivity.this);
        exitSheetPackages = dataBaseHandler.getAllExitSheetPackage(exitSheetId);
        for (int i = 0; i < exitSheetPackages.size(); i++) {
            postCodes.add(exitSheetPackages.get(i).DeliveryNumber);
            ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsList = dataBaseHandler.getAllExitSheetPackageGoods(exitSheetPackages.get(i).Id);
            goodsNumber0.add(exitSheetPackageGoodsList.size() + "");
            ArrayList<Goods> goodsList = new ArrayList<>();
            StringBuilder goodsString = new StringBuilder();
            for (int j = 0; j < exitSheetPackageGoodsList.size(); j++) {
                Goods goods = dataBaseHandler.getGoods(exitSheetPackageGoodsList.get(j).GoodsId);
                goodsList.add(goods);
                goodsString.append(goods.Barocde + " - " + goods.Name + "\n");
            }
            String items = goodsString.toString();
            goodsNumber.add(items);
        }

        ExitReportListAdapter exitReportListAdapter = new ExitReportListAdapter(ExitSheetPackageActivity.this, exitSheetPackages, postCodes, goodsNumber, goodsNumber0);
        lvScan.setAdapter(exitReportListAdapter);


        final int TYPING_TIMEOUT = 500;
        final Handler timeoutHandlerPost = new Handler();
        final Runnable typingTimeoutPost = new Runnable() {
            public void run() {
                serviceCall(etPostCode, etOrderCode, 1);
            }
        };

        final Handler timeoutHandlerOrder = new Handler();
        final Runnable typingTimeoutOrder = new Runnable() {
            public void run() {
                serviceCall(etPostCode, etOrderCode, 0);
            }
        };
        /**
         * check when typing in deliveryNumber_EditText finished
         */
        etPostCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String number = etPostCode.getText().toString();
                timeoutHandlerPost.removeCallbacks(typingTimeoutPost);

                if (number.length() > 3) {
                    Log.e("postBarCode last2", number.substring(number.length() - 2, number.length()) + "");
                } else {
                    canRemove13FromPost = true;
                }

                if (number.length() > 11 && number.substring(number.length() - 2, number.length()).equals("13")) {
                    if (canRemove13FromPost) {
                        Log.e("postBarCode finish", number.substring(number.length() - 2, number.length() - 1) + "");
                        postBarCode = number.substring(0, number.length() - 2);
                        Log.e("postBarCode", postBarCode);
                        timeoutHandlerPost.postDelayed(typingTimeoutPost, TYPING_TIMEOUT);
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        /**
         * check when typing in orderBarcode_EditText finished
         */
        etOrderCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String number = etOrderCode.getText().toString();
                timeoutHandlerOrder.removeCallbacks(typingTimeoutOrder);

                if (number.length() > 3) {
                    Log.e("orderBarcode last2", number.substring(number.length() - 2, number.length()) + "");
                } else {
                    canRemove13FromOrder = true;
                }

                if (number.length() > 6 && number.substring(number.length() - 2, number.length()).equals("13")) {

                    if (canRemove13FromOrder) {
                        Log.e("orderBarCode finish", number.substring(number.length() - 2, number.length() - 1) + "");
                        orderBarCode = number.substring(0, number.length() - 2);
                        Log.e("orderBarCode", orderBarCode);
                        timeoutHandlerOrder.postDelayed(typingTimeoutOrder, TYPING_TIMEOUT);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        TextView tvAddGoods = findViewById(R.id.tvAddGoods);
        tvAddGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postCode = etPostCode.getText().toString();
                String orderCode = etOrderCode.getText().toString();
                if (postCode.isEmpty())
                    Toast.makeText(ExitSheetPackageActivity.this, scan_delivery_number, Toast.LENGTH_LONG).show();
                else {

                    if (postCodes.contains(postCode)) {
                        Toast.makeText(ExitSheetPackageActivity.this, delivery_number_isreaden, Toast.LENGTH_LONG).show();
                        etOrderCode.setText("");
                        etPostCode.setText("");
                        etPostCode.requestFocus();
                    } else {
                        alertDialog_AddGoods(postCode, orderCode);
                    }
                }
            }
        });


//        tvNextScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String postCode = etPostCode.getText().toString();
//                String orderCode = etOrderCode.getText().toString();
//
//                if (postCode.isEmpty()) {
//                    Toast.makeText(ExitSheetPackageActivity.this, scan_delivery_number, Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driverName = etDriveName.getText().toString();
                String vehicleNumber = etVehicleNumber.getText().toString();
                if (driverName.isEmpty())
                    Toast.makeText(ExitSheetPackageActivity.this, "Please fill th driver name", Toast.LENGTH_LONG).show();
                else if (vehicleNumber.isEmpty())
                    Toast.makeText(ExitSheetPackageActivity.this, "Please fill the car plate number", Toast.LENGTH_LONG).show();
                else if (postCodes.size() < 1)
                    Toast.makeText(ExitSheetPackageActivity.this, scan_delivery_number, Toast.LENGTH_LONG).show();
                else {
                    getSignature(driverName, vehicleNumber, date);
                }

            }
        });

    }

    /**
     * get user signature for factor
     * @param driverName
     * @param vehicleNumber
     * @param date
     */
    private void getSignature(final String driverName, final String vehicleNumber, final String date) {

        final LinearLayout signLayout = findViewById(R.id.signLayout);
        final LinearLayout viewLayout = findViewById(R.id.viewLayout);
        TextView tvDoneSignature = findViewById(R.id.tvDoneSignture);
        LinearLayout signatuerLayout = findViewById(R.id.signatuerLayout);
        final CaptureSignatureView mSig = new CaptureSignatureView(this, null);
        signatuerLayout.addView(mSig, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        viewLayout.setVisibility(View.INVISIBLE);
        signLayout.setVisibility(View.VISIBLE);
        tvDoneSignature.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Bitmap signature = mSig.getBitmap();
                createPDF(driverName, vehicleNumber, date, signature);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPDF(String driverName, String vehicleNumber, String date, Bitmap signature) {


        ArrayList<ExitSheetPackage> exitSheetPackages0 = new ArrayList<>();
        ArrayList<Goods> pdfExitSheetPackagesGoods = new ArrayList<>();
        ArrayList<ExitSheetPackage> pdfExitSheetPackages = new ArrayList<>();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(ExitSheetPackageActivity.this);
        exitSheetPackages0 = dataBaseHandler.getAllExitSheetPackage(exitSheetId);
        for (int i = 0; i < exitSheetPackages0.size(); i++) {
            ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsList = dataBaseHandler.getAllExitSheetPackageGoods(exitSheetPackages0.get(i).Id);

            if (exitSheetPackageGoodsList.size() > 0) {
                for (int j = 0; j < exitSheetPackageGoodsList.size(); j++) {
                    Goods goods = dataBaseHandler.getGoods(exitSheetPackageGoodsList.get(j).GoodsId);
                    pdfExitSheetPackages.add(exitSheetPackages0.get(i));
                    pdfExitSheetPackagesGoods.add(goods);
                }
            } else {
                Goods goods = new Goods(0, "", "",0);
                pdfExitSheetPackages.add(exitSheetPackages0.get(i));
                pdfExitSheetPackagesGoods.add(goods);
            }

        }


        File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator + "storeRoomPdf");
        if (!storageDir.exists() || !storageDir.isDirectory()) {
            storageDir.mkdirs();
            if (!storageDir.mkdirs()) {
                Log.e("Error", "failed to create directory");
            }
        }
        try {
            final File file = new File(storageDir.getAbsolutePath() + File.separator + "Outgoing_" + exitSheetDriverName + "_" + date + ".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);


            int pageCount = (int) Math.ceil(pdfExitSheetPackages.size() / 15) + 1;
            if ((((int) (pdfExitSheetPackages.size() / 15)) * 15) == pdfExitSheetPackages.size()) {
                pageCount = (int) Math.ceil(pdfExitSheetPackages.size() / 15);
            }
            PdfDocument document = new PdfDocument();


            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(600, 800, pageCount).create();
            Bitmap bmp = getResizedBitmap(signature, 100, 100);
            SharedPreferences setting = getSharedPreferences("UserInfo", 0);
            String companyName = setting.getString("CompanyName", "");
            String companyAddress = setting.getString("CompanyAddress", "");
            String componyLogoPath = setting.getString("CompanyLogoPath", "");
            File logo = new File(componyLogoPath);
            Bitmap bLogo = null;
            if (logo.exists()) {
                bLogo = BitmapFactory.decodeFile(componyLogoPath);
                bLogo = getResizedBitmap(bLogo, 80, 80);
            }
            for (int i = 0; i < pageCount; i++) {

                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();
                paint.setTextSize(7f);
                Paint paint1 = new Paint();
                paint1.setColor(Color.argb(255, 0, 0, 0));
                paint1.setTextSize(26f);
                paint1.setTypeface(Typeface.DEFAULT_BOLD);


                Paint paint10 = new Paint();
                paint10.setColor(Color.argb(255, 21, 21, 21));
                paint10.setTextSize(18f);
                paint1.setTypeface(Typeface.DEFAULT);


                Paint paint2 = new Paint();
                paint2.setColor(Color.argb(255, 0, 0, 255));
                paint2.setTextSize(14f);
                Paint paint3 = new Paint();
                paint3.setTextSize(16f);
                Paint paint4 = new Paint();
                paint4.setColor(Color.argb(255, 0, 0, 0));


                String strDate = date;

                canvas.drawText(companyName, 20, 30, paint1);
                canvas.drawText(companyAddress, 20, 60, paint10);
                canvas.drawBitmap(bLogo, 500, 10, paint);

                canvas.drawText(strDate, 20, 90, paint2);
                canvas.drawText("LogisticsCompany :"+exitSheetLogisticsCompany, 20, 110, paint2);
                canvas.drawText("Driver Name: " + driverName, 20, 130, paint2);
                canvas.drawText("Vehicle Number: " + vehicleNumber, 20, 155, paint2);


                TextPaint myTextPaint0 = new TextPaint();
                myTextPaint0.setAntiAlias(true);
                myTextPaint0.setTextSize(10f);
                myTextPaint0.setColor(0xFF000000);
                int width0 = 550;
                Layout.Alignment alignment0 = Layout.Alignment.ALIGN_NORMAL;
                float spacingMultiplier0 = 10;
                float spacingAddition0 = 0;
                boolean includePadding0 = false;

                String description = " ";
                if (setting.contains("outgoingDescription")) {
                    if (setting.getString("outgoingDescription", "").length() > 0) {
                        description = "Description: " + setting.getString("outgoingDescription", "");
                    }
                }
                StaticLayout.Builder builder0 = StaticLayout.Builder.obtain(description, 0, description.length(), myTextPaint0, width0)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setLineSpacing(spacingMultiplier0, spacingAddition0)
                        .setIncludePad(includePadding0)
                        .setMaxLines(4);
                StaticLayout myStaticLayout0 = builder0.build();
                canvas.save();
                canvas.translate(20, 165);
                myStaticLayout0.draw(canvas);
                canvas.restore();


                canvas.drawText("Delivery Number", 20, 200, paint3);
                canvas.drawText("Order Barcode", 180, 200, paint3);
                canvas.drawText("Item Barcode", 340, 200, paint3);
                canvas.drawText("Item Name", 500, 200, paint3);
                canvas.drawLine(20, 208, 580, 208, paint4);
//                canvas.drawText("Product Code", 380, 100, paint);


                for (int j = 0; j < 15; j++) {
                    if (((15 * i) + j) < pdfExitSheetPackages.size()) {


                        String text1 = pdfExitSheetPackages.get(((15 * i) + j)).DeliveryNumber;
                        String text2 = pdfExitSheetPackages.get(((15 * i) + j)).InvoiceBarcode;
                        String text3 = pdfExitSheetPackagesGoods.get(((15 * i) + j)).Barocde;
                        String text4 = pdfExitSheetPackagesGoods.get(((15 * i) + j)).Name;

                        //// text1
                        TextPaint myTextPaint = new TextPaint();
                        myTextPaint.setAntiAlias(true);
                        myTextPaint.setTextSize(7f);
                        myTextPaint.setColor(0xFF000000);
                        int width = 140;
                        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
                        float spacingMultiplier = 7;
                        float spacingAddition = 0;
                        boolean includePadding = false;

                        StaticLayout.Builder builder1 = StaticLayout.Builder.obtain(text1, 0, text1.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout1 = builder1.build();
                        canvas.save();
                        canvas.translate(20, (195 + (30 * j)));
                        myStaticLayout1.draw(canvas);
                        canvas.restore();

                        StaticLayout.Builder builder2 = StaticLayout.Builder.obtain(text2, 0, text2.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout2 = builder2.build();
                        canvas.save();
                        canvas.translate(180, (210 + (30 * j)));
                        myStaticLayout2.draw(canvas);
                        canvas.restore();


                        StaticLayout.Builder builder3 = StaticLayout.Builder.obtain(text3, 0, text3.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout3 = builder3.build();
                        canvas.save();
                        canvas.translate(340, (210 + (30 * j)));
                        myStaticLayout3.draw(canvas);
                        canvas.restore();


                        int width2 = 80;
                        StaticLayout.Builder builder4 = StaticLayout.Builder.obtain(text4, 0, text4.length(), myTextPaint, width2)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout4 = builder4.build();
                        canvas.save();
                        canvas.translate(500, (210 + (30 * j)));
                        myStaticLayout4.draw(canvas);
                        canvas.restore();


                        canvas.drawLine(20, (235 + (30 * j)), 580, (235 + (30 * j)), paint4);
                    }
                }

//            canvas.drawText("Product Count: "+productCount, 10, 70, paint);

//            File sign = new File(signPath);
//            if (sign.exists()){
//                Bitmap bmp = BitmapFactory.decodeFile(signPath);
                canvas.drawText("Driver Signature: ", 20, 680, paint2);
                canvas.drawBitmap(bmp, 20, 700, paint);
                canvas.drawLine(20, 690, 120, 690, paint4);
                canvas.drawLine(120, 690, 120, 790, paint4);
                canvas.drawLine(120, 790, 20, 790, paint4);
                canvas.drawLine(20, 790, 20, 690, paint4);

//            }

                document.finishPage(page);

            }

            String pdfPath = file.getAbsolutePath();
            ExitSheet exitSheet = new ExitSheet(exitSheetId, exitSheetDate, exitSheetVehicleNumber, exitSheetDriverName, 1, pdfPath,0,exitSheetLogisticsCompany);
            dataBaseHandler.updateExitSheet(exitSheet);


            document.writeTo(fOut);
            document.close();
            openPDF(file.getAbsolutePath());


        } catch (IOException e) {
            Log.e("error", e.getLocalizedMessage());
        }

    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * open factor pdf automatically after create it
     * @param filePath
     */
    private void openPDF(String filePath) {

        finish();
        File file = new File(filePath);
        Uri attachmentUri = FileProvider.getUriForFile(ExitSheetPackageActivity.this, "com.freshdesk.helpdesk.interstoreroom", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(ExitSheetPackageActivity.this, error_open_file, Toast.LENGTH_LONG).show();
            }
        } else {
            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, "application/pdf");
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(ExitSheetPackageActivity.this, error_open_file, Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * get items barcode for this package (delivery Number,order Barcode)
     * @param deliveryNumber
     * @param orderNumber
     */
    public void alertDialog_AddGoods(final String deliveryNumber, final String orderNumber) {

        final Dialog dialog = new Dialog(ExitSheetPackageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_get_goods_barcode);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_items = dialog.findViewById(R.id.tv_items);
        TextView tv_items_title = dialog.findViewById(R.id.tv_items_title);
        Button bt_done = dialog.findViewById(R.id.bt_done);
        tv_items.setText(items);
        tv_items_title.setText(if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done);
        bt_done.setText(done);


        final ArrayList<Goods> goodsBarCodeList = new ArrayList<>();
        ArrayList<Integer> isEnableList = new ArrayList<>();

        Goods goods = new Goods(0, "", "",0);
        goodsBarCodeList.add(goods);
        isEnableList.add(1);

        ListView lv_GoodsCodeList = dialog.findViewById(R.id.lv_GoodsCodeList);
        final ExitGoodsBarcodeListAdapter goodsBarcodeListAdapter = new ExitGoodsBarcodeListAdapter(ExitSheetPackageActivity.this, lv_GoodsCodeList, goodsBarCodeList, isEnableList);
        lv_GoodsCodeList.setAdapter(goodsBarcodeListAdapter);

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Goods> goods0 = new ArrayList<>();
                ArrayList<Goods> goods = new ArrayList<>();
                goods0 = goodsBarcodeListAdapter.getGoodsBarCodesValue();

                DataBaseHandler dataBaseHandler = new DataBaseHandler(ExitSheetPackageActivity.this);
                ExitSheetPackage exitSheetPackage = new ExitSheetPackage(0, exitSheetId, deliveryNumber, orderNumber,0);
                int exitSheetPackageId = (int) dataBaseHandler.addExitSheetPackage(exitSheetPackage);
                ExitSheetPackage exitSheetPackage2 = new ExitSheetPackage(exitSheetPackageId, exitSheetId, deliveryNumber, orderNumber,0);

                exitSheetPackages.add(exitSheetPackage2);
                postCodes.add(deliveryNumber);

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < goods0.size() - 1; i++) {
                    goods.add(goods0.get(i));
                    stringBuilder.append(goods0.get(i).Barocde + " - " + goods0.get(i).Name + "\n");
                    ExitSheetPackageGoods exitSheetPackageGoods = new ExitSheetPackageGoods(0, exitSheetPackageId, goods0.get(i).Id,0);
                    dataBaseHandler.addExitSheetPackageGoods(exitSheetPackageGoods);

                }

                String goodsList = stringBuilder.toString();
                goodsNumber.add(goodsList + "");
                goodsNumber0.add(goods.size() + "");

                etPostCode.setEnabled(true);
                etOrderCode.setEnabled(true);
                canRemove13FromOrder = true;
                canRemove13FromPost = true;
                etPostCode.setText("");
                etOrderCode.setText("");
                etOrderCode.clearFocus();
                etPostCode.requestFocus();
                ExitReportListAdapter adapter = new ExitReportListAdapter(ExitSheetPackageActivity.this, exitSheetPackages, postCodes, goodsNumber, goodsNumber0);
                lvScan.setAdapter(adapter);
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * action that call after typing finished in deliveryNumber_editText / orderBarcode_editText
     * @param etPost: deliveryNumber_editText
     * @param etOrder: orderBarcode_editText
     * @param isPostBarCode: 1 for action after typing in deliveryNumber_editText & 0 for action after typing in orderBarcode_editText
     */
    public void serviceCall(EditText etPost, EditText etOrder, int isPostBarCode) {

        if (isPostBarCode == 1) {

            if (postCodes.contains(postBarCode)) {
                Toast.makeText(ExitSheetPackageActivity.this, delivery_number_isreaden, Toast.LENGTH_LONG).show();
                etOrderCode.setText("");
                etPostCode.setText("");
                etPostCode.requestFocus();
            } else {
                canRemove13FromPost = false;
                etPost.setText(postBarCode);
                etOrder.requestFocus();
            }

        } else {
            canRemove13FromOrder = false;
            etOrder.setText(orderBarCode);
            etOrder.clearFocus();
            String postCode = etPostCode.getText().toString();
            String orderCode = etOrderCode.getText().toString();
            if (postCode.isEmpty())
                Toast.makeText(ExitSheetPackageActivity.this, scan_delivery_number, Toast.LENGTH_LONG).show();
            else {
                if (postCodes.contains(postCode)) {
                    Toast.makeText(ExitSheetPackageActivity.this, delivery_number_isreaden, Toast.LENGTH_LONG).show();
                } else {
                    alertDialog_AddGoods(postCode, orderCode);
                }
            }
        }

    }


}
