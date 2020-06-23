package com.interhandelsgmbh.storeroom.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Adapter.EntranceReportListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ExitGoodsBarcodeListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ReturnGoodsBarcodeListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ReturnPhotoListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ReturnReportListAdapter;
import com.interhandelsgmbh.storeroom.Class.CaptureSignatureView;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackage;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackageItem;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackageGoods;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.Model.ReturnSheet;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetImage;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetPackageItem;
import com.interhandelsgmbh.storeroom.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.icu.util.ULocale.getName;
import static com.interhandelsgmbh.storeroom.BuildConfig.DEBUG;

public class ReturnSheetPackageActivity extends AppCompatActivity {

    private int SELECT_PICTURE = 1;
    private int Take_PICTURE = 2;
    private Uri mCapturedImageURI = null;
    private String selectedImagePath = "";
    public static final String DOCUMENTS_DIR2 = "documents";
    ReturnPhotoListAdapter returnPhotoListAdapter;


    String returnSheetReceiverName, returnSheetVehicleDriverName,
            returnSheetVehicleNumber, returnSheetPostBarcode, returnSheetDate,returnSheetLogisticsCompany;
    int returnSheetId;
    ListView lv_preReturnPackage;
    ArrayList<ReturnSheetPackage> returnSheetPackageList = new ArrayList<>();
    ArrayList<String> itemBarcodes = new ArrayList<>();
    String packageCode;
    Boolean canRemove13 = true;

    EditText et_PackageCode;
    TextView tvAddGoods, tv_AttachPhoto;


    String return_text, items, order_barcode, attache_photos,
            receiver_signature, save_and_get_driver_signature, driver_signature, done;
    String if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done;
    String add_comment_error, add_item_error;
    String photos, attach_some_photos_of_package, add_to_list, max_photo_select, select_photo_error, save_photos;
    String scan_atleast_one_pack,barcode_len_error;

    public void setViewText() {
        TextView tv_return = findViewById(R.id.tv_return);
        TextView tvAddGoods = findViewById(R.id.tvAddGoods);
        TextView tv_AttachPhoto = findViewById(R.id.tv_AttachPhoto);
        TextView tv_getReceiver_signature = findViewById(R.id.tv_getReceiver_signature);
        TextView rv_tvDoneSignture = findViewById(R.id.rv_tvDoneSignture);
        TextView tv_getDriver_signature = findViewById(R.id.tv_getDriver_signature);
        TextView tvDoneSignture = findViewById(R.id.tvDoneSignture);
        EditText et_PackageCode = findViewById(R.id.et_PackageCode);


        return_text = getResources().getString(R.string.return_text);
        items = getResources().getString(R.string.items);
        order_barcode = getResources().getString(R.string.order_barcode);
        attache_photos = getResources().getString(R.string.attache_photos);
        receiver_signature = getResources().getString(R.string.receiver_signature);
        save_and_get_driver_signature = getResources().getString(R.string.save_and_get_driver_signature);
        driver_signature = getResources().getString(R.string.driver_signature);
        done = getResources().getString(R.string.done);
        if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = getResources().getString(R.string.if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done);
        add_comment_error = getResources().getString(R.string.add_comment_error);
        add_item_error = getResources().getString(R.string.add_item_error);
        photos = getResources().getString(R.string.photos);
        attach_some_photos_of_package = getResources().getString(R.string.attach_some_photos_of_package);
        add_to_list = getResources().getString(R.string.add_to_list);
        max_photo_select = getResources().getString(R.string.max_photo_select);
        select_photo_error = getResources().getString(R.string.select_photo_error);
        save_photos = getResources().getString(R.string.save_photos);
        scan_atleast_one_pack = getResources().getString(R.string.scan_atleast_one_pack);
        barcode_len_error = getResources().getString(R.string.barcode_len_error);

        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(ReturnSheetPackageActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }


            //&& return_text = 63
            //&& items = 32
            //&& order_barcode = 38
            //&& attache_photos = 97
            //&& receiver_signature = 98
            //&& save_and_get_driver_signature = 99
            //&& driver_signature = 100
            //&& done = 17
            //&& if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = 101
            //&& add_comment_error = 102
            //&& add_item_error = 103
            //&& photos = 104
            //&& attach_some_photos_of_package = 105
            //&& add_to_list = 106
            //&& max_photo_select = 107
            //&& select_photo_error = 108
            //&& save_photos = 109
            //&& scan_atleast_one_pack = 33
            //&& barcode_len_error = 110



            AppText return_textText = dataBaseHandler.getAppText(63, languageId);
            AppText itemsText = dataBaseHandler.getAppText(32, languageId);
            AppText order_barcodeText = dataBaseHandler.getAppText(38, languageId);
            AppText attache_photosText = dataBaseHandler.getAppText(97, languageId);
            AppText receiver_signatureText = dataBaseHandler.getAppText(98, languageId);
            AppText save_and_get_driver_signatureText = dataBaseHandler.getAppText(99, languageId);
            AppText driver_signatureText = dataBaseHandler.getAppText(100, languageId);
            AppText doneText = dataBaseHandler.getAppText(17, languageId);
            AppText if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText = dataBaseHandler.getAppText(101, languageId);
            AppText add_comment_errorText = dataBaseHandler.getAppText(102, languageId);
            AppText add_item_errorText = dataBaseHandler.getAppText(103, languageId);
            AppText photosText = dataBaseHandler.getAppText(104, languageId);
            AppText attach_some_photos_of_packageText = dataBaseHandler.getAppText(105, languageId);
            AppText add_to_listText = dataBaseHandler.getAppText(106, languageId);
            AppText max_photo_selectText = dataBaseHandler.getAppText(107, languageId);
            AppText select_photo_errorText = dataBaseHandler.getAppText(108, languageId);
            AppText save_photosText = dataBaseHandler.getAppText(109, languageId);
            AppText scan_atleast_one_packText = dataBaseHandler.getAppText(33, languageId);
            AppText barcode_len_errorText = dataBaseHandler.getAppText(110, languageId);


            if (return_textText != null) return_text = (return_textText.text);
            if (itemsText != null) items = (itemsText.text);
            if (order_barcodeText != null) order_barcode = (order_barcodeText.text);
            if (attache_photosText != null) attache_photos = (attache_photosText.text);
            if (receiver_signatureText != null) receiver_signature = (receiver_signatureText.text);
            if (save_and_get_driver_signatureText != null)
                save_and_get_driver_signature = (save_and_get_driver_signatureText.text);
            if (driver_signatureText != null) driver_signature = (driver_signatureText.text);
            if (doneText != null) done = (doneText.text);
            if (if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText != null)
                if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done = (if_you_have_some_items_read_their_barcode_and_otherwise_click_on_doneText.text);
            if (add_comment_errorText != null) add_comment_error = (add_comment_errorText.text);
            if (add_item_errorText != null) add_item_error = (add_item_errorText.text);
            if (photosText != null) photos = (photosText.text);
            if (attach_some_photos_of_packageText != null)
                attach_some_photos_of_package = (attach_some_photos_of_packageText.text);
            if (add_to_listText != null) add_to_list = (add_to_listText.text);
            if (max_photo_selectText != null) max_photo_select = (max_photo_selectText.text);
            if (select_photo_errorText != null) select_photo_error = (select_photo_errorText.text);
            if (save_photosText != null) save_photos = (save_photosText.text);
            if (barcode_len_errorText != null) barcode_len_error = (barcode_len_errorText.text);
            if (scan_atleast_one_packText != null) scan_atleast_one_pack = (scan_atleast_one_packText.text);


        }

        tv_return.setText(return_text);
        tvAddGoods.setText(items);
        tv_AttachPhoto.setText(attache_photos);
        tv_getReceiver_signature.setText(receiver_signature);
        rv_tvDoneSignture.setText(save_and_get_driver_signature);
        tv_getDriver_signature.setText(driver_signature);
        tvDoneSignture.setText(done);
        et_PackageCode.setHint(order_barcode);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_sheet_package);


        /**
         * find views by Id
         */
        EditText et_ReceiverName = findViewById(R.id.et_ReceiverName);
        EditText et_DriverName = findViewById(R.id.et_DriverName);
        EditText etDate = findViewById(R.id.etDate);
        lv_preReturnPackage = findViewById(R.id.lv_preReturnPackage);
        et_PackageCode = findViewById(R.id.et_PackageCode);
        tvAddGoods = findViewById(R.id.tvAddGoods);
        tv_AttachPhoto = findViewById(R.id.tv_AttachPhoto);
        et_PackageCode.requestFocus();
        setViewText();

        /**
         * get return sheet info from intent
         */
        returnSheetId = Integer.parseInt(getIntent().getStringExtra("returnSheetId"));
        returnSheetReceiverName = getIntent().getStringExtra("returnSheetReceiverName");
        returnSheetVehicleDriverName = getIntent().getStringExtra("returnSheetVehicleDriverName");
        returnSheetVehicleNumber = getIntent().getStringExtra("returnSheetVehicleNumber");
        returnSheetPostBarcode = getIntent().getStringExtra("returnSheetPostBarcode");
        returnSheetDate = getIntent().getStringExtra("returnSheetDate");
        returnSheetLogisticsCompany = getIntent().getStringExtra("returnSheetLogisticsCompany");

        et_ReceiverName.setText(returnSheetReceiverName);
        et_ReceiverName.setEnabled(false);
        et_DriverName.setText(returnSheetVehicleDriverName);
        et_DriverName.setEnabled(false);
        etDate.setText(returnSheetDate);
        etDate.setEnabled(false);


        /**
         * get pre return sheet package for this returnSheetId that saved in database
         * db: TABLE_ReturnSheetPackage
         */
        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReturnSheetPackageActivity.this);
        returnSheetPackageList = dataBaseHandler.getAllReturnSheetPackage(returnSheetId);
        for (int i = 0; i < returnSheetPackageList.size(); i++) {

            ArrayList<ReturnSheetPackageItem> returnSheetPackageItems = dataBaseHandler.getAllReturnSheetPackageItems(returnSheetPackageList.get(i).Id);
            ArrayList<Goods> goodsList = new ArrayList<>();
            StringBuilder goodsString = new StringBuilder();
            for (int j = 0; j < returnSheetPackageItems.size(); j++) {
                Goods goods = dataBaseHandler.getGoods(returnSheetPackageItems.get(j).GoodsId);
                goodsList.add(goods);
                goodsString.append(goods.Barocde + " - " + goods.Name + "\n");
            }
            String items = goodsString.toString();
            itemBarcodes.add(items);
        }

        ReturnReportListAdapter returnReportListAdapter = new ReturnReportListAdapter(ReturnSheetPackageActivity.this, returnSheetPackageList, itemBarcodes);
        lv_preReturnPackage.setAdapter(returnReportListAdapter);

        final int TYPING_TIMEOUT = 500;
        final Handler timeoutHandlerPost = new Handler();
        final Runnable typingTimeoutPost = new Runnable() {
            public void run() {
                serviceCall();
            }
        };
        /**
         * check when typing in package barcode finished
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

                if (number.length() > 7 && number.substring(number.length() - 2, number.length()).equals("13")) {
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
                if (packageCode.length() > 4) {
                    alertDialog_AddGoods(packageCode);
                } else if (packageCode.length() == 0) {
                    packageCode = " ";
                    alertDialog_AddGoods(packageCode);
                } else {
                    Toast.makeText(ReturnSheetPackageActivity.this, barcode_len_error, Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_AttachPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (returnSheetPackageList.size() < 1)
//                    Toast.makeText(ReturnSheetPackageActivity.this, scan_atleast_one_pack, Toast.LENGTH_LONG).show();
//                else {
                    alertDialog_AttachePhotos(returnSheetId + "");
//                }
            }
        });


    }


    /**
     * action that call after typinf in packageCode_editText
     */
    public void serviceCall() {

        canRemove13 = false;
        et_PackageCode.setText(packageCode);
        String packageCode = et_PackageCode.getText().toString();
        alertDialog_AddGoods(packageCode);

    }

    /**
     * add items for package
     *
     * @param packageCode
     */
    public void alertDialog_AddGoods(final String packageCode) {

        canRemove13 = true;
        final Dialog dialog = new Dialog(ReturnSheetPackageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_get_goods_barcode);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_items = dialog.findViewById(R.id.tv_items);
        TextView tv_items_title = dialog.findViewById(R.id.tv_items_title);
        tv_items.setText(items);
        tv_items_title.setText(if_you_have_some_items_read_their_barcode_and_otherwise_click_on_done);
        final ArrayList<Goods> goodsBarCodeList = new ArrayList<>();
        ArrayList<Integer> isEnableList = new ArrayList<>();
        ArrayList<String> itemComment = new ArrayList<>();

        Goods goods = new Goods(0, "", "",0);
        goodsBarCodeList.add(goods);
        isEnableList.add(1);
        itemComment.add("");

        final ListView lv_GoodsCodeList = dialog.findViewById(R.id.lv_GoodsCodeList);
        final ReturnGoodsBarcodeListAdapter goodsBarcodeListAdapter = new ReturnGoodsBarcodeListAdapter(ReturnSheetPackageActivity.this, lv_GoodsCodeList, goodsBarCodeList, itemComment, isEnableList);
        lv_GoodsCodeList.setAdapter(goodsBarcodeListAdapter);

        Button bt_done = dialog.findViewById(R.id.bt_done);
        bt_done.setText(done);
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Goods> goods0 = new ArrayList<>();
                ArrayList<Goods> goods = new ArrayList<>();
                ArrayList<String> comments0 = new ArrayList<>();
                ArrayList<String> comments = new ArrayList<>();
                goods0 = goodsBarcodeListAdapter.getGoodsBarCodesValue();
                comments0 = goodsBarcodeListAdapter.getCommentValue();

                if (goods0.size() - 1 > 0) {
                    Goods endGoods = goods0.get(goods0.size() - 1);
                    if (endGoods.Name.length() > 0) {
                        if (comments0.get(comments0.size() - 1).length() == 0) {
                            Toast.makeText(ReturnSheetPackageActivity.this, add_comment_error, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ReturnSheetPackageActivity.this, add_comment_error, Toast.LENGTH_LONG).show();
                        }
                    } else {

                        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReturnSheetPackageActivity.this);
                        ReturnSheetPackage returnSheetPackage = new ReturnSheetPackage(0, returnSheetId, packageCode,0);
                        int returnSheetPackageId = (int) dataBaseHandler.addReturnSheetPackage(returnSheetPackage);
                        ReturnSheetPackage returnSheetPackage1 = new ReturnSheetPackage(returnSheetPackageId, returnSheetId, packageCode,0);


                        returnSheetPackageList.add(returnSheetPackage1);

                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < goods0.size() - 1; i++) {
                            goods.add(goods0.get(i));
                            stringBuilder.append(goods0.get(i).Barocde + " - " + goods0.get(i).Name + "\n");
                            ReturnSheetPackageItem returnSheetPackageItem = new ReturnSheetPackageItem(0, returnSheetPackageId, goods0.get(i).Id, comments0.get(i),0);
                            dataBaseHandler.addReturnSheetPackageItem(returnSheetPackageItem);

                        }

                        String goodsList = stringBuilder.toString();
                        itemBarcodes.add(goodsList + "");

                        et_PackageCode.setEnabled(true);
                        canRemove13 = true;
                        et_PackageCode.setText("");
                        et_PackageCode.requestFocus();
                        ReturnReportListAdapter returnReportListAdapter = new ReturnReportListAdapter(ReturnSheetPackageActivity.this, returnSheetPackageList, itemBarcodes);
                        lv_preReturnPackage.setAdapter(returnReportListAdapter);
                        dialog.dismiss();
                        dialog.cancel();
                    }
                } else {
                    Toast.makeText(ReturnSheetPackageActivity.this, add_item_error, Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * attache Photos to
     *
     * @param returnSheetId
     */
    public void alertDialog_AttachePhotos(final String returnSheetId) {

        final DataBaseHandler dataBaseHandler = new DataBaseHandler(ReturnSheetPackageActivity.this);
        int sheetId = Integer.parseInt(returnSheetId);
        dataBaseHandler.deleteAllReturnSheetImage(sheetId);

        final Dialog dialog = new Dialog(ReturnSheetPackageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_return_photos);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_photoDialog = dialog.findViewById(R.id.tv_photoDialog);
        TextView tv_addPhotoText = dialog.findViewById(R.id.tv_addPhotoText);
        TextView tv_showPhoto = dialog.findViewById(R.id.tv_showPhoto);
        tv_photoDialog.setText(photos);
        tv_addPhotoText.setText(attach_some_photos_of_package);
        tv_showPhoto.setText(add_to_list);

        ImageView imv_attache = dialog.findViewById(R.id.imv_attache);
        ImageView imv_takePhoto = dialog.findViewById(R.id.imv_takePhoto);
        final LinearLayout bt_addPic = dialog.findViewById(R.id.bt_addPic);
        bt_addPic.setEnabled(true);
        bt_addPic.setVisibility(View.GONE);
        final ListView lv_returnPhoto = dialog.findViewById(R.id.lv_returnPhoto);

        final ArrayList<ReturnSheetImage> photosPaths = new ArrayList<>();


        returnPhotoListAdapter = new ReturnPhotoListAdapter(ReturnSheetPackageActivity.this, photosPaths);

        imv_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ReturnSheetImage> photosPaths0 = new ArrayList<>();
                photosPaths0 = returnPhotoListAdapter.getPhotosPathValue();
                if (photosPaths0.size() < 4) {
                    take_photo();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bt_addPic.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                } else {
                    Toast.makeText(ReturnSheetPackageActivity.this, max_photo_select, Toast.LENGTH_LONG).show();
                }
            }
        });


        imv_attache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ReturnSheetImage> photosPaths0 = new ArrayList<>();
                photosPaths0 = returnPhotoListAdapter.getPhotosPathValue();
                if (photosPaths0.size() < 4) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bt_addPic.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                } else {
                    Toast.makeText(ReturnSheetPackageActivity.this, max_photo_select, Toast.LENGTH_LONG).show();
                }
            }
        });

        bt_addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_addPic.setVisibility(View.GONE);

                if (selectedImagePath.length() > 0) {
                    int returnSheetId0 = Integer.parseInt(returnSheetId);
                    ReturnSheetImage returnSheetImage = new ReturnSheetImage(0, returnSheetId0, selectedImagePath,0);
                    int id = (int) dataBaseHandler.addReturnSheetImage(returnSheetImage);
                    ReturnSheetImage returnSheetImage1 = new ReturnSheetImage(id, returnSheetId0, selectedImagePath,0);
                    photosPaths.add(returnSheetImage1);
                    returnPhotoListAdapter = new ReturnPhotoListAdapter(ReturnSheetPackageActivity.this, photosPaths);
                    lv_returnPhoto.setAdapter(returnPhotoListAdapter);
                    selectedImagePath = "";
                } else {
                    Toast.makeText(ReturnSheetPackageActivity.this, select_photo_error, Toast.LENGTH_LONG).show();
                }
            }
        });


        Button bt_done = dialog.findViewById(R.id.bt_done);
        bt_done.setText(save_photos);
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ReturnSheetImage> finalPhotos = returnPhotoListAdapter.getPhotosPathValue();
                if (finalPhotos.size() > 0) {
                    getReceiverSignature();
                    dialog.dismiss();
                    dialog.cancel();

                } else {
                    Toast.makeText(ReturnSheetPackageActivity.this, select_photo_error, Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    private void getReceiverSignature() {

        final LinearLayout rvsignLayout = findViewById(R.id.receiver_signLayout);
        final LinearLayout drsignLayout = findViewById(R.id.signLayout);
        final LinearLayout viewLayout = findViewById(R.id.viewLayout);
        TextView tvDoneSignature = findViewById(R.id.rv_tvDoneSignture);
        LinearLayout signatuerLayout = findViewById(R.id.rv_signatuerLayout);
        final CaptureSignatureView mSig = new CaptureSignatureView(this, null);
        signatuerLayout.addView(mSig, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        viewLayout.setVisibility(View.INVISIBLE);
        drsignLayout.setVisibility(View.INVISIBLE);
        rvsignLayout.setVisibility(View.VISIBLE);
        tvDoneSignature.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Bitmap signature = mSig.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                signature.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String rvSign = Base64.encodeToString(b, Base64.DEFAULT);
                getDriverSignature(rvSign);
            }
        });

    }


    private void getDriverSignature(final String rvSign) {

        final LinearLayout rvsignLayout = findViewById(R.id.receiver_signLayout);
        final LinearLayout drsignLayout = findViewById(R.id.signLayout);
        final LinearLayout viewLayout = findViewById(R.id.viewLayout);
        TextView tvDoneSignature = findViewById(R.id.tvDoneSignture);
        LinearLayout signatuerLayout = findViewById(R.id.signatuerLayout);
        final CaptureSignatureView mSig = new CaptureSignatureView(this, null);
        signatuerLayout.addView(mSig, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        viewLayout.setVisibility(View.INVISIBLE);
        drsignLayout.setVisibility(View.VISIBLE);
        rvsignLayout.setVisibility(View.INVISIBLE);
        tvDoneSignature.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Bitmap signature = mSig.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                signature.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String drSign = Base64.encodeToString(b, Base64.DEFAULT);
                createPDF(rvSign, drSign);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPDF(String rv_sign, String dr_sign) {


        ArrayList<ReturnSheetPackage> returnSheetPackages0 = new ArrayList<>();
        ArrayList<Goods> pdfReturnSheetPackagesGoods = new ArrayList<>();
        ArrayList<String> pdfReturnSheetPackagesComment = new ArrayList<>();
        ArrayList<ReturnSheetPackage> pdfReturnSheetPackages = new ArrayList<>();
        ArrayList<ReturnSheetImage> pdfReturnSheetImage = new ArrayList<>();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReturnSheetPackageActivity.this);
        pdfReturnSheetImage = dataBaseHandler.getAllReturnSheetImage(returnSheetId);
        returnSheetPackages0 = dataBaseHandler.getAllReturnSheetPackage(returnSheetId);
        for (int i = 0; i < returnSheetPackages0.size(); i++) {
            ArrayList<ReturnSheetPackageItem> returnSheetPackageItemList = dataBaseHandler.getAllReturnSheetPackageItems(returnSheetPackages0.get(i).Id);

            if (returnSheetPackageItemList.size() > 0) {
                for (int j = 0; j < returnSheetPackageItemList.size(); j++) {
                    Goods goods = dataBaseHandler.getGoods(returnSheetPackageItemList.get(j).GoodsId);
                    pdfReturnSheetPackages.add(returnSheetPackages0.get(i));
                    pdfReturnSheetPackagesGoods.add(goods);
                    pdfReturnSheetPackagesComment.add(returnSheetPackageItemList.get(j).Commnet);

                }
            } else {
                Goods goods = new Goods(0, "", "",0);
                pdfReturnSheetPackages.add(returnSheetPackages0.get(i));
                pdfReturnSheetPackagesGoods.add(goods);
                pdfReturnSheetPackagesComment.add("");

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
            final File file = new File(storageDir.getAbsolutePath() + File.separator + "Return_" + returnSheetReceiverName + "_" + returnSheetDate + ".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);


            int pageCount = (int) Math.ceil(pdfReturnSheetPackages.size() / 9) + 1;
            if ((((int) (pdfReturnSheetPackages.size() / 9)) * 9) == pdfReturnSheetPackages.size()) {
                pageCount = (int) Math.ceil(pdfReturnSheetPackages.size() / 9);
            }
            pageCount = pageCount + 1;
            PdfDocument document = new PdfDocument();


            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(600, 800, pageCount).create();

            byte[] decodedByte = Base64.decode(rv_sign, 0);
            Bitmap b_rv_sign = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

            byte[] decodedByte2 = Base64.decode(dr_sign, 0);
            Bitmap b_dr_sign = BitmapFactory.decodeByteArray(decodedByte2, 0, decodedByte2.length);


            b_rv_sign = getResizedBitmap(b_rv_sign, 100, 100);
            b_dr_sign = getResizedBitmap(b_dr_sign, 100, 100);
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
            for (int i = 0; i < pageCount - 1; i++) {

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


                String strDate = returnSheetDate;

                canvas.drawText(companyName, 20, 30, paint1);
                canvas.drawText(companyAddress, 20, 60, paint10);
                canvas.drawBitmap(bLogo, 500, 10, paint);
                canvas.drawText("Receiver: " + returnSheetReceiverName, 20, 90, paint2);
                canvas.drawText("LogisticsCompany: " + returnSheetLogisticsCompany, 20, 120, paint2);
                canvas.drawText("Vehicle Driver name: " + returnSheetVehicleDriverName, 20, 1300, paint2);
                canvas.drawText("Vehicle Number: " + returnSheetVehicleNumber, 20, 150, paint2);
                canvas.drawText("Post Barcode: " + returnSheetPostBarcode, 20, 170, paint2);


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
                if (setting.contains("returnDescription")) {
                    if (setting.getString("returnDescription", "").length() > 0) {
                        description = "Description: " + setting.getString("returnDescription", "");
                    }
                }
                StaticLayout.Builder builder0 = StaticLayout.Builder.obtain(description, 0, description.length(), myTextPaint0, width0)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setLineSpacing(spacingMultiplier0, spacingAddition0)
                        .setIncludePad(includePadding0)
                        .setMaxLines(4);
                StaticLayout myStaticLayout0 = builder0.build();
                canvas.save();
                canvas.translate(20, 185);
                myStaticLayout0.draw(canvas);
                canvas.restore();


                canvas.drawText("Order Barcode", 20, 225, paint3);
                canvas.drawText("Item Barcode", 180, 225, paint3);
                canvas.drawText("Item Name", 340, 225, paint3);
                canvas.drawText("Comment", 500, 1225, paint3);
                canvas.drawLine(20, 245, 580, 245, paint4);
//                canvas.drawText("Product Code", 380, 100, paint);


                for (int j = 0; j < 9; j++) {
                    if (((9 * i) + j) < pdfReturnSheetPackages.size()) {


                        String text1 = pdfReturnSheetPackages.get(((9 * i) + j)).OrderBarcode;
                        String text2 = pdfReturnSheetPackagesGoods.get(((9 * i) + j)).Barocde;
                        String text3 = pdfReturnSheetPackagesGoods.get(((9 * i) + j)).Name;
                        String text4 = pdfReturnSheetPackagesComment.get(((9 * i) + j));

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
                        canvas.translate(20, (260 + (45 * j)));
                        myStaticLayout1.draw(canvas);
                        canvas.restore();

                        StaticLayout.Builder builder2 = StaticLayout.Builder.obtain(text2, 0, text2.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout2 = builder2.build();
                        canvas.save();
                        canvas.translate(180, (260 + (45 * j)));
                        myStaticLayout2.draw(canvas);
                        canvas.restore();


                        StaticLayout.Builder builder3 = StaticLayout.Builder.obtain(text3, 0, text3.length(), myTextPaint, width)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(5);
                        StaticLayout myStaticLayout3 = builder3.build();
                        canvas.save();
                        canvas.translate(340, (260 + (45 * j)));
                        myStaticLayout3.draw(canvas);
                        canvas.restore();


                        int width2 = 100;
                        StaticLayout.Builder builder4 = StaticLayout.Builder.obtain(text4, 0, text4.length(), myTextPaint, width2)
                                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                                .setLineSpacing(spacingMultiplier, spacingAddition)
                                .setIncludePad(includePadding)
                                .setMaxLines(6);
                        StaticLayout myStaticLayout4 = builder4.build();
                        canvas.save();
                        canvas.translate(490, (260 + (45 * j)));
                        myStaticLayout4.draw(canvas);
                        canvas.restore();


                        canvas.drawLine(20, (282 + (45 * j)), 580, (282 + (45 * j)), paint4);
                    }
                }

//            canvas.drawText("Product Count: "+productCount, 10, 70, paint);

//            File sign = new File(signPath);
//            if (sign.exists()){
//                Bitmap bmp = BitmapFactory.decodeFile(signPath);
                canvas.drawText("Receiver Signature: ", 20, 680, paint2);
                canvas.drawBitmap(b_rv_sign, 20, 700, paint);
                canvas.drawLine(20, 690, 120, 690, paint4);
                canvas.drawLine(120, 690, 120, 790, paint4);
                canvas.drawLine(120, 790, 20, 790, paint4);
                canvas.drawLine(20, 790, 20, 690, paint4);


                canvas.drawText("Driver Signature: ", 180, 680, paint2);
                canvas.drawBitmap(b_dr_sign, 180, 700, paint);
                canvas.drawLine(180, 690, 280, 690, paint4);
                canvas.drawLine(280, 690, 280, 790, paint4);
                canvas.drawLine(280, 790, 180, 790, paint4);
                canvas.drawLine(180, 790, 180, 690, paint4);


//            }

                document.finishPage(page);

            }


            for (int i = 0; i < 1; i++) {

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


                String strDate = returnSheetDate;

                canvas.drawText(companyName, 20, 30, paint1);
                canvas.drawText(companyAddress, 20, 60, paint10);
                canvas.drawBitmap(bLogo, 500, 10, paint);

                canvas.drawText("Receiver: " + returnSheetReceiverName, 20, 80, paint2);
                canvas.drawText("Vehicle Driver name: " + returnSheetVehicleDriverName, 20, 100, paint2);
                canvas.drawText("Vehicle Number: " + returnSheetVehicleNumber, 20, 120, paint2);
                canvas.drawText("Post Barcode: " + returnSheetPostBarcode, 20, 140, paint2);

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
                if (setting.contains("returnDescription")) {
                    if (setting.getString("returnDescription", "").length() > 0) {
                        description = "Description: " + setting.getString("returnDescription", "");
                    }
                }

                StaticLayout.Builder builder0 = StaticLayout.Builder.obtain(description, 0, description.length(), myTextPaint0, width0)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setLineSpacing(spacingMultiplier0, spacingAddition0)
                        .setIncludePad(includePadding0)
                        .setMaxLines(4);
                StaticLayout myStaticLayout0 = builder0.build();
                canvas.save();
                canvas.translate(20, 150);
                myStaticLayout0.draw(canvas);
                canvas.restore();


                canvas.drawLine(20, 180, 580, 180, paint4);
//                canvas.drawText("Product Code", 380, 100, paint);


//                if (pdfReturnSheetImage.size() == 1) {
//                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
//                    Bitmap bimage1 = null;
//                    if (image1.exists()) {
//                        bimage1 = BitmapFactory.decodeFile(componyLogoPath);
//                        bimage1 = getResizedBitmap(bimage1, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage1, 20, 180, paint);
//                } else if (pdfReturnSheetImage.size() == 2) {
//                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
//                    Bitmap bimage1 = null;
//                    if (image1.exists()) {
//                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
//                        bimage1 = getResizedBitmap(bimage1, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage1, 20, 180, paint);
//
//                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
//                    Bitmap bimage2 = null;
//                    if (image2.exists()) {
//                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
//                        bimage2 = getResizedBitmap(bimage2, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage2, 220, 180, paint);
//                } else if (pdfReturnSheetImage.size() == 3) {
//                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
//                    Bitmap bimage1 = null;
//                    if (image1.exists()) {
//                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
//                        bimage1 = getResizedBitmap(bimage1, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage1, 20, 180, paint);
//
//                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
//                    Bitmap bimage2 = null;
//                    if (image2.exists()) {
//                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
//                        bimage2 = getResizedBitmap(bimage2, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage2, 220, 180, paint);
//
//                    File image3 = new File(pdfReturnSheetImage.get(2).ImageBinary);
//                    Bitmap bimage3 = null;
//                    if (image3.exists()) {
//                        bimage3 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(2).ImageBinary);
//                        bimage3 = getResizedBitmap(bimage3, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage3, 20, 350, paint);
//                } else if (pdfReturnSheetImage.size() == 4) {
//                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
//                    Bitmap bimage1 = null;
//                    if (image1.exists()) {
//                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
//                        bimage1 = getResizedBitmap(bimage1, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage1, 20, 180, paint);
//
//                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
//                    Bitmap bimage2 = null;
//                    if (image2.exists()) {
//                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
//                        bimage2 = getResizedBitmap(bimage2, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage2, 220, 180, paint);
//
//                    File image3 = new File(pdfReturnSheetImage.get(2).ImageBinary);
//                    Bitmap bimage3 = null;
//                    if (image3.exists()) {
//                        bimage3 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(2).ImageBinary);
//                        bimage3 = getResizedBitmap(bimage3, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage3, 20, 350, paint);
//
//                    File image4 = new File(pdfReturnSheetImage.get(3).ImageBinary);
//                    Bitmap bimage4 = null;
//                    if (image4.exists()) {
//                        bimage4 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(3).ImageBinary);
//                        bimage4 = getResizedBitmap(bimage4, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage4, 220, 350, paint);
//                } else if (pdfReturnSheetImage.size() == 5) {
//                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
//                    Bitmap bimage1 = null;
//                    if (image1.exists()) {
//                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
//                        bimage1 = getResizedBitmap(bimage1, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage1, 20, 180, paint);
//
//                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
//                    Bitmap bimage2 = null;
//                    if (image2.exists()) {
//                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
//                        bimage2 = getResizedBitmap(bimage2, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage2, 220, 180, paint);
//
//                    File image3 = new File(pdfReturnSheetImage.get(2).ImageBinary);
//                    Bitmap bimage3 = null;
//                    if (image3.exists()) {
//                        bimage3 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(2).ImageBinary);
//                        bimage3 = getResizedBitmap(bimage3, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage3, 20, 350, paint);
//
//                    File image4 = new File(pdfReturnSheetImage.get(3).ImageBinary);
//                    Bitmap bimage4 = null;
//                    if (image4.exists()) {
//                        bimage4 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(3).ImageBinary);
//                        bimage4 = getResizedBitmap(bimage4, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage4, 220, 350, paint);
//
//
//                    File image5 = new File(pdfReturnSheetImage.get(4).ImageBinary);
//                    Bitmap bimage5 = null;
//                    if (image5.exists()) {
//                        bimage5 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(4).ImageBinary);
//                        bimage5 = getResizedBitmap(bimage5, 150, 150);
//                    }
//                    canvas.drawBitmap(bimage5, 20, 520, paint);
//
//
//                }


                if (pdfReturnSheetImage.size() == 1) {
                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
                    Bitmap bimage1 = null;
                    if (image1.exists()) {
                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
                        bimage1 = getResizedBitmap(bimage1, 225, 225);
                    }
                    canvas.drawBitmap(bimage1, 65, 190, paint);
                } else if (pdfReturnSheetImage.size() == 2) {
                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
                    Bitmap bimage1 = null;
                    if (image1.exists()) {
                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
                        bimage1 = getResizedBitmap(bimage1, 225, 225);
                    }
                    canvas.drawBitmap(bimage1, 65, 190, paint);

                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
                    Bitmap bimage2 = null;
                    if (image2.exists()) {
                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
                        bimage2 = getResizedBitmap(bimage2, 225, 225);
                    }
                    canvas.drawBitmap(bimage2, 310, 190, paint);
                } else if (pdfReturnSheetImage.size() == 3) {
                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
                    Bitmap bimage1 = null;
                    if (image1.exists()) {
                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
                        bimage1 = getResizedBitmap(bimage1, 225, 225);
                    }
                    canvas.drawBitmap(bimage1, 65, 190, paint);

                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
                    Bitmap bimage2 = null;
                    if (image2.exists()) {
                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
                        bimage2 = getResizedBitmap(bimage2, 225, 225);
                    }
                    canvas.drawBitmap(bimage2, 310, 190, paint);

                    File image3 = new File(pdfReturnSheetImage.get(2).ImageBinary);
                    Bitmap bimage3 = null;
                    if (image3.exists()) {
                        bimage3 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(2).ImageBinary);
                        bimage3 = getResizedBitmap(bimage3, 225, 225);
                    }
                    canvas.drawBitmap(bimage3, 65, 430, paint);
                } else if (pdfReturnSheetImage.size() == 4) {
                    File image1 = new File(pdfReturnSheetImage.get(0).ImageBinary);
                    Bitmap bimage1 = null;
                    if (image1.exists()) {
                        bimage1 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(0).ImageBinary);
                        bimage1 = getResizedBitmap(bimage1, 225, 225);
                    }
                    canvas.drawBitmap(bimage1, 65, 430, paint);

                    File image2 = new File(pdfReturnSheetImage.get(1).ImageBinary);
                    Bitmap bimage2 = null;
                    if (image2.exists()) {
                        bimage2 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(1).ImageBinary);
                        bimage2 = getResizedBitmap(bimage2, 225, 225);
                    }
                    canvas.drawBitmap(bimage2, 310, 190, paint);

                    File image3 = new File(pdfReturnSheetImage.get(2).ImageBinary);
                    Bitmap bimage3 = null;
                    if (image3.exists()) {
                        bimage3 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(2).ImageBinary);
                        bimage3 = getResizedBitmap(bimage3, 225, 225);
                    }
                    canvas.drawBitmap(bimage3, 65, 430, paint);

                    File image4 = new File(pdfReturnSheetImage.get(3).ImageBinary);
                    Bitmap bimage4 = null;
                    if (image4.exists()) {
                        bimage4 = BitmapFactory.decodeFile(pdfReturnSheetImage.get(3).ImageBinary);
                        bimage4 = getResizedBitmap(bimage4, 225, 225);
                    }
                    canvas.drawBitmap(bimage4, 310, 430, paint);
                }


//            canvas.drawText("Product Count: "+productCount, 10, 70, paint);

//            File sign = new File(signPath);
//            if (sign.exists()){
//                Bitmap bmp = BitmapFactory.decodeFile(signPath);
                canvas.drawText("Receiver Signature: ", 20, 680, paint2);
                canvas.drawBitmap(b_rv_sign, 20, 700, paint);
                canvas.drawLine(20, 690, 120, 690, paint4);
                canvas.drawLine(120, 690, 120, 790, paint4);
                canvas.drawLine(120, 790, 20, 790, paint4);
                canvas.drawLine(20, 790, 20, 690, paint4);


                canvas.drawText("Driver Signature: ", 180, 680, paint2);
                canvas.drawBitmap(b_dr_sign, 180, 700, paint);
                canvas.drawLine(180, 690, 280, 690, paint4);
                canvas.drawLine(280, 690, 280, 790, paint4);
                canvas.drawLine(280, 790, 180, 790, paint4);
                canvas.drawLine(180, 790, 180, 690, paint4);


//            }

                document.finishPage(page);

            }


            String pdfPath = file.getAbsolutePath();
            ReturnSheet returnSheet = new ReturnSheet(returnSheetId, returnSheetDate, returnSheetReceiverName, returnSheetVehicleDriverName, returnSheetVehicleNumber, returnSheetPostBarcode, 1, pdfPath,0,returnSheetLogisticsCompany);
            dataBaseHandler.updateReturnSheet(returnSheet);


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
        Uri attachmentUri = FileProvider.getUriForFile(ReturnSheetPackageActivity.this, "com.freshdesk.helpdesk.interstoreroom", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(ReturnSheetPackageActivity.this, "Error! Can't open file.", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, "application/pdf");
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(ReturnSheetPackageActivity.this, "Error! Can't open file.", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(ReturnSheetPackageActivity.this, selectedImageUri);
            } else if (requestCode == Take_PICTURE) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                selectedImagePath = cursor.getString(column_index_data);
            }
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
////                final Uri contentUri = ContentUris.withAppendedId(
////                        Uri.parse("com.android.providers.downloads.documents"), Long.valueOf(id));
//                return getDataColumn(context, contentUri, null, null);

                final String id = DocumentsContract.getDocumentId(uri);

                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4);
                }

                String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads",
                        "content://downloads/all_downloads"
                };

                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                    try {
                        String path = getDataColumn(context, contentUri, null, null);
                        if (path != null) {
                            return path;
                        }
                    } catch (Exception e) {
                    }
                }

                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(context, uri);
                File cacheDir = getDocumentCacheDir(context);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(context, uri, destinationPath);
                }

                return destinationPath;


            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static void saveFileFromUri(Context context, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }

        File file = new File(directory, name);

        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }

            int index = 0;

            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }

        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
//            Log.w(TAG, e);
            return null;
        }

        logDir(directory);

        return file;
    }

    public static File getDocumentCacheDir(@NonNull Context context) {
        File dir = new File(context.getCacheDir(), DOCUMENTS_DIR2);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        logDir(context.getCacheDir());
        logDir(dir);

        return dir;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static void logDir(File dir) {
        if (!DEBUG) return;
//        Log.d(TAG, "Dir=" + dir);
        File[] files = dir.listFiles();
        for (File file : files) {
//            Log.d(TAG, "File=" + file.getPath());
        }
    }

    public static String getFileName(@NonNull Context context, Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        String filename = null;

        if (mimeType == null && context != null) {
            String path = getPath(context, uri);
            if (path == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    filename = getName(uri.toString());
                }
            } else {
                File file = new File(path);
                filename = file.getName();
            }
        } else {
            Cursor returnCursor = context.getContentResolver().query(uri, null,
                    null, null, null);
            if (returnCursor != null) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                filename = returnCursor.getString(nameIndex);
                returnCursor.close();
            }
        }

        return filename;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public void take_photo() {
        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intent, Take_PICTURE);
    }


}
