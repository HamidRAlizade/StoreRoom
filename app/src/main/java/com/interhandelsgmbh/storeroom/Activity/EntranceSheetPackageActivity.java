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

import com.interhandelsgmbh.storeroom.Adapter.EntranceReportListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ExitGoodsBarcodeListAdapter;
import com.interhandelsgmbh.storeroom.Class.CaptureSignatureView;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.EntranceSheet;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackage;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackageItem;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EntranceSheetPackageActivity extends AppCompatActivity {

    int entranceSheetId;
    String entranceSheetUserName, entranceSheetDescription, entranceSheetDate,entranceSheetDateLogisticsCompany;
    EditText et_PackageCode;
    TextView tvAddGoods, tv_Finish;
    ListView lv_EntrancePackage;

    ArrayList<EntranceSheetPackage> entranceSheetPackageList = new ArrayList<>();
    ArrayList<String> itemBarcodes = new ArrayList<>();
    String packageCode;
    Boolean canRemove13 = true;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    String entrance , user_name, package_number, items, finish, please_sign_it,
            done,scan_atleast_one_pack,write_comment,error_open_file,
            if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done;

    public void setViewText() {
        TextView tv_entrance = findViewById(R.id.tv_entrance);
        EditText et_UserName = findViewById(R.id.et_UserName);
        EditText et_PackageCode = findViewById(R.id.et_PackageCode);
        TextView tvAddGoods = findViewById(R.id.tvAddGoods);
        TextView tv_Finish = findViewById(R.id.tv_Finish);
        TextView tv_pleaseSign = findViewById(R.id.tv_pleaseSign);
        TextView tvDoneSignture = findViewById(R.id.tvDoneSignture);


        entrance = getResources().getString(R.string.entrance);
        user_name = getResources().getString(R.string.user_name);
        package_number = getResources().getString(R.string.package_number);
        items = getResources().getString(R.string.items);
        finish = getResources().getString(R.string.finish);
        please_sign_it = getResources().getString(R.string.please_sign_it);
        done = getResources().getString(R.string.done);
        scan_atleast_one_pack = getResources().getString(R.string.scan_atleast_one_pack);
        write_comment = getResources().getString(R.string.write_comment);
        if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = getResources().getString(R.string.if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done);


        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(EntranceSheetPackageActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& entrance = 29
            //&& user_name = 30
            //&& package_number = 31
            //&& items = 32
            //&& finish = 15
            //&& please_sign_it = 16
            //&& done = 17
            //&& scan_atleast_one_pack = 33
            //&& write_comment = 34
            //&& error_open_file = 22
            //&& if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = 35

            AppText entranceText = dataBaseHandler.getAppText(29, languageId);
            AppText user_nameText = dataBaseHandler.getAppText(30, languageId);
            AppText package_numberText = dataBaseHandler.getAppText(31, languageId);
            AppText itemsText = dataBaseHandler.getAppText(32, languageId);
            AppText finishText = dataBaseHandler.getAppText(15, languageId);
            AppText please_sign_itText = dataBaseHandler.getAppText(16, languageId);
            AppText doneText = dataBaseHandler.getAppText(17, languageId);
            AppText scan_atleast_one_packText = dataBaseHandler.getAppText(33, languageId);
            AppText write_commentText = dataBaseHandler.getAppText(34, languageId);
            AppText error_open_fileText = dataBaseHandler.getAppText(22, languageId);
            AppText if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText = dataBaseHandler.getAppText(35, languageId);


            if (entranceText != null) {
                entrance = (entranceText.text);
            }
            if (user_nameText != null) {
                user_name = (user_nameText.text);
            }
            if (package_numberText != null) {
                package_number = (package_numberText.text);
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
            if (scan_atleast_one_packText != null) {
                scan_atleast_one_pack = (scan_atleast_one_packText.text);
            }
            if (write_commentText != null) {
                write_comment = (write_commentText.text);
            }
            if (error_open_fileText != null) {
                error_open_file = (error_open_fileText.text);
            }
            if (if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText != null) {
                if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = (if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText.text);
            }


        }

        tv_entrance.setText(entrance);
        et_UserName.setHint(user_name);
        et_PackageCode.setHint(package_number);
        tvAddGoods.setText(items);
        tv_Finish.setText(finish);
        tv_pleaseSign.setText(please_sign_it);
        tvDoneSignture.setText(done);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        /**
         * Get sheet info from intent
         */
        entranceSheetId = Integer.parseInt(getIntent().getStringExtra("entranceSheetId"));
        entranceSheetUserName = getIntent().getStringExtra("entranceSheetUserName");
        entranceSheetDescription = getIntent().getStringExtra("entranceSheetDescription");
        entranceSheetDate = getIntent().getStringExtra("entranceSheetDate");
        entranceSheetDateLogisticsCompany = getIntent().getStringExtra("entranceSheetLogisticsCompany");

        /**
         * find view by Id
         */
        setViewText();

        EditText et_UserName = findViewById(R.id.et_UserName);
        EditText etDate = findViewById(R.id.etDate);
        et_UserName.setText(entranceSheetUserName);
        et_UserName.setEnabled(false);
        etDate.setText(entranceSheetDate);
        etDate.setEnabled(false);
        et_PackageCode = findViewById(R.id.et_PackageCode);
        tvAddGoods = findViewById(R.id.tvAddGoods);
        tv_Finish = findViewById(R.id.tv_Finish);
        lv_EntrancePackage = findViewById(R.id.lv_EntrancePackage);
        et_PackageCode.requestFocus();




        /**
         * get pre entrance sheet package of this sheet from database
         * db: TABLE_EntranceSheetPackage
         */
        DataBaseHandler dataBaseHandler = new DataBaseHandler(EntranceSheetPackageActivity.this);
        entranceSheetPackageList = dataBaseHandler.getAllEntranceSheetPackage(entranceSheetId);
        for (int i = 0; i < entranceSheetPackageList.size(); i++) {

            ArrayList<EntranceSheetPackageItem> entranceSheetPackageItems = dataBaseHandler.getAllEntranceSheetPackageItems(entranceSheetPackageList.get(i).Id);
            Log.e("entrance Items", entranceSheetPackageItems + "");
            ArrayList<Goods> goodsList = new ArrayList<>();
            StringBuilder goodsString = new StringBuilder();
            for (int j = 0; j < entranceSheetPackageItems.size(); j++) {
                Goods goods = dataBaseHandler.getGoods(entranceSheetPackageItems.get(j).GoodsId);
                goodsList.add(goods);
                goodsString.append(goods.Barocde+" - "+goods.Name + "\n");
            }
            String items = goodsString.toString();
            itemBarcodes.add(items);
        }

        EntranceReportListAdapter entranceReportListAdapter = new EntranceReportListAdapter(EntranceSheetPackageActivity.this, entranceSheetPackageList, itemBarcodes);
        lv_EntrancePackage.setAdapter(entranceReportListAdapter);


        final int TYPING_TIMEOUT = 500;
        final Handler timeoutHandlerPost = new Handler();
        final Runnable typingTimeoutPost = new Runnable() {
            public void run() {
                serviceCall();
            }
        };


        /**
         * check when typing in packageCode_editText finished
         */
        et_PackageCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String number = et_PackageCode.getText().toString();
                timeoutHandlerPost.removeCallbacks(typingTimeoutPost);

                if (number.length() > 3) {
                    Log.e("packageCode last2", number.substring(number.length() - 2, number.length()) + "");
                } else {
                    canRemove13 = true;
                }

                if (number.length() > 9 && number.substring(number.length() - 2, number.length()).equals("13")) {
                    if (canRemove13) {
                        Log.e("packageCode finish", number.substring(number.length() - 2, number.length() - 1) + "");
                        packageCode = number.substring(0, number.length() - 2);
                        Log.e("packageCode", packageCode);
                        timeoutHandlerPost.postDelayed(typingTimeoutPost, TYPING_TIMEOUT);
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
                String packageCode = et_PackageCode.getText().toString();
                if(packageCode.length()>9){
                    alertDialog_AddGoods(packageCode);
                }
            }
        });

        tv_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entranceSheetPackageList.size() < 1)
                    Toast.makeText(EntranceSheetPackageActivity.this, scan_atleast_one_pack, Toast.LENGTH_LONG).show();
                else {
                    alertDialog_AddDescription();
                }
            }
        });


    }


    /**
     * get comment for this entrance sheet
     */
    public void alertDialog_AddDescription() {

        final Dialog dialog = new Dialog(EntranceSheetPackageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_get_description_entrance_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        final EditText et_Description = dialog.findViewById(R.id.et_Description);

        Button bt_add = dialog.findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String description = et_Description.getText().toString();
                if (description.length() > 0) {
                    entranceSheetDescription = description;
                    getSignature(entranceSheetUserName, entranceSheetDescription, entranceSheetDate,entranceSheetDateLogisticsCompany);
                    dialog.dismiss();
                    dialog.cancel();
                } else {
                    Toast.makeText(EntranceSheetPackageActivity.this, write_comment, Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * get user signature for factor
     * @param UserName: name of user who get this entrance package
     * @param Description: comment of this entrance sheet
     * @param date: the date that this sheet created
     */
    private void getSignature(final String UserName, final String Description, final String date,final String LogisticsCompany) {

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
                createPDF(UserName, Description, date, signature,LogisticsCompany);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPDF(String UserName, String Description, String date, Bitmap signature,String LogisticsCompany) {


        ArrayList<EntranceSheetPackage> entranceSheetPackages0 = new ArrayList<>();
        ArrayList<Goods> pdfEntranceSheetPackagesGoods = new ArrayList<>();
        ArrayList<EntranceSheetPackage> pdfEntranceSheetPackages = new ArrayList<>();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(EntranceSheetPackageActivity.this);
        entranceSheetPackages0 = dataBaseHandler.getAllEntranceSheetPackage(entranceSheetId);
        for (int i = 0; i < entranceSheetPackages0.size(); i++) {
            ArrayList<EntranceSheetPackageItem> entranceSheetPackageItemList = dataBaseHandler.getAllEntranceSheetPackageItems(entranceSheetPackages0.get(i).Id);

            if (entranceSheetPackageItemList.size() > 0) {
                for (int j = 0; j < entranceSheetPackageItemList.size(); j++) {
                    Goods goods = dataBaseHandler.getGoods(entranceSheetPackageItemList.get(j).GoodsId);
                    pdfEntranceSheetPackages.add(entranceSheetPackages0.get(i));
                    pdfEntranceSheetPackagesGoods.add(goods);
                }
            } else {
                Goods goods = new Goods(0, "", "",0);
                pdfEntranceSheetPackages.add(entranceSheetPackages0.get(i));
                pdfEntranceSheetPackagesGoods.add(goods);
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
            final File file = new File(storageDir.getAbsolutePath() + File.separator + "Entrance_" + entranceSheetUserName + "_" + date + ".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);



            int pageCount = (int) Math.ceil(pdfEntranceSheetPackages.size() / 15) + 1;
            if ((((int) (pdfEntranceSheetPackages.size() / 15)) * 15) == pdfEntranceSheetPackages.size()) {
                pageCount = (int) Math.ceil(pdfEntranceSheetPackages.size() / 15);
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


                String strDate = "Date: "+date;

                canvas.drawText(companyName, 20, 30, paint1);
                canvas.drawText(companyAddress, 20, 60, paint10);
                canvas.drawBitmap(bLogo, 500, 10, paint);

                canvas.drawText(strDate, 20, 90, paint2);
                canvas.drawText("LogisticsCompany :"+entranceSheetDateLogisticsCompany, 20, 115, paint2);
                canvas.drawText("Name: " + UserName, 20, 130, paint2);

                //// comment
                String comment ="Comment: "+ Description;
                TextPaint myTextPaint0 = new TextPaint();
                myTextPaint0.setAntiAlias(true);
                myTextPaint0.setTextSize(8f);
                myTextPaint0.setColor(Color.argb(255, 0, 0, 255));
                int width0 = 500;
                float spacingMultiplier0 = 10;
                float spacingAddition0 = 0;
                boolean includePadding0 = false;
                StaticLayout.Builder builder0 = StaticLayout.Builder.obtain(comment, 0, comment.length(), myTextPaint0, width0)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setLineSpacing(spacingMultiplier0, spacingAddition0)
                        .setIncludePad(includePadding0)
                        .setMaxLines(5);
                StaticLayout myStaticLayout0 = builder0.build();
                canvas.save();
                canvas.translate(20, 145);
                myStaticLayout0.draw(canvas);
                canvas.restore();





                TextPaint myTextPaint00 = new TextPaint();
                myTextPaint00.setAntiAlias(true);
                myTextPaint00.setTextSize(10f);
                myTextPaint00.setColor(0xFF000000);
                int width00 = 550;
                Layout.Alignment alignment00 = Layout.Alignment.ALIGN_NORMAL;
                float spacingMultiplier00 = 10;
                float spacingAddition00 = 0;
                boolean includePadding00 = false;

                String description=" ";
                if(setting.contains("entranceDescription")) {
                    if (setting.getString("entranceDescription", "").length() > 0) {
                        description = "Description: " + setting.getString("entranceDescription", "");
                    }
                }
                StaticLayout.Builder builder00 = StaticLayout.Builder.obtain(description, 0, description.length(), myTextPaint00, width00)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setLineSpacing(spacingMultiplier00, spacingAddition00)
                        .setIncludePad(includePadding00)
                        .setMaxLines(4);
                StaticLayout myStaticLayout00 = builder00.build();
                canvas.save();
                canvas.translate(20, 175);
                myStaticLayout00.draw(canvas);
                canvas.restore();




                canvas.drawText("Package Code", 20, 230, paint3);
                canvas.drawText("Item Barcode", 240, 230, paint3);
                canvas.drawText("Item Name", 420, 230, paint3);
                canvas.drawLine(20, 240, 580, 240, paint4);
//                canvas.drawText("Product Code", 380, 100, paint);


                for (int j = 0; j < 15; j++) {
                    if (((15 * i) + j) < pdfEntranceSheetPackages.size()) {


                        String text1 = pdfEntranceSheetPackages.get((15 * i) + j).PackageNumber;
                        String text2 = pdfEntranceSheetPackagesGoods.get((15 * i) + j).Barocde;
                        String text3 = pdfEntranceSheetPackagesGoods.get((15 * i) + j).Name;

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
                        canvas.translate(20, (245 + (30 * j)));
                        myStaticLayout1.draw(canvas);
                        canvas.restore();

                        StaticLayout.Builder builder2 = StaticLayout.Builder.obtain(text2, 0, text2.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout2 = builder2.build();
                        canvas.save();
                        canvas.translate(240, (245 + (30 * j)));
                        myStaticLayout2.draw(canvas);
                        canvas.restore();


                        StaticLayout.Builder builder3 = StaticLayout.Builder.obtain(text3, 0, text3.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout3 = builder3.build();
                        canvas.save();
                        canvas.translate(420, (245 + (30 * j)));
                        myStaticLayout3.draw(canvas);
                        canvas.restore();

                        canvas.drawLine(20, (260 + (30 * j)), 580, (260 + (30 * j)), paint4);
                    }
                }

//            canvas.drawText("Product Count: "+productCount, 10, 70, paint);

//            File sign = new File(signPath);
//            if (sign.exists()){
//                Bitmap bmp = BitmapFactory.decodeFile(signPath);
                canvas.drawText("User Signature: ", 20, 680, paint2);
                canvas.drawBitmap(bmp, 20, 700, paint);
                canvas.drawLine(20, 690, 120, 690, paint4);
                canvas.drawLine(120, 690, 120, 790, paint4);
                canvas.drawLine(120, 790, 20, 790, paint4);
                canvas.drawLine(20, 790, 20, 690, paint4);

//            }

                document.finishPage(page);

            }

            String pdfPath = file.getAbsolutePath();
            EntranceSheet entranceSheet = new EntranceSheet(entranceSheetId, entranceSheetDate, entranceSheetUserName, entranceSheetDescription, 1, pdfPath,0,LogisticsCompany);
            dataBaseHandler.updateEntranceSheet(entranceSheet);


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

    private void openPDF(String filePath) {

        finish();
        File file = new File(filePath);
        Uri attachmentUri = FileProvider.getUriForFile(EntranceSheetPackageActivity.this, "com.freshdesk.helpdesk.interstoreroom", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(EntranceSheetPackageActivity.this,error_open_file , Toast.LENGTH_LONG).show();
            }
        } else {
            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, "application/pdf");
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(EntranceSheetPackageActivity.this,error_open_file , Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * get items barcode for this packageId and save them into database
     * @param packageCode
     */
    public void alertDialog_AddGoods(final String packageCode) {

        canRemove13=true;
        final Dialog dialog = new Dialog(EntranceSheetPackageActivity.this);
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
        final ExitGoodsBarcodeListAdapter goodsBarcodeListAdapter = new ExitGoodsBarcodeListAdapter(EntranceSheetPackageActivity.this, lv_GoodsCodeList, goodsBarCodeList, isEnableList);
        lv_GoodsCodeList.setAdapter(goodsBarcodeListAdapter);

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Goods> goods0 = new ArrayList<>();
                ArrayList<Goods> goods = new ArrayList<>();
                goods0 = goodsBarcodeListAdapter.getGoodsBarCodesValue();

                DataBaseHandler dataBaseHandler = new DataBaseHandler(EntranceSheetPackageActivity.this);
                EntranceSheetPackage entranceSheetPackage = new EntranceSheetPackage(0, entranceSheetId, packageCode,0);
                int entranceSheetPackageId = (int) dataBaseHandler.addEntranceSheetPackage(entranceSheetPackage);
                EntranceSheetPackage entranceSheetPackage2 = new EntranceSheetPackage(entranceSheetPackageId, entranceSheetId, packageCode,0);


                entranceSheetPackageList.add(entranceSheetPackage2);

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < goods0.size() - 1; i++) {
                    goods.add(goods0.get(i));
                    stringBuilder.append(goods0.get(i).Barocde+" - "+goods0.get(i).Name + "\n");
                    EntranceSheetPackageItem entranceSheetPackageItem = new EntranceSheetPackageItem(0, entranceSheetPackageId, goods0.get(i).Id,0);
                    dataBaseHandler.addEntranceSheetPackageItem(entranceSheetPackageItem);

                }

                String goodsList = stringBuilder.toString();
                itemBarcodes.add(goodsList + "");

                et_PackageCode.setEnabled(true);
                canRemove13 = true;
                et_PackageCode.setText("");
                et_PackageCode.requestFocus();
                EntranceReportListAdapter entranceReportListAdapter = new EntranceReportListAdapter(EntranceSheetPackageActivity.this, entranceSheetPackageList, itemBarcodes);
                lv_EntrancePackage.setAdapter(entranceReportListAdapter);
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * action that call after typing finished in packageCode_editText
     */
    public void serviceCall() {

        canRemove13 = false;
        et_PackageCode.setText(packageCode);
        String packageCode = et_PackageCode.getText().toString();
        alertDialog_AddGoods(packageCode);

    }


}
