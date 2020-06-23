package com.interhandelsgmbh.storeroom.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Adapter.PreCountingSheetItemAdapter;
import com.interhandelsgmbh.storeroom.Class.CaptureSignatureView;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.Model.CountingSheetItem;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import org.xml.sax.DTDHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CountingSheetItemActivity extends AppCompatActivity {

    private int countingSheetId;
    private String countingSheetName, countingSheetDate,countingSheetLogisticsCompany;
    private ArrayList<CountingSheetItem> countingSheetItemList = new ArrayList<>();
    private ArrayList<Integer> countingSheetItemGoodsId = new ArrayList<>();

    private EditText et_goodsBarcode, et_goodsNumber;
    private TextView tv_goodsName;
    private ListView lv_goodsCounting;
    private String Name, Barcode;
    private int goodsId;
    private int number = 1;

    Boolean canRemove13 = true;

    String counting, barcode_text, name, amount, add, Finish, pleaseSignIt, Done,addNewItem,cancel
            ,please_count_item,please_enter_name_ofitem,error_open_file;

    public void setViewText() {
        TextView tv_counting = findViewById(R.id.tv_counting);
        TextView tv_barcode = findViewById(R.id.tv_barcode);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_amount = findViewById(R.id.tv_amount);
        TextView tv_addGoodsNumber = findViewById(R.id.tv_addGoodsNumber);
        TextView tv_Finish = findViewById(R.id.tv_Finish);
        TextView tv_pleaseSign = findViewById(R.id.tv_pleaseSign);
        TextView tvDoneSignture = findViewById(R.id.tvDoneSignture);

        counting = getResources().getString(R.string.counting);
        barcode_text = getResources().getString(R.string.barcode);
        name = getResources().getString(R.string.name);
        amount = getResources().getString(R.string.amount);
        add = getResources().getString(R.string.add);
        Finish = getResources().getString(R.string.finish);
        pleaseSignIt = getResources().getString(R.string.please_sign_it);
        Done = getResources().getString(R.string.done);
        addNewItem = getResources().getString(R.string.add_new_item);
        cancel = getResources().getString(R.string.cancel);
        please_count_item = getResources().getString(R.string.please_count_item);
        please_enter_name_ofitem = getResources().getString(R.string.please_enter_name_ofitem);
        error_open_file = getResources().getString(R.string.error_open_file);


        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(CountingSheetItemActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& counting = 10
            //&& barcode = 11
            //&& name = 12
            //&& amount = 13
            //&& add = 14
            //&& Finish = 15
            //&& pleaseSignIt = 16
            //&& Done = 17
            //&& addNewItem = 18
            //&& cancel = 19
            //&& please_count_item = 20
            //&& please_enter_name_ofitem = 21
            //&& error_open_file = 22

            AppText countingText = dataBaseHandler.getAppText(10, languageId);
            AppText barcodeText = dataBaseHandler.getAppText(11, languageId);
            AppText nameText = dataBaseHandler.getAppText(12, languageId);
            AppText amountText = dataBaseHandler.getAppText(13, languageId);
            AppText addText = dataBaseHandler.getAppText(14, languageId);
            AppText finishText = dataBaseHandler.getAppText(15, languageId);
            AppText pleaseSignItText = dataBaseHandler.getAppText(16, languageId);
            AppText DoneText = dataBaseHandler.getAppText(17, languageId);
            AppText addNewItemText = dataBaseHandler.getAppText(18, languageId);
            AppText cancelText = dataBaseHandler.getAppText(19, languageId);
            AppText please_count_itemText = dataBaseHandler.getAppText(20, languageId);
            AppText please_enter_name_ofitemText = dataBaseHandler.getAppText(21, languageId);
            AppText error_open_fileText = dataBaseHandler.getAppText(22, languageId);

            if (countingText != null) {
                counting = (countingText.text);
            }
            if (barcodeText != null) {
                barcode_text = (barcodeText.text);
            }
            if (nameText != null) {
                name = (nameText.text);
            }
            if (amountText != null) {
                amount = (amountText.text);
            }
            if (addText != null) {
                add = (addText.text);
            }
            if (finishText != null) {
                Finish = (finishText.text);
            }
            if (pleaseSignItText != null) {
                pleaseSignIt = (pleaseSignItText.text);
            }
            if (DoneText != null) {
                Done = (DoneText.text);
            }
            if (addNewItemText != null) {
                addNewItem = (addNewItemText.text);
            }
            if (cancelText != null) {
                cancel = (cancelText.text);
            }
            if (please_count_itemText != null) {
                please_count_item = (please_count_itemText.text);
            }
            if (please_enter_name_ofitemText != null) {
                please_enter_name_ofitem = (please_enter_name_ofitemText.text);
            }
            if (error_open_fileText != null) {
                error_open_file = (error_open_fileText.text);
            }


        }

        tv_counting.setText(counting);
        tv_barcode.setText(barcode_text+": ");
        tv_name.setText(name+": ");
        tv_amount.setText(amount+": ");
        tv_addGoodsNumber.setText(add);
        tv_Finish.setText(Finish);
        tv_pleaseSign.setText(pleaseSignIt);
        tvDoneSignture.setText(Done);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_sheet_item);

        et_goodsBarcode = findViewById(R.id.et_goodsBarcode);
        et_goodsNumber = findViewById(R.id.et_goodsNumber);
        tv_goodsName = findViewById(R.id.tv_goodsName);
        lv_goodsCounting = findViewById(R.id.lv_goodsCounting);

        countingSheetId = Integer.parseInt(getIntent().getStringExtra("countingSheetId"));
        countingSheetName = getIntent().getStringExtra("countingSheetName");
        countingSheetDate = getIntent().getStringExtra("countingSheetDate");
        countingSheetLogisticsCompany = getIntent().getStringExtra("countingSheetLogisticsCompany");


        setViewText();

        /**
         * get all counting sheet item for this sheetId that saved in database before
         * and show them on listView
         */
        final DataBaseHandler dataBaseHandler = new DataBaseHandler(CountingSheetItemActivity.this);
        countingSheetItemList = dataBaseHandler.getAllCountingSheetItem(countingSheetId);

        for (int i = 0; i < countingSheetItemList.size(); i++) {
            countingSheetItemGoodsId.add(countingSheetItemList.get(i).GoodsId);
        }
        PreCountingSheetItemAdapter preCountingSheetItemAdapter = new PreCountingSheetItemAdapter(CountingSheetItemActivity.this, countingSheetItemList, countingSheetItemGoodsId);
        lv_goodsCounting.setAdapter(preCountingSheetItemAdapter);


        /**
         * check when typing on editText is finished
         * when typing is finished, serviceCall function has been called
         */
        final int TYPING_TIMEOUT = 500;
        final Handler timeoutHandler = new Handler();
        final Runnable typingTimeout = new Runnable() {
            public void run() {
                serviceCall();
            }
        };
        et_goodsBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final String barcode = et_goodsBarcode.getText().toString();
                timeoutHandler.removeCallbacks(typingTimeout);

                if (barcode.length() > 3) {
                    Log.e("postBarCode last2", barcode.substring(barcode.length() - 2, barcode.length()) + "");
                } else {
                    canRemove13 = true;
                    tv_goodsName.setText("");
                    et_goodsNumber.setText("");
                    goodsId = 0;
                    Barcode = "";
                    Name = "";
                    number = 0;
                }

                if (barcode.length() > 12 && barcode.substring(barcode.length() - 2, barcode.length()).equals("13")) {
                    if (canRemove13) {
                        Log.e("postBarCode finish", barcode.substring(barcode.length() - 2, barcode.length() - 1) + "");
                        Barcode = barcode.substring(0, barcode.length() - 2);
                        Log.e("postBarCode", Barcode);
                        timeoutHandler.postDelayed(typingTimeout, TYPING_TIMEOUT);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /**
         * Add current CountingSheetItem into database
         */
        TextView tv_addGoodsNumber = findViewById(R.id.tv_addGoodsNumber);
        tv_addGoodsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et_goodsNumber.getText().toString();
                if (num.length() > 0) {
                    number = Integer.parseInt(et_goodsNumber.getText().toString());
                    if (number > 0 && goodsId > 0 && countingSheetId > 0) {
                        if (countingSheetItemGoodsId.contains(goodsId)) {
                            int index = countingSheetItemGoodsId.indexOf(goodsId);
                            if (index > -1) {
                                int id = countingSheetItemList.get(index).Id;
                                int sumNum = number + countingSheetItemList.get(index).Number;
                                CountingSheetItem countingSheetItem = new CountingSheetItem(id, countingSheetId, goodsId, sumNum, 0,0);
                                dataBaseHandler.updateCountingSheetItem(countingSheetItem);
                                countingSheetItemList.set(index, countingSheetItem);
                                countingSheetItemGoodsId.set(index, goodsId);
                                PreCountingSheetItemAdapter preCountingSheetItemAdapter = new PreCountingSheetItemAdapter(CountingSheetItemActivity.this, countingSheetItemList, countingSheetItemGoodsId);
                                lv_goodsCounting.setAdapter(preCountingSheetItemAdapter);
                                canRemove13 = true;
                                Barcode = "";
                                Name = "";
                                goodsId = 0;
                                et_goodsBarcode.setText("");
                                et_goodsBarcode.requestFocus();
                                et_goodsNumber.setText("");
                            }
                        } else {
                            CountingSheetItem countingSheetItem = new CountingSheetItem(1, countingSheetId, goodsId, number, 0,0);
                            int id = (int) dataBaseHandler.addCountingSheetItem(countingSheetItem);
                            CountingSheetItem countingSheetItem2 = new CountingSheetItem(id, countingSheetId, goodsId, number, 0,0);
                            countingSheetItemList.add(countingSheetItem2);
                            countingSheetItemGoodsId.add(goodsId);
                            PreCountingSheetItemAdapter preCountingSheetItemAdapter = new PreCountingSheetItemAdapter(CountingSheetItemActivity.this, countingSheetItemList, countingSheetItemGoodsId);
                            lv_goodsCounting.setAdapter(preCountingSheetItemAdapter);
                            canRemove13 = true;
                            Barcode = "";
                            Name = "";
                            goodsId = 0;
                            et_goodsBarcode.setText("");
                            et_goodsBarcode.requestFocus();
                            et_goodsNumber.setText("");
                        }
                    }
                }

            }
        });


        TextView tv_finish = findViewById(R.id.tv_Finish);
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countingSheetItemList.size() > 0 && countingSheetId > 0 && countingSheetDate.length() > 0 && countingSheetName.length() > 0&&
                countingSheetLogisticsCompany.length()>0) {
                    getSignature(countingSheetName, countingSheetDate,countingSheetLogisticsCompany);

                } else {
                    Toast.makeText(CountingSheetItemActivity.this, please_count_item, Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    /**
     * check barcode in goods table
     * if table contains this barcode, return its name
     * else show dialog to Add this item into table if user want
     */
    public void serviceCall() {

        DataBaseHandler dataBaseHandler = new DataBaseHandler(CountingSheetItemActivity.this);
        Goods goods = dataBaseHandler.getGoodsWithBarcode(Barcode);
        Log.e("goods barcode", Barcode + "");
        Log.e("goods detected", goods + "");

        if (goods.Id > 0) {
            canRemove13 = false;
            et_goodsBarcode.setText(Barcode);
            et_goodsNumber.setText("1");
            et_goodsNumber.requestFocus();
            et_goodsNumber.setSelection(et_goodsNumber.getText().length());
            tv_goodsName.setText(goods.Name);
            Name = goods.Name;
            goodsId = goods.Id;

        } else {
            canRemove13 = false;
            et_goodsBarcode.setText(Barcode);
            addNewGoodsIntoDatabase(Barcode);
        }
    }


    /**
     * dialog for Adding item into Goods table
     *
     * @param barcode: text of item's barcode
     */
    public void addNewGoodsIntoDatabase(final String barcode) {

        final Dialog dialog = new Dialog(CountingSheetItemActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_new_goods);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        final TextView tv_addNewItem = dialog.findViewById(R.id.tv_addNewItem);
        final TextView tv_barcode = dialog.findViewById(R.id.tv_barcode);
        final TextView tv_name = dialog.findViewById(R.id.tv_name);
        final TextView tv_goodsBarCode = dialog.findViewById(R.id.tv_goodsBarCode);
        final EditText et_GoodsName = dialog.findViewById(R.id.et_GoodsName);
        final Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final Button bt_add = dialog.findViewById(R.id.bt_add);

        String barcode_text, name, add, addNewItem, cancel;

        barcode_text = getResources().getString(R.string.barcode);
        name = getResources().getString(R.string.name);
        add = getResources().getString(R.string.add);
        addNewItem = getResources().getString(R.string.add_new_item);
        cancel = getResources().getString(R.string.cancel);


        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(CountingSheetItemActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& barcode = 11
            //&& name = 12
            //&& add = 14
            //&& addNewItem = 18
            //&& cancel = 19

            AppText barcodeText = dataBaseHandler.getAppText(11, languageId);
            AppText nameText = dataBaseHandler.getAppText(12, languageId);
            AppText addText = dataBaseHandler.getAppText(14, languageId);
            AppText addNewItemText = dataBaseHandler.getAppText(18, languageId);
            AppText cancelText = dataBaseHandler.getAppText(19, languageId);

            if (barcodeText != null) {
                barcode_text = (barcodeText.text);
            }
            if (nameText != null) {
                name = (nameText.text);
            }
            if (addText != null) {
                add = (addText.text);
            }
            if (addNewItemText != null) {
                addNewItem = (addNewItemText.text);
            }
            if (cancelText != null) {
                cancel = (cancelText.text);
            }
        }

            tv_addNewItem.setText(addNewItem);
            tv_barcode.setText(barcode_text);
            tv_name.setText(name);
            bt_cancel.setText(cancel);
            bt_add.setText(add);


        tv_goodsBarCode.setText(barcode);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                canRemove13 = true;
                Barcode = "";
                Name = "";
                goodsId = 0;
                et_goodsBarcode.setText("");
                et_goodsBarcode.requestFocus();
                et_goodsNumber.setText("");
                dialog.dismiss();
                dialog.cancel();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_GoodsName.getText().toString();
                if (name.length() > 0) {
                    Goods goods = new Goods(1, name, barcode,0);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(CountingSheetItemActivity.this);
                    goodsId = (int) dataBaseHandler.addGoods(goods);
                    tv_goodsName.setText(name);
                    Name = name;
                    et_goodsNumber.setText("1");
                    et_goodsNumber.requestFocus();
                    et_goodsNumber.setSelection(et_goodsNumber.getText().length());
                    dialog.dismiss();
                    dialog.cancel();
                } else {
                    Toast.makeText(CountingSheetItemActivity.this, please_enter_name_ofitem, Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * get counter signature for final factor
     *
     * @param countingSheetName: counter name of this factor
     * @param date:              date time of creating this factor
     */
    private void getSignature(final String countingSheetName, final String date,final  String logisticsCompany) {

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
                createPDF(countingSheetName, date, signature,logisticsCompany);
            }
        });

    }


    /**
     * creating pdf with data of this counting sheet
     *
     * @param countingSheetName: name of counter
     * @param date:              date time of counting
     * @param signature:         bitmap type of counter's signature
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPDF(String countingSheetName, String date, Bitmap signature,String logisticsCompany) {

        File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator + "storeRoomPdf");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
            if (!storageDir.mkdirs()) {
                Log.e("Error", "failed to create directory");
            }
        }
        try {
            final File file = new File(storageDir.getAbsolutePath() + File.separator + "Counting_" + countingSheetName + "_" + date + ".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            String pdfPath = file.getAbsolutePath();
            DataBaseHandler dataBaseHandler = new DataBaseHandler(CountingSheetItemActivity.this);
            CountingSheet countingSheet = new CountingSheet(countingSheetId, countingSheetName, countingSheetDate, 1, pdfPath,0,logisticsCompany);
            dataBaseHandler.updateCountingSheet(countingSheet);


            int pageCount = (int) Math.ceil(countingSheetItemList.size() / 15) + 1;
            if ((((int) (countingSheetItemList.size() / 15)) * 15) == countingSheetItemList.size()) {
                pageCount = (int) Math.ceil(countingSheetItemList.size() / 15);
            }
            PdfDocument document = new PdfDocument();


            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(600, 800, pageCount).create();
            Bitmap bmp = getResizedBitmap(signature, 100, 100);
            SharedPreferences setting = getSharedPreferences("UserInfo", 0);
            String companyName = setting.getString("CompanyName", "");
            String logistics = setting.getString("CompanyName", "");
            String companyAddress = setting.getString("LogisticsCompany", "");
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
                paint.setTextSize(8f);
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
                Paint paint5 = new Paint();
                paint5.setColor(Color.argb(255, 0, 0, 255));
                paint5.setTextSize(16f);
                paint5.setFakeBoldText(true);

                final String date2 = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
//                final String strDate = date2.substring(0, 4)+"." + date2.substring(4, 6)+"."+date2.substring(6, 8) +
//                        " " + date2.substring(9, 11) + ":" + date2.substring(11, 13) + ":" + date2.substring(13, 15);
                final String strDate = date2.substring(0, 4) + "." + date2.substring(4, 6) + "." + date2.substring(6, 8) +
                        " " + date2.substring(9, 11) + ":" + date2.substring(11, 13);

                canvas.drawText(companyName, 20, 30, paint1);
                canvas.drawText(logistics, 20, 30, paint1);
                canvas.drawText(companyAddress, 20, 60, paint10);
                canvas.drawBitmap(bLogo, 500, 10, paint);

                canvas.drawText("Date: " + strDate, 20, 90, paint2);
                canvas.drawText("Counter Name: " + countingSheetName, 20, 110, paint2);
                canvas.drawText("Logistics Company: " + logisticsCompany, 20, 130, paint5);
                canvas.drawText("Counting Date: " + date, 20, 170, paint2);

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
                if (setting.contains("countingDescription")) {
                    if (setting.getString("countingDescription", "").length() > 0) {
                        description = "Description: " + setting.getString("countingDescription", "");
                    }
                }
                StaticLayout.Builder builder0 = StaticLayout.Builder.obtain(description, 0, description.length(), myTextPaint0, width0)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setLineSpacing(spacingMultiplier0, spacingAddition0)
                        .setIncludePad(includePadding0)
                        .setMaxLines(5);
                StaticLayout myStaticLayout0 = builder0.build();
                canvas.save();
                canvas.translate(20, 130);
                myStaticLayout0.draw(canvas);
                canvas.restore();


                canvas.drawText("Item Name", 20, 220, paint3);
                canvas.drawText("Item Barcode", 220, 220, paint3);
                canvas.drawText("Item Amount", 420, 220, paint3);
                canvas.drawLine(20, 190, 580, 190, paint4);


                int totalNumber = 0;

                for (int j = 0; j < 15; j++) {

                    if (((15 * i) + j) < countingSheetItemGoodsId.size()) {
                        totalNumber = totalNumber + countingSheetItemList.get((15 * i) + j).Number;

                        Goods goods = dataBaseHandler.getGoods(countingSheetItemGoodsId.get((15 * i) + j));

                        String text1 = goods.Name;
                        String text2 = goods.Barocde;
                        String text3 = countingSheetItemList.get((15 * i) + j).Number + "";

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
                                .setMaxLines(6);
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
                        canvas.translate(450, (245 + (30 * j)));
                        myStaticLayout3.draw(canvas);
                        canvas.restore();


                        canvas.drawLine(20, (265 + (30 * j)), 580, (265 + (30 * j)), paint4);
//                        canvas.drawText(productCode, 380, 120, paint);
                    }
                }

//            canvas.drawText("Product Count: "+productCount, 10, 70, paint);

//            File sign = new File(signPath);
//            if (sign.exists()){
//                Bitmap bmp = BitmapFactory.decodeFile(signPath);

//                z = z+1;
//                if (i == pageCount - 1) {
//                    canvas.drawText("Total Number", 20, (200 + (20 * z)), paint10);
//                    canvas.drawText( totalNumber+ "", 450, (200 + (20 * z)), paint10);
//                }


                canvas.drawText("Counter Signature: ", 20, 680, paint2);
                canvas.drawBitmap(bmp, 20, 700, paint);
                canvas.drawLine(20, 690, 120, 690, paint4);
                canvas.drawLine(120, 690, 120, 790, paint4);
                canvas.drawLine(120, 790, 20, 790, paint4);
                canvas.drawLine(20, 790, 20, 690, paint4);

//            }

                document.finishPage(page);

            }

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
     * open pdf file after creating file
     *
     * @param filePath
     */
    private void openPDF(String filePath) {

        finish();
        File file = new File(filePath);
        Uri attachmentUri = FileProvider.getUriForFile(CountingSheetItemActivity.this, "com.freshdesk.helpdesk.interstoreroom", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(CountingSheetItemActivity.this, error_open_file, Toast.LENGTH_LONG).show();
            }
        } else {
            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, "application/pdf");
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(CountingSheetItemActivity.this, error_open_file, Toast.LENGTH_LONG).show();
            }
        }
    }


}
