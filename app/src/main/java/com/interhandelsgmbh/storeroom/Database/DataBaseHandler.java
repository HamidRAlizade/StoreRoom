package com.interhandelsgmbh.storeroom.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.interhandelsgmbh.storeroom.Model.AllData;
import com.interhandelsgmbh.storeroom.Model.AllDataLastIndex;
import com.interhandelsgmbh.storeroom.Model.AllDataString;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.Model.CountingSheetItem;
import com.interhandelsgmbh.storeroom.Model.EntranceSheet;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackage;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackageItem;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackageGoods;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.Model.Language;
import com.interhandelsgmbh.storeroom.Model.ReturnSheet;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetImage;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetPackageItem;
import com.interhandelsgmbh.storeroom.Model.SettingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "storeroom.db";

    ///// table Names
    private static final String TABLE_Goods = "goods";
    private static final String TABLE_CountingSheet = "CountingSheet";
    private static final String TABLE_CountingSheetItem = "CountingSheetItem";
    private static final String TABLE_ExitSheet = "ExitSheet";
    private static final String TABLE_ExitSheetPackage = "ExitSheetPackage";
    private static final String TABLE_ExitSheetPackageGoods = "ExitSheetPackageGoods";
    private static final String TABLE_EntranceSheet = "EntranceSheet";
    private static final String TABLE_EntranceSheetPackage = "EntranceSheetPackage";
    private static final String TABLE_EntranceSheetPackageItem = "EntranceSheetPackageGoods";
    private static final String TABLE_ReturnSheet = "ReturnSheet";
    private static final String TABLE_ReturnSheetImage = "ReturnSheetImage";
    private static final String TABLE_ReturnSheetPackage = "ReturnSheetPackage";
    private static final String TABLE_ReturnSheetPackageItem = "ReturnSheetPackageItem";
    private static final String TABLE_SettingScanner = "SettingScanner";
    private static final String TABLE_Language = "Language";
    private static final String TABLE_AppText = "AppText";


    ///// table key Names
    //TABLE_Language
    private static final String Language_KEY_ID = "Id";
    private static final String Language_KEY_Language = "Language";
    //TABLE_AppText
    private static final String AppText_KEY_ID = "Id";
    private static final String AppText_KEY_ParentId = "ParentId";
    private static final String AppText_KEY_LanguageId = "LanguageId";
    private static final String AppText_KEY_Text = "Text";
    //TABLE_Goods
    private static final String Goods_KEY_ID = "Id";
    private static final String Goods_KEY_NAME = "Name";
    private static final String Goods_KEY_BARCODE = "Barcode";
    private static final String Goods_KEY_IsSend = "IsSend";
    //TABLE_CountingSheet
    private static final String CountingSheet_KEY_ID = "Id";
    private static final String CountingSheet_KEY_CounterName = "CounterName";
    private static final String CountingSheet_KEY_CountingDate = "CountingDate";
    private static final String CountingSheet_KEY_IsDONE = "IsDone";
    private static final String CountingSheet_KEY_PdfPath = "PdfPath";
    private static final String CountingSheet_KEY_IsSend = "IsSend";
    private static final String CountingSheet_KEY_Company = "LogisticsCompany";

    //TABLE_CountingSheetItem
    private static final String CountingSheetItem_KEY_ID = "Id";
    private static final String CountingSheetItem_KEY_CountingSheetId = "CountingSheetId";
    private static final String CountingSheetItem_KEY_GoodsId = "GoodsId";
    private static final String CountingSheetItem_KEY_Number = "Number";
    private static final String CountingSheetItem_KEY_IsDONE = "IsDone";
    private static final String CountingSheetItem_KEY_IsSend = "IsSend";

    //TABLE_ExitSheet
    private static final String ExitSheet_KEY_ID = "Id";
    private static final String ExitSheet_KEY_Date = "Date";
    private static final String ExitSheet_KEY_VehicleNumber = "VehicleNumber";
    private static final String ExitSheet_KEY_VehicleDriverName = "VehicleDriverName";
    private static final String ExitSheet_KEY_IsDONE = "IsDone";
    private static final String ExitSheet_KEY_PdfPath = "PdfPath";
    private static final String ExitSheet_KEY_IsSend = "IsSend";
    private static final String ExitSheet_KEY_LogisticsCompany = "LogisticsCompany";

    //TABLE_ExitSheetPackage
    private static final String ExitSheetPackage_KEY_ID = "Id";
    private static final String ExitSheetPackage_KEY_ExitSheetId = "ExitSheetId";
    private static final String ExitSheetPackage_KEY_DeliveryNumber = "DeliveryNumber";
    private static final String ExitSheetPackage_KEY_InvoiceBarcode = "InvoiceBarcode";
    private static final String ExitSheetPackage_KEY_IsSend = "IsSend";

    //TABLE_ExitSheetPackageGoods
    private static final String ExitSheetPackageGoods_KEY_ID = "Id";
    private static final String ExitSheetPackageGoods_KEY_ExitSheetPackageId = "ExitSheetPackageId";
    private static final String ExitSheetPackageGoods_KEY_GoodsId = "GoodsId";
    private static final String ExitSheetPackageGoods_KEY_IsSend = "IsSend";

    //TABLE_EntranceSheet
    private static final String EntranceSheet_KEY_ID = "Id";
    private static final String EntranceSheet_KEY_Date = "Date";
    private static final String EntranceSheet_KEY_UserName = "UserName";
    private static final String EntranceSheet_KEY_Description = "Description";
    private static final String EntranceSheet_KEY_IsDONE = "IsDone";
    private static final String EntranceSheet_KEY_PdfPath = "PdfPath";
    private static final String EntranceSheet_KEY_IsSend = "IsSend";
    private static final String EntranceSheet_KEY_LogisticsCompany = "LogisticsCompany";

    //TABLE_EntranceSheetPackage
    private static final String EntranceSheetPackage_KEY_ID = "Id";
    private static final String EntranceSheetPackage_KEY_EntranceSheetId = "EntranceSheetId";
    private static final String EntranceSheetPackage_KEY_PackageNumber = "PackageNumber";
    private static final String EntranceSheetPackage_KEY_IsSend = "IsSend";

    //TABLE_EntranceSheetPackageItem
    private static final String EntranceSheetPackageItem_KEY_ID = "Id";
    private static final String EntranceSheetPackageItem_KEY_EntranceSheetPackageId = "EntranceSheetPackageId";
    private static final String EntranceSheetPackageItem_KEY_GoodsId = "GoodsId";
    private static final String EntranceSheetPackageItem_KEY_IsSend = "IsSend";

    //TABLE_ReturnSheet
    private static final String ReturnSheet_KEY_ID = "Id";
    private static final String ReturnSheet_KEY_Date = "Date";
    private static final String ReturnSheet_KEY_ReceiverName = "ReceiverName";
    private static final String ReturnSheet_KEY_VehicleDriverName = "VehicleDriverName";
    private static final String ReturnSheet_KEY_VehicleNumber = "VehicleNumber";
    private static final String ReturnSheet_KEY_PostBarcode = "PostBarcode";
    private static final String ReturnSheet_KEY_IsDONE = "IsDone";
    private static final String ReturnSheet_KEY_PdfPath = "PdfPath";
    private static final String ReturnSheet_KEY_IsSend = "IsSend";
    private static final String ReturnSheet_KEY_LogisticsCompany = "LogisticsCompany";
    int id = 1;

    //TABLE_ReturnSheetImage
    private static final String ReturnSheetImage_KEY_ID = "Id";
    private static final String ReturnSheetImage_KEY_ReturnSheetId = "ReturnSheetId";
    private static final String ReturnSheetImage_KEY_ImageBinary = "ImageBinary";
    private static final String ReturnSheetImage_KEY_IsSend = "IsSend";

    //TABLE_ReturnSheetPackage
    private static final String ReturnSheetPackage_KEY_ID = "Id";
    private static final String ReturnSheetPackage_KEY_ReturnSheetId = "ReturnSheetId";
    private static final String ReturnSheetPackage_KEY_OrderBarcode = "OrderBarcode";
    private static final String ReturnSheetPackage_KEY_IsSend = "IsSend";

    //TABLE_ReturnSheetPackageItem
    private static final String ReturnSheetPackageItem_KEY_ID = "Id";
    private static final String ReturnSheetPackageItem_KEY_ReturnSheetPackageId = "ReturnSheetPackageId";
    private static final String ReturnSheetPackageItem_KEY_GoodsId = "GoodsId";
    private static final String ReturnSheetPackageItem_KEY_Comment = "Comment";
    private static final String ReturnSheetPackageItem_KEY_IsSend = "IsSend";
    //TABLE_SettingScanner
    private static final String SettingScanner_KEY_ID = "Id";
    private static final String SettingScanner_KEY_NameStore = "NameStor";
    private static final String SettingScanner_KEY_NameScanner = "NameScanner";
    private static final String SettingScanner_KEY_IsSend = "IsSend";

    // query string for CREATE_Language_TABLE
    private static final String CREATE_Language_TABLE = "CREATE TABLE " + TABLE_Language + "("
            + Language_KEY_ID + " INTEGER NOT NULL , "
            + Language_KEY_Language + " TEXT NOT NULL "
            + ")";
    // query string for CREATE_AppText_TABLE
    private static final String CREATE_AppText_TABLE = "CREATE TABLE " + TABLE_AppText + "("
            + AppText_KEY_ID + " INTEGER NOT NULL , "
            + AppText_KEY_ParentId + " INTEGER , "
            + AppText_KEY_LanguageId + " INTEGER NOT NULL , "
            + AppText_KEY_Text + " TEXT NOT NULL"
            + ")";
    // query string for CREATE_Goods_TABLE
    private static final String CREATE_Goods_TABLE = "CREATE TABLE " + TABLE_Goods + "("
            + Goods_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
            + Goods_KEY_NAME + " TEXT NOT NULL , "
            + Goods_KEY_BARCODE + " TEXT NOT NULL , "
            + Goods_KEY_IsSend + " INTEGER NOT NULL  "
            + ")";
    // query string for CREATE_CountingSheet_TABLE
    private static final String CREATE_CountingSheet_TABLE = "CREATE TABLE " + TABLE_CountingSheet + "("
            + CountingSheet_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + CountingSheet_KEY_CounterName + " TEXT NOT NULL , "
            + CountingSheet_KEY_CountingDate + " TEXT NOT NULL ,"
            + CountingSheet_KEY_IsDONE + " INTEGER NOT NULL ,"
            + CountingSheet_KEY_PdfPath + " TEXT , "
            + CountingSheet_KEY_IsSend + " INTEGER NOT NULL ,"
            + CountingSheet_KEY_Company+ " TEXT NOT NULL "
            + ")";

    // query string for CREATE_CountingSheetItem_TABLE
    private static final String CREATE_CountingSheetItem_TABLE = "CREATE TABLE " + TABLE_CountingSheetItem + "("
            + CountingSheetItem_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + CountingSheetItem_KEY_CountingSheetId + " INTEGER NOT NULL , "
            + CountingSheetItem_KEY_GoodsId + " INTEGER NOT NULL ,"
            + CountingSheetItem_KEY_Number + " INTEGER NOT NULL ,"
            + CountingSheetItem_KEY_IsDONE + " INTEGER NOT NULL , "
            + CountingSheetItem_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_ExitSheet_TABLE
    private static final String CREATE_ExitSheet_TABLE = "CREATE TABLE " + TABLE_ExitSheet + "("
            + ExitSheet_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ExitSheet_KEY_Date + " TEXT NOT NULL , "
            + ExitSheet_KEY_VehicleNumber + " TEXT NOT NULL ,"
            + ExitSheet_KEY_VehicleDriverName + " TEXT NOT NULL ,"
            + ExitSheet_KEY_IsDONE + " INTEGER NOT NULL ,"
            + ExitSheet_KEY_PdfPath + " TEXT , "
            + ExitSheet_KEY_IsSend + " INTEGER NOT NULL ,"
            + ExitSheet_KEY_LogisticsCompany + " TEXT "
            + ")";


    // query string for CREATE_ExitSheetPackage_TABLE
    private static final String CREATE_ExitSheetPackage_TABLE = "CREATE TABLE " + TABLE_ExitSheetPackage + "("
            + ExitSheetPackage_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ExitSheetPackage_KEY_ExitSheetId + " INTEGER NOT NULL , "
            + ExitSheetPackage_KEY_DeliveryNumber + " TEXT NOT NULL ,"
            + ExitSheetPackage_KEY_InvoiceBarcode + " TEXT , "
            + ExitSheetPackage_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_ExitSheetPackageGoods_TABLE
    private static final String CREATE_ExitSheetPackageGoods_TABLE = "CREATE TABLE " + TABLE_ExitSheetPackageGoods + "("
            + ExitSheetPackageGoods_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ExitSheetPackageGoods_KEY_ExitSheetPackageId + " INTEGER NOT NULL , "
            + ExitSheetPackageGoods_KEY_GoodsId + " INTEGER NOT NULL , "
            + ExitSheetPackageGoods_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_EntranceSheet_TABLE
    private static final String CREATE_EntranceSheet_TABLE = "CREATE TABLE " + TABLE_EntranceSheet + "("
            + EntranceSheet_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + EntranceSheet_KEY_Date + " TEXT NOT NULL , "
            + EntranceSheet_KEY_UserName + " TEXT NOT NULL ,"
            + EntranceSheet_KEY_Description + " TEXT ,"
            + EntranceSheet_KEY_IsDONE + " INTEGER NOT NULL ,"
            + EntranceSheet_KEY_PdfPath + " TEXT , "
            + EntranceSheet_KEY_IsSend + " INTEGER NOT NULL ,"
            + EntranceSheet_KEY_LogisticsCompany + " TEXT  "
            + ")";


    // query string for CREATE_EntranceSheetPackage_TABLE
    private static final String CREATE_EntranceSheetPackage_TABLE = "CREATE TABLE " + TABLE_EntranceSheetPackage + "("
            + EntranceSheetPackage_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + EntranceSheetPackage_KEY_EntranceSheetId + " INTEGER NOT NULL , "
            + EntranceSheetPackage_KEY_PackageNumber + " TEXT NOT NULL , "
            + EntranceSheetPackage_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_EntranceSheetPackageItem_TABLE
    private static final String CREATE_EntranceSheetPackageItem_TABLE = "CREATE TABLE " + TABLE_EntranceSheetPackageItem + "("
            + EntranceSheetPackageItem_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + EntranceSheetPackageItem_KEY_EntranceSheetPackageId + " INTEGER NOT NULL , "
            + EntranceSheetPackageItem_KEY_GoodsId + " INTEGER NOT NULL , "
            + EntranceSheetPackageItem_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_ReturnSheet_TABLE
    private static final String CREATE_ReturnSheet_TABLE = "CREATE TABLE " + TABLE_ReturnSheet + "("
            + ReturnSheet_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ReturnSheet_KEY_Date + " TEXT NOT NULL , "
            + ReturnSheet_KEY_ReceiverName + " TEXT NOT NULL ,"
            + ReturnSheet_KEY_VehicleDriverName + " TEXT NOT NULL ,"
            + ReturnSheet_KEY_VehicleNumber + " TEXT NOT NULL,"
            + ReturnSheet_KEY_PostBarcode + " TEXT NOT NULL,"
            + ReturnSheet_KEY_IsDONE + " INTEGER NOT NULL ,"
            + ReturnSheet_KEY_PdfPath + " TEXT , "
            + ReturnSheet_KEY_IsSend + " INTEGER NOT NULL ,"
            + ReturnSheet_KEY_LogisticsCompany+ " TEXT NOT NULL "
            + ")";


    // query string for CREATE_ReturnSheetImage_TABLE
    private static final String CREATE_ReturnSheetImage_TABLE = "CREATE TABLE " + TABLE_ReturnSheetImage + "("
            + ReturnSheetImage_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ReturnSheetImage_KEY_ReturnSheetId + " INTEGER NOT NULL , "
            + ReturnSheetImage_KEY_ImageBinary + " TEXT NOT NULL , "
            + ReturnSheetImage_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_ReturnSheetPackage_TABLE
    private static final String CREATE_ReturnSheetPackage_TABLE = "CREATE TABLE " + TABLE_ReturnSheetPackage + "("
            + ReturnSheetPackage_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ReturnSheetPackage_KEY_ReturnSheetId + " INTEGER NOT NULL , "
            + ReturnSheetPackage_KEY_OrderBarcode + " TEXT NOT NULL , "
            + ReturnSheetPackage_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    // query string for CREATE_ReturnSheetPackageItem_TABLE
    private static final String CREATE_ReturnSheetPackageItem_TABLE = "CREATE TABLE " + TABLE_ReturnSheetPackageItem + "("
            + ReturnSheetPackageItem_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + ReturnSheetPackageItem_KEY_ReturnSheetPackageId + " INTEGER NOT NULL , "
            + ReturnSheetPackageItem_KEY_GoodsId + " INTEGER NOT NULL ,"
            + ReturnSheetPackageItem_KEY_Comment + " TEXT , "
            + ReturnSheetPackageItem_KEY_IsSend + " INTEGER NOT NULL "
            + ")";
    // query string for CREATE_SettingScanner_TABLE
    private static final String CREATE_SettingScanner_TABLE = "CREATE TABLE " + TABLE_SettingScanner + "("
            + SettingScanner_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + SettingScanner_KEY_NameStore+ " TEXT , "
            + SettingScanner_KEY_NameScanner + " TEXT , "
            + SettingScanner_KEY_IsSend + " INTEGER NOT NULL "
            + ")";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_Language_TABLE);
        db.execSQL(CREATE_AppText_TABLE);
        db.execSQL(CREATE_Goods_TABLE);
        db.execSQL(CREATE_CountingSheet_TABLE);
        db.execSQL(CREATE_CountingSheetItem_TABLE);
        db.execSQL(CREATE_ExitSheet_TABLE);
        db.execSQL(CREATE_ExitSheetPackage_TABLE);
        db.execSQL(CREATE_ExitSheetPackageGoods_TABLE);
        db.execSQL(CREATE_EntranceSheet_TABLE);
        db.execSQL(CREATE_EntranceSheetPackage_TABLE);
        db.execSQL(CREATE_EntranceSheetPackageItem_TABLE);
        db.execSQL(CREATE_ReturnSheet_TABLE);
        db.execSQL(CREATE_ReturnSheetImage_TABLE);
        db.execSQL(CREATE_ReturnSheetPackage_TABLE);
        db.execSQL(CREATE_ReturnSheetPackageItem_TABLE);
        db.execSQL(CREATE_SettingScanner_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Language);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AppText);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Goods);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CountingSheet);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CountingSheetItem);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ExitSheet);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ExitSheetPackage);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ExitSheetPackageGoods);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EntranceSheet);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EntranceSheetPackage);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EntranceSheetPackageItem);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheet);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheetImage);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheetPackage);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheetPackageItem);
        onCreate(db);
    }


    /////////// Language
    public long addLanguage(Language language) {
        SQLiteDatabase db0 = this.getWritableDatabase();
        db0.execSQL("DELETE FROM " + TABLE_Language + " WHERE " + Language_KEY_ID + " = " + language.Id);
        db0.close();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Language_KEY_ID, language.Id);
        values.put(Language_KEY_Language, language.language);
        long id = db.insert(TABLE_Language, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<Language> getAllLanguage() {
        ArrayList<Language> languages = new ArrayList<Language>();
        String selectQuery = "SELECT  * FROM " + TABLE_Language + " ORDER BY " + Language_KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Language language = new Language(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1)
                );

                languages.add(language);
            } while (cursor.moveToNext());
        }

        db.close();

        return languages;
    }


    /////////// AppText

    public long addEnText(AppText appText) {

        SQLiteDatabase db0 = this.getWritableDatabase();
        db0.execSQL("DELETE FROM " + TABLE_AppText + " WHERE " + AppText_KEY_ID + " = " + appText.Id);
        db0.close();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppText_KEY_ID, appText.Id);
        values.put(AppText_KEY_ParentId, appText.parentId);
        values.put(AppText_KEY_LanguageId, appText.languageId);
        values.put(AppText_KEY_Text, appText.text);
        long id = db.insert(TABLE_AppText, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public long addDuText(AppText appText) {

        SQLiteDatabase db0 = this.getWritableDatabase();
        db0.execSQL("DELETE FROM " + TABLE_AppText + " WHERE " + AppText_KEY_ID + " = " + appText.Id);
        db0.close();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppText_KEY_ID, appText.Id);
        values.put(AppText_KEY_ParentId, appText.parentId);
        values.put(AppText_KEY_LanguageId, appText.languageId);
        values.put(AppText_KEY_Text, appText.text);
        long id = db.insert(TABLE_AppText, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public long addAppText(AppText appText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppText_KEY_ID, appText.Id);
        values.put(AppText_KEY_ParentId, appText.parentId);
        values.put(AppText_KEY_LanguageId, appText.languageId);
        values.put(AppText_KEY_Text, appText.text);
        long id = db.insert(TABLE_AppText, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public AppText getAppText(int parentId, int languageId) {
        ArrayList<AppText> AppTextList = new ArrayList<AppText>();

        if (languageId == 1) {
            String selectQuery = "SELECT  * FROM " + TABLE_AppText + " WHERE " + AppText_KEY_ID + " = " + parentId;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    AppText appText = new AppText(
                            Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
                            Integer.parseInt(cursor.getString(2)),
                            cursor.getString(3));

                    AppTextList.add(appText);
                } while (cursor.moveToNext());
            }

            db.close();
            if (AppTextList.size() > 0) {
                AppText appText = AppTextList.get(0);
                return appText;
            } else {
                return null;
            }
        } else {

            String selectQuery = "SELECT  * FROM " + TABLE_AppText + " WHERE " + AppText_KEY_ParentId + " = " + parentId + " AND " + AppText_KEY_LanguageId + " = " + languageId;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    AppText appText = new AppText(
                            Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
                            Integer.parseInt(cursor.getString(2)),
                            cursor.getString(3));

                    AppTextList.add(appText);
                } while (cursor.moveToNext());
            }

            db.close();
            if (AppTextList.size() > 0) {
                AppText appText = AppTextList.get(0);
                return appText;
            } else {
                return null;
            }
        }


    }

    public ArrayList<String[]> getAllEnText(String languageName) {
        ArrayList<String[]> AppTextList = new ArrayList<String[]>();

        String selectQuery = "SELECT  * FROM " + TABLE_AppText + " WHERE " + AppText_KEY_LanguageId + " = " + 1 + " ORDER BY " + AppText_KEY_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String[] enExcelText0 = new String[]{"index", "english", languageName + ""};
        AppTextList.add(enExcelText0);
        if (cursor.moveToFirst()) {
            do {
                String[] enExcelText = new String[]{
                        (cursor.getString(0)),
                        cursor.getString(3), ""};

                AppTextList.add(enExcelText);
            } while (cursor.moveToNext());
        }

        db.close();
        return AppTextList;
    }

    public void deleteAllImportedText() {
        SQLiteDatabase db0 = this.getWritableDatabase();
        db0.execSQL("DELETE FROM " + TABLE_Language + " WHERE " + Language_KEY_ID + " != " + 1);
        db0.close();
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.execSQL("DELETE FROM " + TABLE_AppText + " WHERE " + AppText_KEY_LanguageId + " != " + 1);
        db1.close();
    }


    /////////// Goods
    public long addGoods(Goods goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Goods_KEY_NAME, goods.Name);
        values.put(Goods_KEY_BARCODE, goods.Barocde);
        values.put(Goods_KEY_IsSend, goods.IsSend);
        long id = db.insert(TABLE_Goods, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public Goods getGoods(int Id) {
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        String selectQuery = "SELECT  * FROM " + TABLE_Goods + " WHERE " + Goods_KEY_ID + " = " + Id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)));

                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        db.close();
        Goods goods = goodsList.get(0);
        return goods;
    }

    public Goods getGoodsWithBarcode(String Barcode) {
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
//        String selectQuery = "SELECT  * FROM " + TABLE_Goods + " WHERE "+ Goods_KEY_BARCODE+" = "+" '"+Barcode+"'";
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_Goods, new String[]{Goods_KEY_ID,
                        Goods_KEY_NAME, Goods_KEY_BARCODE, Goods_KEY_IsSend}, Goods_KEY_BARCODE + "=?",
                new String[]{Barcode}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)));

                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        db.close();
        if (goodsList.size() > 0) {
            Goods goods = goodsList.get(0);
            return goods;
        } else {
            Goods goods = new Goods(0, "", "", 0);
            return goods;
        }

    }

    public ArrayList<Goods> getAllGoods() {
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        String selectQuery = "SELECT  * FROM " + TABLE_Goods;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3))
                );

                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        db.close();

        return goodsList;
    }

    public void deleteGoods() {

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_Goods);
        database.execSQL(CREATE_Goods_TABLE);
        database.close();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Goods + " WHERE " + Goods_KEY_ID + " > 0");
        db.close();
    }

    public void updateGoods(Goods goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Goods_KEY_IsSend, goods.IsSend);
        db.update(TABLE_Goods, values, Goods_KEY_ID + " = " + goods.Id, null);
        db.close();
    }


    /////////// CountingSheet
    public long addCountingSheet(CountingSheet countingSheet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CountingSheet_KEY_CounterName, countingSheet.CounterName);
        values.put(CountingSheet_KEY_CountingDate, countingSheet.CountingDate);
        values.put(CountingSheet_KEY_IsDONE, countingSheet.IsDone);
        values.put(CountingSheet_KEY_PdfPath, countingSheet.PdfPath);
        values.put(CountingSheet_KEY_IsSend, countingSheet.IsSend);
        values.put(CountingSheet_KEY_Company, countingSheet.LogisticsCompany);
        long id = db.insert(TABLE_CountingSheet, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<CountingSheet> getAllCountingSheet() {
        ArrayList<CountingSheet> countingSheetList = new ArrayList<CountingSheet>();
        String selectQuery = "SELECT  * FROM " + TABLE_CountingSheet + " ORDER BY " + CountingSheet_KEY_IsDONE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CountingSheet countingSheet = new CountingSheet(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)),

                        cursor.getString(6)

                );

                countingSheetList.add(countingSheet);
            } while (cursor.moveToNext());
        }

        db.close();

        return countingSheetList;
    }

    public void updateCountingSheet(CountingSheet countingSheet) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CountingSheet_KEY_CounterName, countingSheet.CounterName);
        values.put(CountingSheet_KEY_CountingDate, countingSheet.CountingDate);
        values.put(CountingSheet_KEY_IsDONE, countingSheet.IsDone);
        values.put(CountingSheet_KEY_PdfPath, countingSheet.PdfPath);
        values.put(CountingSheet_KEY_IsSend, countingSheet.IsSend);
        values.put(CountingSheet_KEY_Company, countingSheet.LogisticsCompany);
        db.update(TABLE_CountingSheet, values, CountingSheet_KEY_ID + " = " + countingSheet.Id, null);

        db.close();
    }


    /////////// CountingSheetItem
    public long addCountingSheetItem(CountingSheetItem countingSheetItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CountingSheetItem_KEY_CountingSheetId, countingSheetItem.CountingSheetId);
        values.put(CountingSheetItem_KEY_GoodsId, countingSheetItem.GoodsId);
        values.put(CountingSheetItem_KEY_Number, countingSheetItem.Number);
        values.put(CountingSheetItem_KEY_IsDONE, countingSheetItem.IsDone);
        values.put(CountingSheetItem_KEY_IsSend, countingSheetItem.IsSend);
        long id = db.insert(TABLE_CountingSheetItem, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<CountingSheetItem> getAllCountingSheetItem(int countingSheetId) {
        ArrayList<CountingSheetItem> countingSheetItemList = new ArrayList<CountingSheetItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_CountingSheetItem + " WHERE " + CountingSheetItem_KEY_CountingSheetId + " = " + countingSheetId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CountingSheetItem countingSheetItem = new CountingSheetItem(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5))
                );

                countingSheetItemList.add(countingSheetItem);
            } while (cursor.moveToNext());
        }

        db.close();

        return countingSheetItemList;
    }

    public void deleteCountingSheetItem(CountingSheetItem countingSheetItem) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CountingSheetItem + " WHERE " + CountingSheetItem_KEY_ID + " = " + countingSheetItem.Id);
        db.close();
    }

    public void updateCountingSheetItem(CountingSheetItem countingSheetItem) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CountingSheetItem_KEY_CountingSheetId, countingSheetItem.CountingSheetId);
        values.put(CountingSheetItem_KEY_GoodsId, countingSheetItem.GoodsId);
        values.put(CountingSheetItem_KEY_Number, countingSheetItem.Number);
        values.put(CountingSheetItem_KEY_IsDONE, countingSheetItem.IsDone);
        values.put(CountingSheetItem_KEY_IsSend, countingSheetItem.IsSend);
        db.update(TABLE_CountingSheetItem, values, CountingSheetItem_KEY_ID + " = " + countingSheetItem.Id, null);

        db.close();
    }


    /////////// ExitSheet
    public long addExitSheet(ExitSheet exitSheet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExitSheet_KEY_Date, exitSheet.Date);
        values.put(ExitSheet_KEY_VehicleNumber, exitSheet.VehicleNumber);
        values.put(ExitSheet_KEY_VehicleDriverName, exitSheet.VehicleDriverName);
        values.put(ExitSheet_KEY_IsDONE, exitSheet.IsDone);
        values.put(ExitSheet_KEY_PdfPath, exitSheet.PdfPath);
        values.put(ExitSheet_KEY_IsSend, exitSheet.IsSend);
        values.put(ExitSheet_KEY_LogisticsCompany, exitSheet.LogisticsCompany);
        long id = db.insert(TABLE_ExitSheet, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ExitSheet> getAllExitSheet() {
        ArrayList<ExitSheet> exitSheetList = new ArrayList<ExitSheet>();
        String selectQuery = "SELECT  * FROM " + TABLE_ExitSheet + " ORDER BY " + ExitSheet_KEY_IsDONE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ExitSheet exitSheet = new ExitSheet(
                        Integer.parseInt(cursor.getString(0)),
                        (cursor.getString(1)),
                        (cursor.getString(2)),
                        (cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),
                        Integer.parseInt(cursor.getString(6))
                        ,cursor.getString(7)
                );

                exitSheetList.add(exitSheet);
            } while (cursor.moveToNext());
        }

        db.close();

        return exitSheetList;
    }

    public void updateExitSheet(ExitSheet exitSheet) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExitSheet_KEY_Date, exitSheet.Date);
        values.put(ExitSheet_KEY_VehicleNumber, exitSheet.VehicleNumber);
        values.put(ExitSheet_KEY_VehicleDriverName, exitSheet.VehicleDriverName);
        values.put(ExitSheet_KEY_IsDONE, exitSheet.IsDone);
        values.put(ExitSheet_KEY_PdfPath, exitSheet.PdfPath);
        values.put(ExitSheet_KEY_IsSend, exitSheet.IsSend);
        values.put(ExitSheet_KEY_LogisticsCompany, exitSheet.LogisticsCompany);
        db.update(TABLE_ExitSheet, values, ExitSheet_KEY_ID + " = " + exitSheet.Id, null);

        db.close();
    }
    public void settingScanner(SettingData settingData) {
        Log.e("NameStore",settingData.NameStore);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SettingScanner_KEY_NameScanner, settingData.NameScanner);
        values.put(SettingScanner_KEY_NameStore, settingData.NameStore);
        values.put(SettingScanner_KEY_IsSend, settingData.IsSend);

        long id = db.insert(TABLE_SettingScanner, null, values);

        db.close();

    }

    /////////// ExitSheetPackage
    public long addExitSheetPackage(ExitSheetPackage exitSheetPackage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExitSheetPackage_KEY_ExitSheetId, exitSheetPackage.ExitSheetId);
        values.put(ExitSheetPackage_KEY_DeliveryNumber, exitSheetPackage.DeliveryNumber);
        values.put(ExitSheetPackage_KEY_InvoiceBarcode, exitSheetPackage.InvoiceBarcode);
        values.put(ExitSheetPackage_KEY_IsSend, exitSheetPackage.IsSend);
        long id = db.insert(TABLE_ExitSheetPackage, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ExitSheetPackage> getAllExitSheetPackage(int Id) {
        ArrayList<ExitSheetPackage> exitSheetPackageList = new ArrayList<ExitSheetPackage>();
        String selectQuery = "SELECT  * FROM " + TABLE_ExitSheetPackage + " WHERE " + ExitSheetPackage_KEY_ExitSheetId + " = " + Id;
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ExitSheetPackage exitSheetPackage = new ExitSheetPackage(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        (cursor.getString(2)),
                        (cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4))
                );

                exitSheetPackageList.add(exitSheetPackage);
            } while (cursor.moveToNext());
        }

        db.close();

        return exitSheetPackageList;
    }

    public void deleteExitSheetPackage(ExitSheetPackage exitSheetPackage) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ExitSheetPackage + " WHERE " + ExitSheetPackage_KEY_ID + " = " + exitSheetPackage.Id);
        db.close();
    }

    public void updateExitSheetPackage(ExitSheetPackage exitSheetPackage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExitSheetPackage_KEY_IsSend, exitSheetPackage.IsSend);
        db.update(TABLE_ExitSheetPackage, values, ExitSheetPackage_KEY_ID + " = " + exitSheetPackage.Id, null);
        db.close();
    }

    ////////// ExitSheetPackageGoods
    public long addExitSheetPackageGoods(ExitSheetPackageGoods exitSheetPackageGoods) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExitSheetPackageGoods_KEY_ExitSheetPackageId, exitSheetPackageGoods.ExitSheetPackageId);
        values.put(ExitSheetPackageGoods_KEY_GoodsId, exitSheetPackageGoods.GoodsId);
        values.put(ExitSheetPackageGoods_KEY_IsSend, exitSheetPackageGoods.IsSend);

        long id = db.insert(TABLE_ExitSheetPackageGoods, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ExitSheetPackageGoods> getAllExitSheetPackageGoods(int packageId) {
        ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsList = new ArrayList<ExitSheetPackageGoods>();
        String selectQuery = "SELECT  * FROM " + TABLE_ExitSheetPackageGoods + " WHERE " + ExitSheetPackageGoods_KEY_ExitSheetPackageId + " = " + packageId;
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ExitSheetPackageGoods exitSheetPackageGoods = new ExitSheetPackageGoods(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );

                exitSheetPackageGoodsList.add(exitSheetPackageGoods);
            } while (cursor.moveToNext());
        }

        db.close();

        return exitSheetPackageGoodsList;
    }

    public void updateExitSheetPackageGoods(ExitSheetPackageGoods exitSheetPackageGoods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExitSheetPackageGoods_KEY_IsSend, exitSheetPackageGoods.IsSend);
        db.update(TABLE_ExitSheetPackageGoods, values, ExitSheetPackage_KEY_ID + " = " + exitSheetPackageGoods.Id, null);
        db.close();
    }

    /////////// EntranceSheet
    public long addEntranceSheet(EntranceSheet entranceSheet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EntranceSheet_KEY_Date, entranceSheet.Date);
        values.put(EntranceSheet_KEY_UserName, entranceSheet.UserName);
        values.put(EntranceSheet_KEY_Description, entranceSheet.Description);
        values.put(EntranceSheet_KEY_IsDONE, entranceSheet.IsDone);
        values.put(EntranceSheet_KEY_PdfPath, entranceSheet.PdfPath);
        values.put(EntranceSheet_KEY_IsSend, entranceSheet.IsSend);
        values.put(EntranceSheet_KEY_LogisticsCompany, entranceSheet.LogisticsCompany);
        long id = db.insert(TABLE_EntranceSheet, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<EntranceSheet> getAllEntranceSheet() {
        ArrayList<EntranceSheet> entranceSheets = new ArrayList<EntranceSheet>();
        String selectQuery = "SELECT  * FROM " + TABLE_EntranceSheet + " ORDER BY " + EntranceSheet_KEY_IsDONE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                EntranceSheet entranceSheet = new EntranceSheet(
                        Integer.parseInt(cursor.getString(0)),
                        (cursor.getString(1)),
                        (cursor.getString(2)),
                        (cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),
                        Integer.parseInt(cursor.getString(6))
                        ,cursor.getString(7)

                );

                entranceSheets.add(entranceSheet);
            } while (cursor.moveToNext());
        }

        db.close();

        return entranceSheets;
    }

    public void updateEntranceSheet(EntranceSheet entranceSheet) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntranceSheet_KEY_Date, entranceSheet.Date);
        values.put(EntranceSheet_KEY_UserName, entranceSheet.UserName);
        values.put(EntranceSheet_KEY_Description, entranceSheet.Description);
        values.put(EntranceSheet_KEY_IsDONE, entranceSheet.IsDone);
        values.put(EntranceSheet_KEY_PdfPath, entranceSheet.PdfPath);
        values.put(EntranceSheet_KEY_IsSend, entranceSheet.IsSend);
        values.put(EntranceSheet_KEY_LogisticsCompany, entranceSheet.LogisticsCompany);
        db.update(TABLE_EntranceSheet, values, EntranceSheet_KEY_ID + " = " + entranceSheet.Id, null);

        db.close();
    }


    /////////// EntranceSheetPackage
    public long addEntranceSheetPackage(EntranceSheetPackage entranceSheetPackage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EntranceSheetPackage_KEY_EntranceSheetId, entranceSheetPackage.EntranceSheetId);
        values.put(EntranceSheetPackage_KEY_PackageNumber, entranceSheetPackage.PackageNumber);
        values.put(EntranceSheetPackage_KEY_IsSend, entranceSheetPackage.IsSend);

        long id = db.insert(TABLE_EntranceSheetPackage, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<EntranceSheetPackage> getAllEntranceSheetPackage(int Id) {
        ArrayList<EntranceSheetPackage> entranceSheetPackageList = new ArrayList<EntranceSheetPackage>();
        String selectQuery = "SELECT  * FROM " + TABLE_EntranceSheetPackage + " WHERE " + EntranceSheetPackage_KEY_EntranceSheetId + " = " + Id;
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                EntranceSheetPackage entranceSheetPackage = new EntranceSheetPackage(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        (cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );

                entranceSheetPackageList.add(entranceSheetPackage);
            } while (cursor.moveToNext());
        }

        db.close();

        return entranceSheetPackageList;
    }

    public void deleteEntranceSheetPackage(EntranceSheetPackage entranceSheetPackage) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EntranceSheetPackage + " WHERE " + EntranceSheetPackage_KEY_ID + " = " + entranceSheetPackage.Id);
        db.close();
    }

    public void updateEntranceSheetPackage(EntranceSheetPackage entranceSheetPackage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntranceSheetPackage_KEY_IsSend, entranceSheetPackage.IsSend);
        db.update(TABLE_EntranceSheetPackage, values, EntranceSheetPackage_KEY_ID + " = " + entranceSheetPackage.Id, null);
        db.close();
    }

    ////////// EntranceSheetPackageItem
    public long addEntranceSheetPackageItem(EntranceSheetPackageItem entranceSheetPackageItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EntranceSheetPackageItem_KEY_EntranceSheetPackageId, entranceSheetPackageItem.EntranceSheetPackageId);
        values.put(EntranceSheetPackageItem_KEY_GoodsId, entranceSheetPackageItem.GoodsId);
        values.put(EntranceSheetPackageItem_KEY_IsSend, entranceSheetPackageItem.IsSend);

        long id = db.insert(TABLE_EntranceSheetPackageItem, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<EntranceSheetPackageItem> getAllEntranceSheetPackageItems(int packageId) {
        ArrayList<EntranceSheetPackageItem> entranceSheetPackageItemList = new ArrayList<EntranceSheetPackageItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_EntranceSheetPackageItem + " WHERE " + EntranceSheetPackageItem_KEY_EntranceSheetPackageId + " = " + packageId;
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                EntranceSheetPackageItem entranceSheetPackageItem = new EntranceSheetPackageItem(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );

                entranceSheetPackageItemList.add(entranceSheetPackageItem);
            } while (cursor.moveToNext());
        }

        db.close();

        return entranceSheetPackageItemList;
    }

    public void updateEntranceSheetPackageItem(EntranceSheetPackageItem entranceSheetPackageItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntranceSheetPackageItem_KEY_IsSend, entranceSheetPackageItem.IsSend);
        db.update(TABLE_EntranceSheetPackageItem, values, EntranceSheetPackageItem_KEY_ID + " = " + entranceSheetPackageItem.Id, null);
        db.close();
    }

    /////////// ReturnSheet
    public long addReturnSheet(ReturnSheet returnSheet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ReturnSheet_KEY_Date, returnSheet.Date);
        values.put(ReturnSheet_KEY_ReceiverName, returnSheet.ReceiverName);
        values.put(ReturnSheet_KEY_VehicleDriverName, returnSheet.VehicleDriverName);
        values.put(ReturnSheet_KEY_VehicleNumber, returnSheet.VehicleNumber);
        values.put(ReturnSheet_KEY_PostBarcode, returnSheet.PostBarcode);
        values.put(ReturnSheet_KEY_IsDONE, returnSheet.IsDone);
        values.put(ReturnSheet_KEY_PdfPath, returnSheet.PdfPath);
        values.put(ReturnSheet_KEY_IsSend, returnSheet.IsSend);
        values.put(ReturnSheet_KEY_LogisticsCompany, returnSheet.LogisticsCompany);
        long id = db.insert(TABLE_ReturnSheet, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ReturnSheet> getAllReturnSheet() {
        ArrayList<ReturnSheet> returnSheets = new ArrayList<ReturnSheet>();
        String selectQuery = "SELECT  * FROM " + TABLE_ReturnSheet + " ORDER BY " + ReturnSheet_KEY_IsDONE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReturnSheet returnSheet = new ReturnSheet(
                        Integer.parseInt(cursor.getString(0)),
                        (cursor.getString(1)),
                        (cursor.getString(2)),
                        (cursor.getString(3)),
                        (cursor.getString(4)),
                        (cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)),
                        cursor.getString(7),
                        Integer.parseInt(cursor.getString(8))
                        ,cursor.getString(9)

                );

                returnSheets.add(returnSheet);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnSheets;
    }

    public void updateReturnSheet(ReturnSheet returnSheet) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReturnSheet_KEY_Date, returnSheet.Date);
        values.put(ReturnSheet_KEY_ReceiverName, returnSheet.ReceiverName);
        values.put(ReturnSheet_KEY_VehicleDriverName, returnSheet.VehicleDriverName);
        values.put(ReturnSheet_KEY_VehicleNumber, returnSheet.VehicleNumber);
        values.put(ReturnSheet_KEY_PostBarcode, returnSheet.PostBarcode);
        values.put(ReturnSheet_KEY_IsDONE, returnSheet.IsDone);
        values.put(ReturnSheet_KEY_PdfPath, returnSheet.PdfPath);
        values.put(ReturnSheet_KEY_IsSend, returnSheet.IsSend);
        values.put(ReturnSheet_KEY_LogisticsCompany, returnSheet.LogisticsCompany);
        db.update(TABLE_ReturnSheet, values, ReturnSheet_KEY_ID + " = " + returnSheet.Id, null);

        db.close();
    }


    /////////// ReturnSheetPackage
    public long addReturnSheetPackage(ReturnSheetPackage returnSheetPackage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ReturnSheetPackage_KEY_ReturnSheetId, returnSheetPackage.ReturnSheetId);
        values.put(ReturnSheetPackage_KEY_OrderBarcode, returnSheetPackage.OrderBarcode);
        values.put(ReturnSheetPackage_KEY_IsSend, returnSheetPackage.IsSend);
        long id = db.insert(TABLE_ReturnSheetPackage, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ReturnSheetPackage> getAllReturnSheetPackage(int Id) {
        ArrayList<ReturnSheetPackage> returnSheetPackageList = new ArrayList<ReturnSheetPackage>();
        String selectQuery = "SELECT  * FROM " + TABLE_ReturnSheetPackage + " WHERE " + ReturnSheetPackage_KEY_ReturnSheetId + " = " + Id;
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReturnSheetPackage returnSheetPackage = new ReturnSheetPackage(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        (cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );

                returnSheetPackageList.add(returnSheetPackage);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnSheetPackageList;
    }

    public void deleteReturnSheetPackage(ReturnSheetPackage returnSheetPackage) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ReturnSheetPackage + " WHERE " + ReturnSheetPackage_KEY_ID + " = " + returnSheetPackage.Id);
        db.close();
    }

    public void updateReturnSheetPackage(ReturnSheetPackage returnSheetPackage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReturnSheetPackage_KEY_IsSend, returnSheetPackage.IsSend);
        db.update(TABLE_ReturnSheetPackage, values, ReturnSheetPackage_KEY_ID + " = " + returnSheetPackage.Id, null);
        db.close();
    }

    /////////// ReturnSheetImage
    public long addReturnSheetImage(ReturnSheetImage returnSheetImage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ReturnSheetImage_KEY_ReturnSheetId, returnSheetImage.ReturnSheetId);
        values.put(ReturnSheetImage_KEY_ImageBinary, returnSheetImage.ImageBinary);
        values.put(ReturnSheetImage_KEY_IsSend, returnSheetImage.IsSend);
        long id = db.insert(TABLE_ReturnSheetImage, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ReturnSheetImage> getAllReturnSheetImage(int Id) {
        ArrayList<ReturnSheetImage> returnSheetImageList = new ArrayList<ReturnSheetImage>();
        String selectQuery = "SELECT  * FROM " + TABLE_ReturnSheetImage + " WHERE " + ReturnSheetImage_KEY_ReturnSheetId + " = " + Id;
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReturnSheetImage returnSheetImage = new ReturnSheetImage(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        (cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3))
                );

                returnSheetImageList.add(returnSheetImage);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnSheetImageList;
    }

    public void deleteReturnSheetImage(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ReturnSheetImage + " WHERE " + ReturnSheetImage_KEY_ID + " = " + id);
        db.close();
    }

    public void deleteAllReturnSheetImage(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ReturnSheetImage + " WHERE " + ReturnSheetImage_KEY_ReturnSheetId + " = " + id);
        db.close();
    }

    public void updateReturnSheetImage(ReturnSheetImage returnSheetImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReturnSheetImage_KEY_IsSend, returnSheetImage.IsSend);
        db.update(TABLE_ReturnSheetImage, values, ReturnSheetImage_KEY_ID + " = " + returnSheetImage.Id, null);
        db.close();
    }

    ////////// ReturnSheetPackageItem
    public long addReturnSheetPackageItem(ReturnSheetPackageItem returnSheetPackageItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ReturnSheetPackageItem_KEY_ReturnSheetPackageId, returnSheetPackageItem.ReturnSheetPackageId);
        values.put(ReturnSheetPackageItem_KEY_GoodsId, returnSheetPackageItem.GoodsId);
        values.put(ReturnSheetPackageItem_KEY_Comment, returnSheetPackageItem.Commnet);
        values.put(ReturnSheetPackageItem_KEY_IsSend, returnSheetPackageItem.IsSend);

        long id = db.insert(TABLE_ReturnSheetPackageItem, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public ArrayList<ReturnSheetPackageItem> getAllReturnSheetPackageItems(int packageId) {
        ArrayList<ReturnSheetPackageItem> returnSheetPackageItemList = new ArrayList<ReturnSheetPackageItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_ReturnSheetPackageItem + " WHERE " + ReturnSheetPackageItem_KEY_ReturnSheetPackageId + " = " + packageId;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReturnSheetPackageItem returnSheetPackageItem = new ReturnSheetPackageItem(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4))
                );

                returnSheetPackageItemList.add(returnSheetPackageItem);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnSheetPackageItemList;
    }

    public void updateReturnSheetPackageItem(ReturnSheetPackageItem returnSheetPackageItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReturnSheetPackageItem_KEY_IsSend, returnSheetPackageItem.IsSend);
        db.update(TABLE_ReturnSheetPackageItem, values, ReturnSheetPackageItem_KEY_ID + " = " + returnSheetPackageItem.Id, null);
        db.close();
    }


    ///////// update data after upload
    public void updateDataAfterUpload(AllDataLastIndex allDataLastIndex) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery1 = " UPDATE " + TABLE_Goods + " SET " + Goods_KEY_IsSend + " = 1 " + " WHERE " + Goods_KEY_ID + " <= " + allDataLastIndex.LastIndexGoods;
        String selectQuery2 = " UPDATE " + TABLE_CountingSheet + " SET " + CountingSheet_KEY_IsSend + " = 1" + " WHERE " + CountingSheet_KEY_ID + " <= " + allDataLastIndex.LastIndexCountingSheet;
        String selectQuery3 = " UPDATE " + TABLE_CountingSheetItem + " SET " + CountingSheetItem_KEY_IsSend + " = 1" + " WHERE " + CountingSheetItem_KEY_ID + " <= " + allDataLastIndex.LastIndexCountingSheetItem;
        String selectQuery4 = " UPDATE " + TABLE_ExitSheet + " SET " + ExitSheet_KEY_IsSend + " = 1" + " WHERE " + ExitSheet_KEY_ID + " <= " + allDataLastIndex.LastIndexExitSheet;
        String selectQuery5 = " UPDATE " + TABLE_ExitSheetPackage + " SET " + ExitSheetPackage_KEY_IsSend + " = 1" + " WHERE " + ExitSheetPackage_KEY_ID + " <= " + allDataLastIndex.LastIndexExitSheetPackage;
        String selectQuery6 = " UPDATE " + TABLE_ExitSheetPackageGoods + " SET " + ExitSheetPackageGoods_KEY_IsSend + " = 1" + " WHERE " + ExitSheetPackageGoods_KEY_ID + " <= " + allDataLastIndex.LastIndexExitSheetPackageGoods;
        String selectQuery7 = " UPDATE " + TABLE_EntranceSheet + " SET " + EntranceSheet_KEY_IsSend + " = 1" + " WHERE " + EntranceSheet_KEY_ID + " <= " + allDataLastIndex.LastIndexEntranceSheet;
        String selectQuery8 = " UPDATE " + TABLE_EntranceSheetPackage + " SET " + EntranceSheetPackage_KEY_IsSend + " = 1" + " WHERE " + EntranceSheetPackage_KEY_ID + " <= " + allDataLastIndex.LastIndexEntranceSheetPackage;
        String selectQuery9 = " UPDATE " + TABLE_EntranceSheetPackageItem + " SET " + EntranceSheetPackageItem_KEY_IsSend + " = 1" + " WHERE " + EntranceSheetPackageItem_KEY_ID + " <= " + allDataLastIndex.LastIndexEntranceSheetPackageItem;
        String selectQuery10 = " UPDATE " + TABLE_ReturnSheet + " SET " + ReturnSheet_KEY_IsSend + " = 1" + " WHERE " + ReturnSheet_KEY_ID + " <= " + allDataLastIndex.LastIndexReturnSheet;
        String selectQuery11 = " UPDATE " + TABLE_ReturnSheetPackage + " SET " + ReturnSheetPackage_KEY_IsSend + " = 1" + " WHERE " + ReturnSheetPackage_KEY_ID + " <= " + allDataLastIndex.LastIndexReturnSheetPackage;
        String selectQuery12 = " UPDATE " + TABLE_ReturnSheetImage + " SET " + ReturnSheetImage_KEY_IsSend + " = 1" + " WHERE " + ReturnSheetImage_KEY_ID + " <= " + allDataLastIndex.LastIndexReturnSheetImage;
        String selectQuery13 = " UPDATE " + TABLE_ReturnSheetPackageItem + " SET " + ReturnSheetPackageItem_KEY_IsSend + " = 1" + " WHERE " + ReturnSheetPackageItem_KEY_ID + " <= " + allDataLastIndex.LastIndexReturnSheetPackageItem;

        db.execSQL(selectQuery1);
        db.execSQL(selectQuery2);
        db.execSQL(selectQuery3);
        db.execSQL(selectQuery4);
        db.execSQL(selectQuery5);
        db.execSQL(selectQuery6);
        db.execSQL(selectQuery7);
        db.execSQL(selectQuery8);
        db.execSQL(selectQuery9);
        db.execSQL(selectQuery10);
        db.execSQL(selectQuery11);
        db.execSQL(selectQuery12);
        db.execSQL(selectQuery13);
        db.close();

    }


    ////////// Get not send Data
    public AllData getAllUnsendData() {


        String selectQuery1 = "SELECT  * FROM " + TABLE_Goods + " ORDER BY " + Goods_KEY_ID;
        String selectQuery2 = "SELECT  * FROM " + TABLE_CountingSheet + " WHERE " + CountingSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + CountingSheet_KEY_ID;
        String selectQuery2_plus = "SELECT  * FROM " + TABLE_CountingSheet + " ORDER BY " + CountingSheet_KEY_ID;

        String selectQuery3 = "SELECT  * FROM " + TABLE_CountingSheetItem + " WHERE " + CountingSheetItem_KEY_IsSend + " = " + 0 + " ORDER BY " + CountingSheetItem_KEY_ID;
        String selectQuery4 = "SELECT  * FROM " + TABLE_ExitSheet + " WHERE " + ExitSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + ExitSheet_KEY_ID;
        String selectQuery5 = "SELECT  * FROM " + TABLE_ExitSheetPackage + " WHERE " + ExitSheetPackage_KEY_IsSend + " = " + 0 + " ORDER BY " + ExitSheetPackage_KEY_ID;
        String selectQuery6 = "SELECT  * FROM " + TABLE_ExitSheetPackageGoods + " WHERE " + ExitSheetPackageGoods_KEY_IsSend + " = " + 0 + " ORDER BY " + ExitSheetPackageGoods_KEY_ID;
        String selectQuery7 = "SELECT  * FROM " + TABLE_EntranceSheet + " WHERE " + EntranceSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + EntranceSheet_KEY_ID;
        String selectQuery8 = "SELECT  * FROM " + TABLE_EntranceSheetPackage + " WHERE " + EntranceSheetPackage_KEY_IsSend + " = " + 0 + " ORDER BY " + EntranceSheetPackage_KEY_ID;
        String selectQuery9 = "SELECT  * FROM " + TABLE_EntranceSheetPackageItem + " WHERE " + EntranceSheetPackageItem_KEY_IsSend + " = " + 0 + " ORDER BY " + EntranceSheetPackageItem_KEY_ID;
        String selectQuery10 = "SELECT  * FROM " + TABLE_ReturnSheet + " WHERE " + ReturnSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheet_KEY_ID;
        String selectQuery11 = "SELECT  * FROM " + TABLE_ReturnSheetPackage + " WHERE " + ReturnSheetPackage_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheetPackage_KEY_ID;
        String selectQuery12 = "SELECT  * FROM " + TABLE_ReturnSheetImage + " WHERE " + ReturnSheetImage_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheetImage_KEY_ID;
        String selectQuery13 = "SELECT  * FROM " + TABLE_ReturnSheetPackageItem + " WHERE " + ReturnSheetPackageItem_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheetPackageItem_KEY_ID;
        String selectQuery14 = "SELECT  * FROM " + TABLE_SettingScanner  + " ORDER BY " + SettingScanner_KEY_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        Cursor cursor2 = db.rawQuery(selectQuery2, null);
        Cursor cursor2_plus = db.rawQuery(selectQuery2_plus, null);
        Cursor cursor3 = db.rawQuery(selectQuery3, null);
        Cursor cursor4 = db.rawQuery(selectQuery4, null);
        Cursor cursor5 = db.rawQuery(selectQuery5, null);
        Cursor cursor6 = db.rawQuery(selectQuery6, null);
        Cursor cursor7 = db.rawQuery(selectQuery7, null);
        Cursor cursor8 = db.rawQuery(selectQuery8, null);
        Cursor cursor9 = db.rawQuery(selectQuery9, null);
        Cursor cursor10 = db.rawQuery(selectQuery10, null);
        Cursor cursor11 = db.rawQuery(selectQuery11, null);
        Cursor cursor12 = db.rawQuery(selectQuery12, null);
        Cursor cursor13 = db.rawQuery(selectQuery13, null);
        Cursor cursor14 = db.rawQuery(selectQuery14, null);
        SettingData settingData = null;
        if (cursor14.moveToFirst()) {
            settingData  = new SettingData( Integer.parseInt(cursor14.getString(0)),cursor14.getString(1),
                    cursor14.getString(2),Integer.parseInt(cursor14.getString(3)));
        }
        ArrayList<Goods> goodsArray = new ArrayList<>();
        if (cursor1.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor1.getString(0)),
                        cursor1.getString(1),
                        cursor1.getString(2),
                        Integer.parseInt(cursor1.getString(3))
                );


                goodsArray.add(goods);
            } while (cursor1.moveToNext());
        }
        int goodsNameC = 0;
        ArrayList<ArrayList<CountingSheetItem>> coItem = new ArrayList<>();
        ArrayList<CountingSheetItem> coItemplus = new ArrayList<>();
        ArrayList<CountingSheetItem> countingSheetItemArray = new ArrayList<>();
        CountingSheetItem countingSheetItem;
        int isItemCo = 1;
        if (cursor3.moveToFirst()) {
            isItemCo = Integer.parseInt(cursor3.getString(1));

            do {
                String goods = " ";
                if (goodsArray.size() > 0 && Integer.parseInt(cursor3.getString(0)) - 1 < goodsArray.size()) {
                    Log.e("GOOODS", goodsArray.get(0).Name);
                    goods = goodsArray.get(Integer.parseInt(cursor3.getString(2)) - 1).Name;
                    countingSheetItem = new CountingSheetItem(
                            Integer.parseInt(cursor3.getString(0)),
                            Integer.parseInt(cursor3.getString(1)),
                            Integer.parseInt(cursor3.getString(2)),
                            Integer.parseInt(cursor3.getString(3)),
                            Integer.parseInt(cursor3.getString(4)),
                            Integer.parseInt(cursor3.getString(5)),
                            goods
                    );
                } else {
                    countingSheetItem = new CountingSheetItem(
                            Integer.parseInt(cursor3.getString(0)),
                            Integer.parseInt(cursor3.getString(1)),
                            Integer.parseInt(cursor3.getString(2)),
                            Integer.parseInt(cursor3.getString(3)),
                            Integer.parseInt(cursor3.getString(4)),
                            Integer.parseInt(cursor3.getString(5))
                    );

                }

                if (Integer.parseInt(cursor3.getString(1)) ==
                        isItemCo) {
                    coItemplus.add(countingSheetItem);
                } else {
                    isItemCo = Integer.parseInt(cursor3.getString(1));
                    coItem.add(coItemplus);
                    coItemplus = new ArrayList<>();
                    coItemplus.clear();
                    coItemplus.add(countingSheetItem);
                }
                countingSheetItemArray.add(countingSheetItem);
            } while (cursor3.moveToNext());
        }
        coItem.add(coItemplus);


        ArrayList<CountingSheet> countingSheetArray = new ArrayList<>();
        CountingSheet countingSheet;
        int id = 0;

        if (cursor2.moveToFirst()) {
            do {
                countingSheet = new CountingSheet(
                        Integer.parseInt(cursor2.getString(0)),
                        cursor2.getString(1),
                        cursor2.getString(2),
                        Integer.parseInt(cursor2.getString(3)),
                        cursor2.getString(4),
                        Integer.parseInt(cursor2.getString(5)),
                        coItem.get(id),
                        cursor2.getString(6)

                );
                if(id<coItem.size()-1)
                id++;
                Log.e("cursor2",cursor2.getString(6));


                countingSheetArray.add(countingSheet);
            } while (cursor2.moveToNext());
        }

        ArrayList<ArrayList<ExitSheetPackageGoods>> exGodItem = new ArrayList<>();
        ArrayList<ExitSheetPackageGoods> exGodItemplus = new ArrayList<>();
        ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsArray = new ArrayList<>();
        ExitSheetPackageGoods exitSheetPackageGoods;
        int isItemExGod = 1;
        if (cursor6.moveToFirst()) {
            isItemExGod = Integer.parseInt(cursor6.getString(1));

            do {
                String goods = " ";
                if (goodsArray.size() > 0 && Integer.parseInt(cursor6.getString(0)) - 1 < goodsArray.size()) {
                    goods = goodsArray.get(Integer.parseInt(cursor6.getString(2)) - 1).Name;
                    exitSheetPackageGoods = new ExitSheetPackageGoods(
                            Integer.parseInt(cursor6.getString(0)),
                            Integer.parseInt(cursor6.getString(1)),
                            Integer.parseInt(cursor6.getString(2)),
                            Integer.parseInt(cursor6.getString(3)),
                            goods
                    );
                } else {
                    exitSheetPackageGoods = new ExitSheetPackageGoods(
                            Integer.parseInt(cursor6.getString(0)),
                            Integer.parseInt(cursor6.getString(1)),
                            Integer.parseInt(cursor6.getString(2)),
                            Integer.parseInt(cursor6.getString(3))

                    );
                }
                if (Integer.parseInt(cursor6.getString(1)) ==
                        isItemExGod) {
                    exGodItemplus.add(exitSheetPackageGoods);
                } else {
                    isItemExGod = Integer.parseInt(cursor6.getString(1));
                    exGodItem.add(exGodItemplus);
                    exGodItemplus = new ArrayList<>();
                    exGodItemplus.clear();
                    exGodItemplus.add(exitSheetPackageGoods);
                }

                exitSheetPackageGoodsArray.add(exitSheetPackageGoods);
            } while (cursor6.moveToNext());
        }
        exGodItem.add(exGodItemplus);

        ArrayList<ArrayList<ExitSheetPackage>> expckItem = new ArrayList<>();
        ArrayList<ExitSheetPackage> expckItemplus = new ArrayList<>();
        ArrayList<ExitSheetPackage> exitSheetPackageArray = new ArrayList<>();
        ExitSheetPackage exitSheetPackage;
        int idExitSheetPackageArray=0;


        int isItemExPck = 1;
        if (cursor5.moveToFirst()) {
            isItemExPck = Integer.parseInt(cursor5.getString(1));

            do {
                exitSheetPackage = new ExitSheetPackage(
                        Integer.parseInt(cursor5.getString(0)),
                        Integer.parseInt(cursor5.getString(1)),
                        (cursor5.getString(2)),
                        (cursor5.getString(3)),
                        Integer.parseInt(cursor5.getString(4)),
                        exGodItem.get(idExitSheetPackageArray)
                );
                if (Integer.parseInt(cursor5.getString(1)) ==
                        isItemExPck) {
                    expckItemplus.add(exitSheetPackage);
                } else {
                    isItemExPck = Integer.parseInt(cursor5.getString(1));
                    expckItem.add(expckItemplus);
                    expckItemplus = new ArrayList<>();
                    expckItemplus.clear();
                    expckItemplus.add(exitSheetPackage);
                }
                idExitSheetPackageArray++;

                exitSheetPackageArray.add(exitSheetPackage);
            } while (cursor5.moveToNext());
        }
        expckItem.add(expckItemplus);

        int idExitSheetArray=0;

        ArrayList<ExitSheet> exitSheetArray = new ArrayList<>();
        if (cursor4.moveToFirst()) {
            do {
                ExitSheet exitSheet = new ExitSheet(
                        Integer.parseInt(cursor4.getString(0)),
                        (cursor4.getString(1)),
                        (cursor4.getString(2)),
                        (cursor4.getString(3)),
                        Integer.parseInt(cursor4.getString(4)),
                        cursor4.getString(5),
                        Integer.parseInt(cursor4.getString(6)),
                        expckItem.get(idExitSheetArray)
                        ,cursor4.getString(7)


                );
                if (idExitSheetArray<expckItem.size()-1)
                    idExitSheetArray++;

                exitSheetArray.add(exitSheet);
            } while (cursor4.moveToNext());
        }

        int isItemEn = 1;
        ArrayList<ArrayList<EntranceSheetPackageItem>> enItem = new ArrayList<>();
        ArrayList<EntranceSheetPackageItem> enItemplus = new ArrayList<>();
        ArrayList<EntranceSheetPackageItem> entranceSheetPackageItemArray = new ArrayList<>();
        EntranceSheetPackageItem entranceSheetPackageItem;
        if (cursor9.moveToFirst()) {
            isItemEn = Integer.parseInt(cursor9.getString(1));
            do {
                String goods = " ";
                Log.e("INDEX=>", Integer.parseInt(cursor9.getString(0)) - 1 + "");

                Log.e("SIZE===>", goodsArray.size() + "");
                if (goodsArray.size() > 0 && Integer.parseInt(cursor9.getString(0)) - 1 < goodsArray.size()) {
                    goods = goodsArray.get(Integer.parseInt(cursor9.getString(2)) - 1).Name;
                    entranceSheetPackageItem = new EntranceSheetPackageItem(
                            Integer.parseInt(cursor9.getString(0)),
                            Integer.parseInt(cursor9.getString(1)),
                            Integer.parseInt(cursor9.getString(2)),
                            Integer.parseInt(cursor9.getString(3)),
                            goods

                    );
                } else {
                    entranceSheetPackageItem = new EntranceSheetPackageItem(
                            Integer.parseInt(cursor9.getString(0)),
                            Integer.parseInt(cursor9.getString(1)),
                            Integer.parseInt(cursor9.getString(2)),
                            Integer.parseInt(cursor9.getString(3))


                    );
                }
                if (Integer.parseInt(cursor9.getString(1)) ==
                        isItemEn) {
                    enItemplus.add(entranceSheetPackageItem);
                } else {
                    isItemEn = Integer.parseInt(cursor9.getString(1));
                    enItem.add(enItemplus);
                    enItemplus = new ArrayList<>();
                    enItemplus.clear();
                    enItemplus.add(entranceSheetPackageItem);
                }


                entranceSheetPackageItemArray.add(entranceSheetPackageItem);
            } while (cursor9.moveToNext());
        }
        enItem.add(enItemplus);

//        int ind=Integer.parseInt(cursor9.getString(0))-1;
//        if(entranceSheetPackageItemArray.get(ind).EntranceSheetPackageId
//                ==Integer.parseInt(cursor8.getString(1))){
//
//        }
        int is = 1;
        ArrayList<ArrayList<EntranceSheetPackage>> en = new ArrayList<>();
        ArrayList<EntranceSheetPackage> enplus = new ArrayList<>();
        ArrayList<EntranceSheetPackage> entranceSheetPackageArray = new ArrayList<>();
        int idEntranceSheetPackageArray=0;

        if (cursor8.moveToFirst()) {
            is = Integer.parseInt(cursor8.getString(1));

            do {

                Log.e("EN+++en", Integer.parseInt(cursor8.getString(1)) + "");

                EntranceSheetPackage entranceSheetPackage = new EntranceSheetPackage(
                        Integer.parseInt(cursor8.getString(0)),
                        Integer.parseInt(cursor8.getString(1)),
                        (cursor8.getString(2)),
                        Integer.parseInt(cursor8.getString(3)),
                        enItem.get(idEntranceSheetPackageArray)
                );
                Log.e("EN+++en", "===>" + Integer.parseInt(cursor8.getString(1)));

                if (Integer.parseInt(cursor8.getString(1)) ==
                        is) {
                    enplus.add(entranceSheetPackage);
                } else {
                    is = Integer.parseInt(cursor8.getString(1));
                    en.add(enplus);
                    enplus = new ArrayList<>();
                    enplus.clear();
                    enplus.add(entranceSheetPackage);
                }
                idEntranceSheetPackageArray++;
                entranceSheetPackageArray.add(entranceSheetPackage);
            } while (cursor8.moveToNext());

        }
        en.add(enplus);

        int idEntranceSheet = 0;

        ArrayList<EntranceSheet> entranceSheetArray = new ArrayList<>();
        if (cursor7.moveToFirst()) {
            do {
                EntranceSheet entranceSheet = new EntranceSheet(
                        Integer.parseInt(cursor7.getString(0)),
                        (cursor7.getString(1)),
                        (cursor7.getString(2)),
                        (cursor7.getString(3)),
                        Integer.parseInt(cursor7.getString(4)),
                        cursor7.getString(5),
                        Integer.parseInt(cursor7.getString(6)),
                        en.get(idEntranceSheet)
                        ,cursor7.getString(7)


                );
                if (idEntranceSheet<en.size()-1)
                    idEntranceSheet++;

                entranceSheetArray.add(entranceSheet);
            } while (cursor7.moveToNext());
        }
        int isItemReImage = 1;
        ArrayList<ArrayList<ReturnSheetImage>> reImageItem = new ArrayList<>();
        ArrayList<ReturnSheetImage> reImageItemplus = new ArrayList<>();
        ArrayList<ReturnSheetImage> returnSheetImageArray = new ArrayList<>();
        ReturnSheetImage returnSheetImage;

        if (cursor12.moveToFirst()) {
            isItemReImage = Integer.parseInt(cursor12.getString(1));

            do {
                returnSheetImage = new ReturnSheetImage(
                        Integer.parseInt(cursor12.getString(0)),
                        Integer.parseInt(cursor12.getString(1)),
                        (cursor12.getString(2)),
                        Integer.parseInt(cursor12.getString(3))
                );
                if (Integer.parseInt(cursor12.getString(1)) ==
                        isItemReImage) {
                    reImageItemplus.add(returnSheetImage);
                } else {
                    isItemReImage = Integer.parseInt(cursor12.getString(1));
                    reImageItem.add(reImageItemplus);
                    reImageItemplus = new ArrayList<>();
                    reImageItemplus.clear();
                    reImageItemplus.add(returnSheetImage);
                }
                returnSheetImageArray.add(returnSheetImage);
            } while (cursor12.moveToNext());
        }
        reImageItem.add(reImageItemplus);
        int isItemRe = 1;
        ArrayList<ArrayList<ReturnSheetPackageItem>> reItem = new ArrayList<>();
        ArrayList<ReturnSheetPackageItem> reItemplus = new ArrayList<>();
        ArrayList<ReturnSheetPackageItem> returnSheetPackageItemArray = new ArrayList<>();
        ReturnSheetPackageItem returnSheetPackageItem;
        if (cursor13.moveToFirst()) {
            isItemRe = Integer.parseInt(cursor13.getString(1));

            do {
                String goods = " ";
                if (goodsArray.size() > 0 && Integer.parseInt(cursor13.getString(0)) - 1 < goodsArray.size()) {
                    goods = goodsArray.get(Integer.parseInt(cursor13.getString(2)) - 1).Name;
                    returnSheetPackageItem = new ReturnSheetPackageItem(
                            Integer.parseInt(cursor13.getString(0)),
                            Integer.parseInt(cursor13.getString(1)),
                            Integer.parseInt(cursor13.getString(2)),
                            cursor13.getString(3),
                            Integer.parseInt(cursor13.getString(4)),
                            goods

                    );
                } else {
                    returnSheetPackageItem = new ReturnSheetPackageItem(
                            Integer.parseInt(cursor13.getString(0)),
                            Integer.parseInt(cursor13.getString(1)),
                            Integer.parseInt(cursor13.getString(2)),
                            cursor13.getString(3),
                            Integer.parseInt(cursor13.getString(4))
                    );
                }
                if (Integer.parseInt(cursor13.getString(1)) ==
                        isItemRe) {
                    reItemplus.add(returnSheetPackageItem);
                } else {
                    isItemRe = Integer.parseInt(cursor13.getString(1));
                    reItem.add(reItemplus);
                    reItemplus = new ArrayList<>();
                    reItemplus.clear();
                    reItemplus.add(returnSheetPackageItem);
                }
                returnSheetPackageItemArray.add(returnSheetPackageItem);
            } while (cursor13.moveToNext());
        }
        reItem.add(reItemplus);


        int isRe = 1;
        ArrayList<ArrayList<ReturnSheetPackage>> re = new ArrayList<>();
        ArrayList<ReturnSheetPackage> replus = new ArrayList<>();
        ReturnSheetPackage returnSheetPackage;
        ArrayList<ReturnSheetPackage> returnSheetPackageArray = new ArrayList<>();
        int idreturnSheetPackage = 0;

        if (cursor11.moveToFirst()) {
            isRe = Integer.parseInt(cursor11.getString(1));

            do {
                returnSheetPackage = new ReturnSheetPackage(
                        Integer.parseInt(cursor11.getString(0)),
                        Integer.parseInt(cursor11.getString(1)),
                        (cursor11.getString(2)),
                        Integer.parseInt(cursor11.getString(3)),
                        reItem.get(idreturnSheetPackage)

                );
                if (Integer.parseInt(cursor11.getString(1)) ==
                        isRe) {
                    replus.add(returnSheetPackage);
                } else {
                    isRe = Integer.parseInt(cursor11.getString(1));
                    re.add(replus);
                    replus = new ArrayList<>();
                    replus.clear();
                    replus.add(returnSheetPackage);
                }

                idreturnSheetPackage++;
                returnSheetPackageArray.add(returnSheetPackage);
            } while (cursor11.moveToNext());
        }
        re.add(replus);


        int idReturnSheetArray = 0;

        ArrayList<ReturnSheet> returnSheetArray = new ArrayList<>();
        if (cursor10.moveToFirst()) {
            do {
                ReturnSheet returnSheet = new ReturnSheet(
                        Integer.parseInt(cursor10.getString(0)),
                        (cursor10.getString(1)),
                        (cursor10.getString(2)),
                        (cursor10.getString(3)),
                        (cursor10.getString(4)),
                        (cursor10.getString(5)),
                        Integer.parseInt(cursor10.getString(6)),
                        cursor10.getString(7),
                        Integer.parseInt(cursor10.getString(8)),
                        re.get(idReturnSheetArray),
                        reImageItem.get(idReturnSheetArray)
                        ,cursor10.getString(9)

                );
                if (idReturnSheetArray<reImageItem.size()-1)
                    idReturnSheetArray++;


                returnSheetArray.add(returnSheet);
            } while (cursor10.moveToNext());
        }


        AllData allData = new AllData(goodsArray, countingSheetArray, countingSheetItemArray, exitSheetArray,
                exitSheetPackageArray, exitSheetPackageGoodsArray, entranceSheetArray, entranceSheetPackageArray,
                entranceSheetPackageItemArray, returnSheetArray, returnSheetPackageArray, returnSheetImageArray, returnSheetPackageItemArray,settingData);

        db.close();

        return allData;


    }

    public AllDataString getAllUnsendDataJson() {

        String selectQuery1 = "SELECT  * FROM " + TABLE_Goods + " WHERE " + Goods_KEY_IsSend + " = " + 0 + " ORDER BY " + Goods_KEY_ID;
        String selectQuery2 = "SELECT  * FROM " + TABLE_CountingSheet + " WHERE " + CountingSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + CountingSheet_KEY_ID;
        String selectQuery3 = "SELECT  * FROM " + TABLE_CountingSheetItem + " WHERE " + CountingSheetItem_KEY_IsSend + " = " + 0 + " ORDER BY " + CountingSheetItem_KEY_ID;
        String selectQuery4 = "SELECT  * FROM " + TABLE_ExitSheet + " WHERE " + ExitSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + ExitSheet_KEY_ID;
        String selectQuery5 = "SELECT  * FROM " + TABLE_ExitSheetPackage + " WHERE " + ExitSheetPackage_KEY_IsSend + " = " + 0 + " ORDER BY " + ExitSheetPackage_KEY_ID;
        String selectQuery6 = "SELECT  * FROM " + TABLE_ExitSheetPackageGoods + " WHERE " + ExitSheetPackageGoods_KEY_IsSend + " = " + 0 + " ORDER BY " + ExitSheetPackageGoods_KEY_ID;
        String selectQuery7 = "SELECT  * FROM " + TABLE_EntranceSheet + " WHERE " + EntranceSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + EntranceSheet_KEY_ID;
        String selectQuery8 = "SELECT  * FROM " + TABLE_EntranceSheetPackage + " WHERE " + EntranceSheetPackage_KEY_IsSend + " = " + 0 + " ORDER BY " + EntranceSheetPackage_KEY_ID;
        String selectQuery9 = "SELECT  * FROM " + TABLE_EntranceSheetPackageItem + " WHERE " + EntranceSheetPackageItem_KEY_IsSend + " = " + 0 + " ORDER BY " + EntranceSheetPackageItem_KEY_ID;
        String selectQuery10 = "SELECT  * FROM " + TABLE_ReturnSheet + " WHERE " + ReturnSheet_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheet_KEY_ID;
        String selectQuery11 = "SELECT  * FROM " + TABLE_ReturnSheetPackage + " WHERE " + ReturnSheetPackage_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheetPackage_KEY_ID;
        String selectQuery12 = "SELECT  * FROM " + TABLE_ReturnSheetImage + " WHERE " + ReturnSheetImage_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheetImage_KEY_ID;
        String selectQuery13 = "SELECT  * FROM " + TABLE_ReturnSheetPackageItem + " WHERE " + ReturnSheetPackageItem_KEY_IsSend + " = " + 0 + " ORDER BY " + ReturnSheetPackageItem_KEY_ID;
        String selectQuery14 = "SELECT  * FROM " + TABLE_SettingScanner  + " ORDER BY " + SettingScanner_KEY_ID;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        Cursor cursor2 = db.rawQuery(selectQuery2, null);
        Cursor cursor3 = db.rawQuery(selectQuery3, null);
        Cursor cursor4 = db.rawQuery(selectQuery4, null);
        Cursor cursor5 = db.rawQuery(selectQuery5, null);
        Cursor cursor6 = db.rawQuery(selectQuery6, null);
        Cursor cursor7 = db.rawQuery(selectQuery7, null);
        Cursor cursor8 = db.rawQuery(selectQuery8, null);
        Cursor cursor9 = db.rawQuery(selectQuery9, null);
        Cursor cursor10 = db.rawQuery(selectQuery10, null);
        Cursor cursor11 = db.rawQuery(selectQuery11, null);
        Cursor cursor12 = db.rawQuery(selectQuery12, null);
        Cursor cursor13 = db.rawQuery(selectQuery13, null);
        Cursor cursor14 = db.rawQuery(selectQuery14, null);

        SettingData settingData = null;
        if (cursor14.moveToFirst()) {
            settingData  = new SettingData( Integer.parseInt(cursor14.getString(0)),cursor14.getString(1),
                    cursor14.getString(2),Integer.parseInt(cursor14.getString(3)));
        }
        ArrayList<Map<String, String>> goodsArray = new ArrayList<>();
        if (cursor1.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor1.getString(0)),
                        cursor1.getString(1),
                        cursor1.getString(2),
                        Integer.parseInt(cursor1.getString(3))
                );

                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", goods.Id + "");
                param.put("Barocde", goods.Barocde + "");
                param.put("Name", goods.Name + "");
                param.put("IsSend", goods.IsSend + "");
                goodsArray.add(param);
            } while (cursor1.moveToNext());
        }


        ArrayList<Map<String, String>> countingSheetArray = new ArrayList<>();
        if (cursor2.moveToFirst()) {
            do {
                CountingSheet countingSheet = new CountingSheet(
                        Integer.parseInt(cursor2.getString(0)),
                        cursor2.getString(1),
                        cursor2.getString(2),
                        Integer.parseInt(cursor2.getString(3)),
                        cursor2.getString(4),
                        Integer.parseInt(cursor2.getString(5))
                        ,
                        cursor2.getString(6)

                );

                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", countingSheet.Id + "");
                param.put("CounterName", countingSheet.CounterName + "");
                param.put("CountingDate", countingSheet.CountingDate + "");
                param.put("PdfPath", countingSheet.PdfPath + "");
                param.put("IsDone", countingSheet.IsDone + "");
                param.put("IsSend", countingSheet.IsSend + "");
                param.put("LogisticsCompany", countingSheet.LogisticsCompany + "");
                countingSheetArray.add(param);
            } while (cursor2.moveToNext());
        }


        ArrayList<Map<String, String>> countingSheetItemArray = new ArrayList<>();
        if (cursor3.moveToFirst()) {
            do {
                CountingSheetItem countingSheetItem = new CountingSheetItem(
                        Integer.parseInt(cursor3.getString(0)),
                        Integer.parseInt(cursor3.getString(1)),
                        Integer.parseInt(cursor3.getString(2)),
                        Integer.parseInt(cursor3.getString(3)),
                        Integer.parseInt(cursor3.getString(4)),
                        Integer.parseInt(cursor3.getString(5))

                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", countingSheetItem.Id + "");
                param.put("CountingSheetId", countingSheetItem.CountingSheetId + "");
                param.put("GoodsId", countingSheetItem.GoodsId + "");
                param.put("Number", countingSheetItem.Number + "");
                param.put("IsDone", countingSheetItem.IsDone + "");
                param.put("IsSend", countingSheetItem.IsSend + "");

                countingSheetItemArray.add(param);
            } while (cursor3.moveToNext());
        }

        ArrayList<Map<String, String>> exitSheetArray = new ArrayList<>();
        if (cursor4.moveToFirst()) {
            do {
                ExitSheet exitSheet = new ExitSheet(
                        Integer.parseInt(cursor4.getString(0)),
                        (cursor4.getString(1)),
                        (cursor4.getString(2)),
                        (cursor4.getString(3)),
                        Integer.parseInt(cursor4.getString(4)),
                        cursor4.getString(5),
                        Integer.parseInt(cursor4.getString(6))
                        ,cursor4.getString(7)

                );

                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", exitSheet.Id + "");
                param.put("VehicleNumber", exitSheet.VehicleNumber + "");
                param.put("VehicleDriverName", exitSheet.VehicleDriverName + "");
                param.put("PdfPath", exitSheet.PdfPath + "");
                param.put("Date", exitSheet.Date + "");
                param.put("IsDone", exitSheet.IsDone + "");
                param.put("IsSend", exitSheet.IsSend + "");
                param.put("LogisticsCompany", exitSheet.LogisticsCompany + "");
                exitSheetArray.add(param);
            } while (cursor4.moveToNext());
        }

        ArrayList<Map<String, String>> exitSheetPackageArray = new ArrayList<>();
        if (cursor5.moveToFirst()) {
            do {
                ExitSheetPackage exitSheetPackage = new ExitSheetPackage(
                        Integer.parseInt(cursor5.getString(0)),
                        Integer.parseInt(cursor5.getString(1)),
                        (cursor5.getString(2)),
                        (cursor5.getString(3)),
                        Integer.parseInt(cursor5.getString(4))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", exitSheetPackage.Id + "");
                param.put("ExitSheetId", exitSheetPackage.ExitSheetId + "");
                param.put("DeliveryNumber", exitSheetPackage.DeliveryNumber + "");
                param.put("InvoiceBarcode", exitSheetPackage.InvoiceBarcode + "");
                param.put("IsSend", exitSheetPackage.IsSend + "");
                exitSheetPackageArray.add(param);
            } while (cursor5.moveToNext());
        }

        ArrayList<Map<String, String>> exitSheetPackageGoodsArray = new ArrayList<>();
        if (cursor6.moveToFirst()) {
            do {
                ExitSheetPackageGoods exitSheetPackageGoods = new ExitSheetPackageGoods(
                        Integer.parseInt(cursor6.getString(0)),
                        Integer.parseInt(cursor6.getString(1)),
                        Integer.parseInt(cursor6.getString(2)),
                        Integer.parseInt(cursor6.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", exitSheetPackageGoods.Id + "");
                param.put("ExitSheetPackageId", exitSheetPackageGoods.ExitSheetPackageId + "");
                param.put("GoodsId", exitSheetPackageGoods.GoodsId + "");
                param.put("IsSend", exitSheetPackageGoods.IsSend + "");
                exitSheetPackageGoodsArray.add(param);
            } while (cursor6.moveToNext());
        }

        ArrayList<Map<String, String>> entranceSheetArray = new ArrayList<>();
        if (cursor7.moveToFirst()) {
            do {
                EntranceSheet entranceSheet = new EntranceSheet(
                        Integer.parseInt(cursor7.getString(0)),
                        (cursor7.getString(1)),
                        (cursor7.getString(2)),
                        (cursor7.getString(3)),
                        Integer.parseInt(cursor7.getString(4)),
                        cursor7.getString(5),
                        Integer.parseInt(cursor7.getString(6))
                        ,cursor7.getString(7)

                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", entranceSheet.Id + "");
                param.put("UserName", entranceSheet.UserName + "");
                param.put("Date", entranceSheet.Date + "");
                param.put("Description", entranceSheet.Description + "");
                param.put("PdfPath", entranceSheet.PdfPath + "");
                param.put("IsDone", entranceSheet.IsDone + "");
                param.put("IsSend", entranceSheet.IsSend + "");
                param.put("IsSend", entranceSheet.LogisticsCompany + "");
                entranceSheetArray.add(param);
            } while (cursor7.moveToNext());
        }


        ArrayList<Map<String, String>> entranceSheetPackageArray = new ArrayList<>();
        if (cursor8.moveToFirst()) {
            do {
                EntranceSheetPackage entranceSheetPackage = new EntranceSheetPackage(
                        Integer.parseInt(cursor8.getString(0)),
                        Integer.parseInt(cursor8.getString(1)),
                        (cursor8.getString(2)),
                        Integer.parseInt(cursor8.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", entranceSheetPackage.Id + "");
                param.put("EntranceSheetId", entranceSheetPackage.EntranceSheetId + "");
                param.put("PackageNumber", entranceSheetPackage.PackageNumber + "");
                param.put("IsSend", entranceSheetPackage.IsSend + "");
                entranceSheetPackageArray.add(param);
            } while (cursor8.moveToNext());
        }

        ArrayList<Map<String, String>> entranceSheetPackageItemArray = new ArrayList<>();
        if (cursor9.moveToFirst()) {
            do {
                EntranceSheetPackageItem entranceSheetPackageItem = new EntranceSheetPackageItem(
                        Integer.parseInt(cursor9.getString(0)),
                        Integer.parseInt(cursor9.getString(1)),
                        Integer.parseInt(cursor9.getString(2)),
                        Integer.parseInt(cursor9.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", entranceSheetPackageItem.Id + "");
                param.put("EntranceSheetPackageId", entranceSheetPackageItem.EntranceSheetPackageId + "");
                param.put("GoodsId", entranceSheetPackageItem.GoodsId + "");
                param.put("IsSend", entranceSheetPackageItem.IsSend + "");
                entranceSheetPackageItemArray.add(param);
            } while (cursor9.moveToNext());
        }


        ArrayList<Map<String, String>> returnSheetArray = new ArrayList<>();
        if (cursor10.moveToFirst()) {
            do {
                ReturnSheet returnSheet = new ReturnSheet(
                        Integer.parseInt(cursor10.getString(0)),
                        (cursor10.getString(1)),
                        (cursor10.getString(2)),
                        (cursor10.getString(3)),
                        (cursor10.getString(4)),
                        (cursor10.getString(5)),
                        Integer.parseInt(cursor10.getString(6)),
                        cursor10.getString(7),
                        Integer.parseInt(cursor10.getString(8))
                        ,cursor10.getString(9)

                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheet.Id + "");
                param.put("VehicleDriverName", returnSheet.VehicleDriverName + "");
                param.put("VehicleNumber", returnSheet.VehicleNumber + "");
                param.put("ReceiverName", returnSheet.ReceiverName + "");
                param.put("PostBarcode", returnSheet.PostBarcode + "");
                param.put("PdfPath", returnSheet.PdfPath + "");
                param.put("Date", returnSheet.Date + "");
                param.put("LogisticsCompany", returnSheet.LogisticsCompany + "");
                param.put("IsDone", returnSheet.IsDone + "");
                param.put("IsSend", returnSheet.IsSend + "");
                returnSheetArray.add(param);
            } while (cursor10.moveToNext());
        }

        ArrayList<Map<String, String>> returnSheetPackageArray = new ArrayList<>();
        if (cursor11.moveToFirst()) {
            do {
                ReturnSheetPackage returnSheetPackage = new ReturnSheetPackage(
                        Integer.parseInt(cursor11.getString(0)),
                        Integer.parseInt(cursor11.getString(1)),
                        (cursor11.getString(2)),
                        Integer.parseInt(cursor11.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheetPackage.Id + "");
                param.put("ReturnSheetId", returnSheetPackage.ReturnSheetId + "");
                param.put("OrderBarcode", returnSheetPackage.OrderBarcode + "");
                param.put("IsSend", returnSheetPackage.IsSend + "");
                returnSheetPackageArray.add(param);
            } while (cursor11.moveToNext());
        }

        ArrayList<Map<String, String>> returnSheetImageArray = new ArrayList<>();
        if (cursor12.moveToFirst()) {
            do {
                ReturnSheetImage returnSheetImage = new ReturnSheetImage(
                        Integer.parseInt(cursor12.getString(0)),
                        Integer.parseInt(cursor12.getString(1)),
                        (cursor12.getString(2)),
                        Integer.parseInt(cursor12.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheetImage.Id + "");
                param.put("ReturnSheetId", returnSheetImage.ReturnSheetId + "");
                param.put("Image", returnSheetImage.ImageBinary + "");
                param.put("IsSend", returnSheetImage.IsSend + "");
                returnSheetImageArray.add(param);
            } while (cursor12.moveToNext());
        }

        ArrayList<Map<String, String>> returnSheetPackageItemArray = new ArrayList<>();
        if (cursor13.moveToFirst()) {
            do {
                ReturnSheetPackageItem returnSheetPackageItem = new ReturnSheetPackageItem(
                        Integer.parseInt(cursor13.getString(0)),
                        Integer.parseInt(cursor13.getString(1)),
                        Integer.parseInt(cursor13.getString(2)),
                        cursor13.getString(3),
                        Integer.parseInt(cursor13.getString(4))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheetPackageItem.Id + "");
                param.put("ReturnSheetPackageId", returnSheetPackageItem.ReturnSheetPackageId + "");
                param.put("GoodsId", returnSheetPackageItem.GoodsId + "");
                param.put("Comment", returnSheetPackageItem.Commnet + "");
                param.put("IsSend", returnSheetPackageItem.IsSend + "");
                returnSheetPackageItemArray.add(param);
            } while (cursor13.moveToNext());
        }



        AllDataString allData = new AllDataString(goodsArray, countingSheetArray, countingSheetItemArray, exitSheetArray,
                exitSheetPackageArray, exitSheetPackageGoodsArray, entranceSheetArray, entranceSheetPackageArray,
                entranceSheetPackageItemArray, returnSheetArray, returnSheetPackageArray, returnSheetImageArray, returnSheetPackageItemArray,settingData);

        db.close();

        return allData;


    }


    public AllData getAllData() {

        String selectQuery1 = "SELECT  * FROM " + TABLE_Goods + " ORDER BY " + Goods_KEY_ID;
        String selectQuery2 = "SELECT  * FROM " + TABLE_CountingSheet + " ORDER BY " + CountingSheet_KEY_ID;
        String selectQuery3 = "SELECT  * FROM " + TABLE_CountingSheetItem + " ORDER BY " + CountingSheetItem_KEY_ID;
        String selectQuery4 = "SELECT  * FROM " + TABLE_ExitSheet + " ORDER BY " + ExitSheet_KEY_ID;
        String selectQuery5 = "SELECT  * FROM " + TABLE_ExitSheetPackage + " ORDER BY " + ExitSheetPackage_KEY_ID;
        String selectQuery6 = "SELECT  * FROM " + TABLE_ExitSheetPackageGoods + " ORDER BY " + ExitSheetPackageGoods_KEY_ID;
        String selectQuery7 = "SELECT  * FROM " + TABLE_EntranceSheet + " ORDER BY " + EntranceSheet_KEY_ID;
        String selectQuery8 = "SELECT  * FROM " + TABLE_EntranceSheetPackage + " ORDER BY " + EntranceSheetPackage_KEY_ID;
        String selectQuery9 = "SELECT  * FROM " + TABLE_EntranceSheetPackageItem + " ORDER BY " + EntranceSheetPackageItem_KEY_ID;
        String selectQuery10 = "SELECT  * FROM " + TABLE_ReturnSheet + " ORDER BY " + ReturnSheet_KEY_ID;
        String selectQuery11 = "SELECT  * FROM " + TABLE_ReturnSheetPackage + " ORDER BY " + ReturnSheetPackage_KEY_ID;
        String selectQuery12 = "SELECT  * FROM " + TABLE_ReturnSheetImage + " ORDER BY " + ReturnSheetImage_KEY_ID;
        String selectQuery13 = "SELECT  * FROM " + TABLE_ReturnSheetPackageItem + " ORDER BY " + ReturnSheetPackageItem_KEY_ID;
        String selectQuery14 = "SELECT  * FROM " + TABLE_SettingScanner  + " ORDER BY " + SettingScanner_KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        Cursor cursor2 = db.rawQuery(selectQuery2, null);
        Cursor cursor3 = db.rawQuery(selectQuery3, null);
        Cursor cursor4 = db.rawQuery(selectQuery4, null);
        Cursor cursor5 = db.rawQuery(selectQuery5, null);
        Cursor cursor6 = db.rawQuery(selectQuery6, null);
        Cursor cursor7 = db.rawQuery(selectQuery7, null);
        Cursor cursor8 = db.rawQuery(selectQuery8, null);
        Cursor cursor9 = db.rawQuery(selectQuery9, null);
        Cursor cursor10 = db.rawQuery(selectQuery10, null);
        Cursor cursor11 = db.rawQuery(selectQuery11, null);
        Cursor cursor12 = db.rawQuery(selectQuery12, null);
        Cursor cursor13 = db.rawQuery(selectQuery13, null);
        Cursor cursor14 = db.rawQuery(selectQuery14, null);


        int idReturnSheetArray = 0;
        int idReturnSheetArrayImage = 0;
        int idreturnSheetPackage = 0;
        int idcountingSheetArray = 0;
        int idExitSheetPackageArray=0;
        int idExitSheetArray=0;
        int idEntranceSheetPackageArray=0;
        int idEntranceSheetGod = 0;
        int idEntranceSheet = 0;

        ArrayList<Goods> goodsArray = new ArrayList<>();
        if (cursor1.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor1.getString(0)),
                        cursor1.getString(1),
                        cursor1.getString(2),
                        Integer.parseInt(cursor1.getString(3))
                );


                goodsArray.add(goods);
            } while (cursor1.moveToNext());
        }
        int goodsNameC = 0;
        ArrayList<ArrayList<CountingSheetItem>> coItem = new ArrayList<>();
        ArrayList<CountingSheetItem> coItemplus = new ArrayList<>();
        ArrayList<CountingSheetItem> countingSheetItemArray = new ArrayList<>();
        CountingSheetItem countingSheetItem;
        int isItemCo = 1;
        if (cursor3.moveToFirst()) {

            do {
                String goods = " ";
                if (goodsArray.size() > 0 && Integer.parseInt(cursor3.getString(0)) - 1 < goodsArray.size()) {
                    Log.e("GOOODS", goodsArray.get(0).Name);
                    goods = goodsArray.get(Integer.parseInt(cursor3.getString(2)) - 1).Name;
                    countingSheetItem = new CountingSheetItem(
                            Integer.parseInt(cursor3.getString(0)),
                            Integer.parseInt(cursor3.getString(1)),
                            Integer.parseInt(cursor3.getString(2)),
                            Integer.parseInt(cursor3.getString(3)),
                            Integer.parseInt(cursor3.getString(4)),
                            Integer.parseInt(cursor3.getString(5)),
                            goods
                    );
                } else {
                    countingSheetItem = new CountingSheetItem(
                            Integer.parseInt(cursor3.getString(0)),
                            Integer.parseInt(cursor3.getString(1)),
                            Integer.parseInt(cursor3.getString(2)),
                            Integer.parseInt(cursor3.getString(3)),
                            Integer.parseInt(cursor3.getString(4)),
                            Integer.parseInt(cursor3.getString(5))
                    );

                }

                if (Integer.parseInt(cursor3.getString(1)) ==
                        isItemCo) {
                    coItemplus.add(countingSheetItem);
                } else {
                    isItemCo = Integer.parseInt(cursor3.getString(1));
                    coItem.add(coItemplus);
                    coItemplus = new ArrayList<>();
                    coItemplus.clear();
                    coItemplus.add(countingSheetItem);
                }
                countingSheetItemArray.add(countingSheetItem);
            } while (cursor3.moveToNext());
        }
        coItem.add(coItemplus);


        ArrayList<CountingSheet> countingSheetArray = new ArrayList<>();
        CountingSheet countingSheet;
        if (cursor2.moveToFirst()) {
            do {
                countingSheet = new CountingSheet(
                        Integer.parseInt(cursor2.getString(0)),
                        cursor2.getString(1),
                        cursor2.getString(2),
                        Integer.parseInt(cursor2.getString(3)),
                        cursor2.getString(4),
                        Integer.parseInt(cursor2.getString(5)),
                        coItem.get(idcountingSheetArray)
                        ,
                        cursor2.getString(6)

                );
                if (idcountingSheetArray<coItem.size()-1)
                idcountingSheetArray++;
                countingSheetArray.add(countingSheet);
            } while (cursor2.moveToNext());
        }

        ArrayList<ArrayList<ExitSheetPackageGoods>> exGodItem = new ArrayList<>();
        ArrayList<ExitSheetPackageGoods> exGodItemplus = new ArrayList<>();
        ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsArray = new ArrayList<>();
        ExitSheetPackageGoods exitSheetPackageGoods;
        int isItemExGod = 1;
        if (cursor6.moveToFirst()) {

            do {
                String goods = " ";
                if (goodsArray.size() > 0 && Integer.parseInt(cursor6.getString(0)) - 1 < goodsArray.size()) {
                    goods = goodsArray.get(Integer.parseInt(cursor6.getString(2)) - 1).Name;
                    exitSheetPackageGoods = new ExitSheetPackageGoods(
                            Integer.parseInt(cursor6.getString(0)),
                            Integer.parseInt(cursor6.getString(1)),
                            Integer.parseInt(cursor6.getString(2)),
                            Integer.parseInt(cursor6.getString(3)),
                            goods
                    );
                } else {
                    exitSheetPackageGoods = new ExitSheetPackageGoods(
                            Integer.parseInt(cursor6.getString(0)),
                            Integer.parseInt(cursor6.getString(1)),
                            Integer.parseInt(cursor6.getString(2)),
                            Integer.parseInt(cursor6.getString(3))

                    );
                }
                if (Integer.parseInt(cursor6.getString(1)) ==
                        isItemExGod) {
                    exGodItemplus.add(exitSheetPackageGoods);
                } else {
                    isItemExGod = Integer.parseInt(cursor6.getString(1));
                    exGodItem.add(exGodItemplus);
                    exGodItemplus = new ArrayList<>();
                    exGodItemplus.clear();
                    exGodItemplus.add(exitSheetPackageGoods);
                }

                exitSheetPackageGoodsArray.add(exitSheetPackageGoods);
            } while (cursor6.moveToNext());
        }
        exGodItem.add(exGodItemplus);

        ArrayList<ArrayList<ExitSheetPackage>> expckItem = new ArrayList<>();
        ArrayList<ExitSheetPackage> expckItemplus = new ArrayList<>();
        ArrayList<ExitSheetPackage> exitSheetPackageArray = new ArrayList<>();
        ExitSheetPackage exitSheetPackage;
        int isItemExPck = 1;
        if (cursor5.moveToFirst()) {
            do {
                exitSheetPackage = new ExitSheetPackage(
                        Integer.parseInt(cursor5.getString(0)),
                        Integer.parseInt(cursor5.getString(1)),
                        (cursor5.getString(2)),
                        (cursor5.getString(3)),
                        Integer.parseInt(cursor5.getString(4)),
                        exGodItem.get(idEntranceSheetGod)
                );
                if (Integer.parseInt(cursor5.getString(1)) ==
                        isItemExPck) {
                    expckItemplus.add(exitSheetPackage);
                } else {
                    isItemExPck = Integer.parseInt(cursor5.getString(1));
                    expckItem.add(expckItemplus);
                    expckItemplus = new ArrayList<>();
                    expckItemplus.clear();
                    expckItemplus.add(exitSheetPackage);
                }
                idEntranceSheetGod++;
                exitSheetPackageArray.add(exitSheetPackage);
            } while (cursor5.moveToNext());
        }
        expckItem.add(expckItemplus);


        ArrayList<ExitSheet> exitSheetArray = new ArrayList<>();
        if (cursor4.moveToFirst()) {
            do {
                ExitSheet exitSheet = new ExitSheet(
                        Integer.parseInt(cursor4.getString(0)),
                        (cursor4.getString(1)),
                        (cursor4.getString(2)),
                        (cursor4.getString(3)),
                        Integer.parseInt(cursor4.getString(4)),
                        cursor4.getString(5),
                        Integer.parseInt(cursor4.getString(6)),
                        expckItem.get(idExitSheetPackageArray)
                        ,cursor4.getString(7)


                );
                if (idExitSheetPackageArray<expckItem.size()-1)
                    idExitSheetPackageArray++;
                exitSheetArray.add(exitSheet);
            } while (cursor4.moveToNext());
        }

        int isItemEn = 1;
        ArrayList<ArrayList<EntranceSheetPackageItem>> enItem = new ArrayList<>();
        ArrayList<EntranceSheetPackageItem> enItemplus = new ArrayList<>();
        ArrayList<EntranceSheetPackageItem> entranceSheetPackageItemArray = new ArrayList<>();
        EntranceSheetPackageItem entranceSheetPackageItem;
        if (cursor9.moveToFirst()) {


            do {
                String goods = " ";
                Log.e("INDEX=>", Integer.parseInt(cursor9.getString(0)) - 1 + "");

                Log.e("SIZE===>", goodsArray.size() + "");
                if (goodsArray.size() > 0 && Integer.parseInt(cursor9.getString(0)) - 1 < goodsArray.size()) {
                    goods = goodsArray.get(Integer.parseInt(cursor9.getString(2)) - 1).Name;
                    entranceSheetPackageItem = new EntranceSheetPackageItem(
                            Integer.parseInt(cursor9.getString(0)),
                            Integer.parseInt(cursor9.getString(1)),
                            Integer.parseInt(cursor9.getString(2)),
                            Integer.parseInt(cursor9.getString(3)),
                            goods

                    );
                } else {
                    entranceSheetPackageItem = new EntranceSheetPackageItem(
                            Integer.parseInt(cursor9.getString(0)),
                            Integer.parseInt(cursor9.getString(1)),
                            Integer.parseInt(cursor9.getString(2)),
                            Integer.parseInt(cursor9.getString(3))


                    );
                }
                if (Integer.parseInt(cursor9.getString(1)) ==
                        isItemEn) {
                    enItemplus.add(entranceSheetPackageItem);
                } else {
                    isItemEn = Integer.parseInt(cursor9.getString(1));
                    enItem.add(enItemplus);
                    enItemplus = new ArrayList<>();
                    enItemplus.clear();
                    enItemplus.add(entranceSheetPackageItem);
                }


                entranceSheetPackageItemArray.add(entranceSheetPackageItem);
            } while (cursor9.moveToNext());
        }
        enItem.add(enItemplus);

//        int ind=Integer.parseInt(cursor9.getString(0))-1;
//        if(entranceSheetPackageItemArray.get(ind).EntranceSheetPackageId
//                ==Integer.parseInt(cursor8.getString(1))){
//
//        }
        int is = 1;
        ArrayList<ArrayList<EntranceSheetPackage>> en = new ArrayList<>();
        ArrayList<EntranceSheetPackage> enplus = new ArrayList<>();
        ArrayList<EntranceSheetPackage> entranceSheetPackageArray = new ArrayList<>();
        if (cursor8.moveToFirst()) {

            do {

                Log.e("EN+++en", Integer.parseInt(cursor8.getString(1)) + "");

                EntranceSheetPackage entranceSheetPackage = new EntranceSheetPackage(
                        Integer.parseInt(cursor8.getString(0)),
                        Integer.parseInt(cursor8.getString(1)),
                        (cursor8.getString(2)),
                        Integer.parseInt(cursor8.getString(3)),
                        enItem.get(idEntranceSheet)
                );
                Log.e("EN+++en", "===>" + Integer.parseInt(cursor8.getString(1)));

                if (Integer.parseInt(cursor8.getString(1)) ==
                        is) {
                    enplus.add(entranceSheetPackage);
                } else {
                    is = Integer.parseInt(cursor8.getString(1));
                    en.add(enplus);
                    enplus = new ArrayList<>();
                    enplus.clear();
                    enplus.add(entranceSheetPackage);
                }

                if (enItem.size()!=idEntranceSheet){
                    idEntranceSheet++;
                }



                entranceSheetPackageArray.add(entranceSheetPackage);
            } while (cursor8.moveToNext());

        }
        en.add(enplus);



        ArrayList<EntranceSheet> entranceSheetArray = new ArrayList<>();
        if (cursor7.moveToFirst()) {
            do {
                EntranceSheet entranceSheet = new EntranceSheet(
                        Integer.parseInt(cursor7.getString(0)),
                        (cursor7.getString(1)),
                        (cursor7.getString(2)),
                        (cursor7.getString(3)),
                        Integer.parseInt(cursor7.getString(4)),
                        cursor7.getString(5),
                        Integer.parseInt(cursor7.getString(6)),
                        en.get(idEntranceSheetPackageArray)
                        ,cursor7.getString(7)


                );
                if (idEntranceSheetPackageArray<en.size()-1)
                idEntranceSheetPackageArray++;
                entranceSheetArray.add(entranceSheet);
            } while (cursor7.moveToNext());
        }
        int isItemReImage = 1;
        ArrayList<ArrayList<ReturnSheetImage>> reImageItem = new ArrayList<>();
        ArrayList<ReturnSheetImage> reImageItemplus = new ArrayList<>();
        ArrayList<ReturnSheetImage> returnSheetImageArray = new ArrayList<>();
        ReturnSheetImage returnSheetImage;

        if (cursor12.moveToFirst()) {
            do {
                returnSheetImage = new ReturnSheetImage(
                        Integer.parseInt(cursor12.getString(0)),
                        Integer.parseInt(cursor12.getString(1)),
                        (cursor12.getString(2)),
                        Integer.parseInt(cursor12.getString(3))
                );
                if (Integer.parseInt(cursor12.getString(1)) ==
                        isItemReImage) {
                    reImageItemplus.add(returnSheetImage);
                } else {
                    isItemReImage = Integer.parseInt(cursor12.getString(1));
                    reImageItem.add(reImageItemplus);
                    reImageItemplus = new ArrayList<>();
                    reImageItemplus.clear();
                    reImageItemplus.add(returnSheetImage);
                }
                returnSheetImageArray.add(returnSheetImage);
            } while (cursor12.moveToNext());
        }
        reImageItem.add(reImageItemplus);
        int isItemRe = 1;
        ArrayList<ArrayList<ReturnSheetPackageItem>> reItem = new ArrayList<>();
        ArrayList<ReturnSheetPackageItem> reItemplus = new ArrayList<>();
        ArrayList<ReturnSheetPackageItem> returnSheetPackageItemArray = new ArrayList<>();
        ReturnSheetPackageItem returnSheetPackageItem;
        if (cursor13.moveToFirst()) {

            do {
                String goods = " ";
                if (goodsArray.size() > 0 && Integer.parseInt(cursor13.getString(0)) - 1 < goodsArray.size()) {
                    goods = goodsArray.get(Integer.parseInt(cursor13.getString(2)) - 1).Name;
                    returnSheetPackageItem = new ReturnSheetPackageItem(
                            Integer.parseInt(cursor13.getString(0)),
                            Integer.parseInt(cursor13.getString(1)),
                            Integer.parseInt(cursor13.getString(2)),
                            cursor13.getString(3),
                            Integer.parseInt(cursor13.getString(4)),
                            goods

                    );
                } else {
                    returnSheetPackageItem = new ReturnSheetPackageItem(
                            Integer.parseInt(cursor13.getString(0)),
                            Integer.parseInt(cursor13.getString(1)),
                            Integer.parseInt(cursor13.getString(2)),
                            cursor13.getString(3),
                            Integer.parseInt(cursor13.getString(4))
                    );
                }
                if (Integer.parseInt(cursor13.getString(1)) ==
                        isItemRe) {
                    reItemplus.add(returnSheetPackageItem);
                } else {
                    isItemRe = Integer.parseInt(cursor13.getString(1));
                    reItem.add(reItemplus);
                    reItemplus = new ArrayList<>();
                    reItemplus.clear();
                    reItemplus.add(returnSheetPackageItem);
                }
                returnSheetPackageItemArray.add(returnSheetPackageItem);
            } while (cursor13.moveToNext());
        }
        reItem.add(reItemplus);


        int isRe = 1;
        ArrayList<ArrayList<ReturnSheetPackage>> re = new ArrayList<>();
        ArrayList<ReturnSheetPackage> replus = new ArrayList<>();
        ReturnSheetPackage returnSheetPackage;
        ArrayList<ReturnSheetPackage> returnSheetPackageArray = new ArrayList<>();
        if (cursor11.moveToFirst()) {
            do {
                returnSheetPackage = new ReturnSheetPackage(
                        Integer.parseInt(cursor11.getString(0)),
                        Integer.parseInt(cursor11.getString(1)),
                        (cursor11.getString(2)),
                        Integer.parseInt(cursor11.getString(3)),
                        reItem.get(idreturnSheetPackage)

                );
                if (Integer.parseInt(cursor11.getString(1)) ==
                        isRe) {
                    replus.add(returnSheetPackage);
                } else {
                    isRe = Integer.parseInt(cursor11.getString(1));
                    re.add(replus);
                    replus = new ArrayList<>();
                    replus.clear();
                    replus.add(returnSheetPackage);
                }

                idreturnSheetPackage++;
                returnSheetPackageArray.add(returnSheetPackage);
            } while (cursor11.moveToNext());
        }
        re.add(replus);


        ArrayList<ReturnSheet> returnSheetArray = new ArrayList<>();
        if (cursor10.moveToFirst()) {
            do {
                ReturnSheet returnSheet = new ReturnSheet(
                        Integer.parseInt(cursor10.getString(0)),
                        (cursor10.getString(1)),
                        (cursor10.getString(2)),
                        (cursor10.getString(3)),
                        (cursor10.getString(4)),
                        (cursor10.getString(5)),
                        Integer.parseInt(cursor10.getString(6)),
                        cursor10.getString(7),
                        Integer.parseInt(cursor10.getString(8)),
                        re.get(idReturnSheetArrayImage),
                        reImageItem.get(idReturnSheetArray)
                        ,cursor10.getString(9)
                );
                if (idReturnSheetArray<reImageItem.size()-1)
                    idReturnSheetArray++;

                if (idReturnSheetArrayImage<re.size()-1)
                idReturnSheetArrayImage++;
                returnSheetArray.add(returnSheet);
            } while (cursor10.moveToNext());
        }
        Log.d("cursor14", "getAllData: "+cursor14.getCount());
        SettingData settingData = null;
//
        if (cursor14.moveToFirst()){
            do {
                settingData  = new SettingData( Integer.parseInt(cursor14.getString(0)),cursor14.getString(1),
                cursor14.getString(2),Integer.parseInt(cursor14.getString(3)));

            } while (cursor14.moveToNext());

        }





        AllData allData = new AllData(goodsArray, countingSheetArray, countingSheetItemArray, exitSheetArray,
                exitSheetPackageArray, exitSheetPackageGoodsArray, entranceSheetArray, entranceSheetPackageArray,
                entranceSheetPackageItemArray, returnSheetArray, returnSheetPackageArray, returnSheetImageArray, returnSheetPackageItemArray,settingData);

        db.close();

        return allData;


    }

    public AllDataString getAllDataJson() {

        String selectQuery1 = "SELECT  * FROM " + TABLE_Goods + " ORDER BY " + Goods_KEY_ID;
        String selectQuery2 = "SELECT  * FROM " + TABLE_CountingSheet + " ORDER BY " + CountingSheet_KEY_ID;
        String selectQuery3 = "SELECT  * FROM " + TABLE_CountingSheetItem + " ORDER BY " + CountingSheetItem_KEY_ID;
        String selectQuery4 = "SELECT  * FROM " + TABLE_ExitSheet + " ORDER BY " + ExitSheet_KEY_ID;
        String selectQuery5 = "SELECT  * FROM " + TABLE_ExitSheetPackage + " ORDER BY " + ExitSheetPackage_KEY_ID;
        String selectQuery6 = "SELECT  * FROM " + TABLE_ExitSheetPackageGoods + " ORDER BY " + ExitSheetPackageGoods_KEY_ID;
        String selectQuery7 = "SELECT  * FROM " + TABLE_EntranceSheet + " ORDER BY " + EntranceSheet_KEY_ID;
        String selectQuery8 = "SELECT  * FROM " + TABLE_EntranceSheetPackage + " ORDER BY " + EntranceSheetPackage_KEY_ID;
        String selectQuery9 = "SELECT  * FROM " + TABLE_EntranceSheetPackageItem + " ORDER BY " + EntranceSheetPackageItem_KEY_ID;
        String selectQuery10 = "SELECT  * FROM " + TABLE_ReturnSheet + " ORDER BY " + ReturnSheet_KEY_ID;
        String selectQuery11 = "SELECT  * FROM " + TABLE_ReturnSheetPackage + " ORDER BY " + ReturnSheetPackage_KEY_ID;
        String selectQuery12 = "SELECT  * FROM " + TABLE_ReturnSheetImage + " ORDER BY " + ReturnSheetImage_KEY_ID;
        String selectQuery13 = "SELECT  * FROM " + TABLE_ReturnSheetPackageItem + " ORDER BY " + ReturnSheetPackageItem_KEY_ID;
        String selectQuery14 = "SELECT  * FROM " + TABLE_SettingScanner  + " ORDER BY " + SettingScanner_KEY_ID;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        Cursor cursor2 = db.rawQuery(selectQuery2, null);
        Cursor cursor3 = db.rawQuery(selectQuery3, null);
        Cursor cursor4 = db.rawQuery(selectQuery4, null);
        Cursor cursor5 = db.rawQuery(selectQuery5, null);
        Cursor cursor6 = db.rawQuery(selectQuery6, null);
        Cursor cursor7 = db.rawQuery(selectQuery7, null);
        Cursor cursor8 = db.rawQuery(selectQuery8, null);
        Cursor cursor9 = db.rawQuery(selectQuery9, null);
        Cursor cursor10 = db.rawQuery(selectQuery10, null);
        Cursor cursor11 = db.rawQuery(selectQuery11, null);
        Cursor cursor12 = db.rawQuery(selectQuery12, null);
        Cursor cursor13 = db.rawQuery(selectQuery13, null);
        Cursor cursor14 = db.rawQuery(selectQuery14, null);


        ArrayList<Map<String, String>> goodsArray = new ArrayList<>();
        if (cursor1.moveToFirst()) {
            do {
                Goods goods = new Goods(
                        Integer.parseInt(cursor1.getString(0)),
                        cursor1.getString(1),
                        cursor1.getString(2),
                        Integer.parseInt(cursor1.getString(3))
                );

                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", goods.Id + "");
                param.put("Barocde", goods.Barocde + "");
                param.put("Name", goods.Name + "");
                param.put("IsSend", goods.IsSend + "");
                goodsArray.add(param);
            } while (cursor1.moveToNext());
        }


        ArrayList<Map<String, String>> countingSheetArray = new ArrayList<>();
        if (cursor2.moveToFirst()) {
            do {
                CountingSheet countingSheet = new CountingSheet(
                        Integer.parseInt(cursor2.getString(0)),
                        cursor2.getString(1),
                        cursor2.getString(2),
                        Integer.parseInt(cursor2.getString(3)),
                        cursor2.getString(4),
                        Integer.parseInt(cursor2.getString(5))
                        ,
                        cursor2.getString(6)

                );


                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", countingSheet.Id + "");
                param.put("CounterName", countingSheet.CounterName + "");
                param.put("CountingDate", countingSheet.CountingDate + "");
                param.put("PdfPath", countingSheet.PdfPath + "");
                param.put("IsDone", countingSheet.IsDone + "");
                param.put("IsSend", countingSheet.IsSend + "");
                param.put("LogisticsCompany", countingSheet.LogisticsCompany + "");
                countingSheetArray.add(param);
            } while (cursor2.moveToNext());
        }


        ArrayList<Map<String, String>> countingSheetItemArray = new ArrayList<>();
        if (cursor3.moveToFirst()) {
            do {
                CountingSheetItem countingSheetItem = new CountingSheetItem(
                        Integer.parseInt(cursor3.getString(0)),
                        Integer.parseInt(cursor3.getString(1)),
                        Integer.parseInt(cursor3.getString(2)),
                        Integer.parseInt(cursor3.getString(3)),
                        Integer.parseInt(cursor3.getString(4)),
                        Integer.parseInt(cursor3.getString(5))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", countingSheetItem.Id + "");
                param.put("CountingSheetId", countingSheetItem.CountingSheetId + "");
                param.put("GoodsId", countingSheetItem.GoodsId + "");
                param.put("Number", countingSheetItem.Number + "");
                param.put("IsDone", countingSheetItem.IsDone + "");
                param.put("IsSend", countingSheetItem.IsSend + "");

                countingSheetItemArray.add(param);
            } while (cursor3.moveToNext());
        }

        ArrayList<Map<String, String>> exitSheetArray = new ArrayList<>();
        if (cursor4.moveToFirst()) {
            do {
                ExitSheet exitSheet = new ExitSheet(
                        Integer.parseInt(cursor4.getString(0)),
                        (cursor4.getString(1)),
                        (cursor4.getString(2)),
                        (cursor4.getString(3)),
                        Integer.parseInt(cursor4.getString(4)),
                        cursor4.getString(5),
                        Integer.parseInt(cursor4.getString(6))
                        ,cursor4.getString(7)

                );

                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", exitSheet.Id + "");
                param.put("VehicleNumber", exitSheet.VehicleNumber + "");
                param.put("VehicleDriverName", exitSheet.VehicleDriverName + "");
                param.put("PdfPath", exitSheet.PdfPath + "");
                param.put("Date", exitSheet.Date + "");
                param.put("IsDone", exitSheet.IsDone + "");
                param.put("IsSend", exitSheet.IsSend + "");
                param.put("LogisticsCompany", exitSheet.LogisticsCompany + "");
                exitSheetArray.add(param);
            } while (cursor4.moveToNext());
        }

        ArrayList<Map<String, String>> exitSheetPackageArray = new ArrayList<>();
        if (cursor5.moveToFirst()) {
            do {
                ExitSheetPackage exitSheetPackage = new ExitSheetPackage(
                        Integer.parseInt(cursor5.getString(0)),
                        Integer.parseInt(cursor5.getString(1)),
                        (cursor5.getString(2)),
                        (cursor5.getString(3)),
                        Integer.parseInt(cursor5.getString(4))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", exitSheetPackage.Id + "");
                param.put("ExitSheetId", exitSheetPackage.ExitSheetId + "");
                param.put("DeliveryNumber", exitSheetPackage.DeliveryNumber + "");
                param.put("InvoiceBarcode", exitSheetPackage.InvoiceBarcode + "");
                param.put("IsSend", exitSheetPackage.IsSend + "");
                exitSheetPackageArray.add(param);
            } while (cursor5.moveToNext());
        }

        ArrayList<Map<String, String>> exitSheetPackageGoodsArray = new ArrayList<>();
        if (cursor6.moveToFirst()) {
            do {
                ExitSheetPackageGoods exitSheetPackageGoods = new ExitSheetPackageGoods(
                        Integer.parseInt(cursor6.getString(0)),
                        Integer.parseInt(cursor6.getString(1)),
                        Integer.parseInt(cursor6.getString(2)),
                        Integer.parseInt(cursor6.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", exitSheetPackageGoods.Id + "");
                param.put("ExitSheetPackageId", exitSheetPackageGoods.ExitSheetPackageId + "");
                param.put("GoodsId", exitSheetPackageGoods.GoodsId + "");
                param.put("IsSend", exitSheetPackageGoods.IsSend + "");
                exitSheetPackageGoodsArray.add(param);
            } while (cursor6.moveToNext());
        }

        ArrayList<Map<String, String>> entranceSheetArray = new ArrayList<>();
        if (cursor7.moveToFirst()) {
            do {
                EntranceSheet entranceSheet = new EntranceSheet(
                        Integer.parseInt(cursor7.getString(0)),
                        (cursor7.getString(1)),
                        (cursor7.getString(2)),
                        (cursor7.getString(3)),
                        Integer.parseInt(cursor7.getString(4)),
                        cursor7.getString(5),
                        Integer.parseInt(cursor7.getString(6))
                        ,cursor7.getString(7)

                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", entranceSheet.Id + "");
                param.put("UserName", entranceSheet.UserName + "");
                param.put("Date", entranceSheet.Date + "");
                param.put("Description", entranceSheet.Description + "");
                param.put("PdfPath", entranceSheet.PdfPath + "");
                param.put("IsDone", entranceSheet.IsDone + "");
                param.put("IsSend", entranceSheet.IsSend + "");
                param.put("IsSend", entranceSheet.LogisticsCompany + "");
                entranceSheetArray.add(param);
            } while (cursor7.moveToNext());
        }

        ArrayList<Map<String, String>> entranceSheetPackageItemArray = new ArrayList<>();
        if (cursor9.moveToFirst()) {
            do {
                EntranceSheetPackageItem entranceSheetPackageItem = new EntranceSheetPackageItem(
                        Integer.parseInt(cursor9.getString(0)),
                        Integer.parseInt(cursor9.getString(1)),
                        Integer.parseInt(cursor9.getString(2)),
                        Integer.parseInt(cursor9.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", entranceSheetPackageItem.Id + "");
                param.put("EntranceSheetPackageId", entranceSheetPackageItem.EntranceSheetPackageId + "");
                param.put("GoodsId", entranceSheetPackageItem.GoodsId + "");
                param.put("IsSend", entranceSheetPackageItem.IsSend + "");
                entranceSheetPackageItemArray.add(param);
            } while (cursor9.moveToNext());
        }


        ArrayList<Map<String, String>> entranceSheetPackageArray = new ArrayList<>();
        if (cursor8.moveToFirst()) {
            do {
                EntranceSheetPackage entranceSheetPackage = new EntranceSheetPackage(
                        Integer.parseInt(cursor8.getString(0)),
                        Integer.parseInt(cursor8.getString(1)),
                        (cursor8.getString(2)),
                        Integer.parseInt(cursor8.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", entranceSheetPackage.Id + "");
                param.put("EntranceSheetId", entranceSheetPackage.EntranceSheetId + "");
                param.put("PackageNumber", entranceSheetPackage.PackageNumber + "");
                param.put("IsSend", entranceSheetPackage.IsSend + "");
                entranceSheetPackageArray.add(param);
            } while (cursor8.moveToNext());
        }


        ArrayList<Map<String, String>> returnSheetArray = new ArrayList<>();
        if (cursor10.moveToFirst()) {
            do {
                ReturnSheet returnSheet = new ReturnSheet(
                        Integer.parseInt(cursor10.getString(0)),
                        (cursor10.getString(1)),
                        (cursor10.getString(2)),
                        (cursor10.getString(3)),
                        (cursor10.getString(4)),
                        (cursor10.getString(5)),
                        Integer.parseInt(cursor10.getString(6)),
                        cursor10.getString(7),
                        Integer.parseInt(cursor10.getString(8))
                        ,cursor10.getString(9)


                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheet.Id + "");
                param.put("VehicleDriverName", returnSheet.VehicleDriverName + "");
                param.put("VehicleNumber", returnSheet.VehicleNumber + "");
                param.put("ReceiverName", returnSheet.ReceiverName + "");
                param.put("PostBarcode", returnSheet.PostBarcode + "");
                param.put("PdfPath", returnSheet.PdfPath + "");
                param.put("Date", returnSheet.Date + "");
                param.put("Date", returnSheet.LogisticsCompany + "");
                param.put("IsDone", returnSheet.IsDone + "");
                param.put("IsSend", returnSheet.IsSend + "");
                returnSheetArray.add(param);
            } while (cursor10.moveToNext());
        }

        ArrayList<Map<String, String>> returnSheetPackageArray = new ArrayList<>();
        if (cursor11.moveToFirst()) {
            do {
                ReturnSheetPackage returnSheetPackage = new ReturnSheetPackage(
                        Integer.parseInt(cursor11.getString(0)),
                        Integer.parseInt(cursor11.getString(1)),
                        (cursor11.getString(2)),
                        Integer.parseInt(cursor11.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheetPackage.Id + "");
                param.put("ReturnSheetId", returnSheetPackage.ReturnSheetId + "");
                param.put("OrderBarcode", returnSheetPackage.OrderBarcode + "");
                param.put("IsSend", returnSheetPackage.IsSend + "");
                returnSheetPackageArray.add(param);
            } while (cursor11.moveToNext());
        }

        ArrayList<Map<String, String>> returnSheetImageArray = new ArrayList<>();
        if (cursor12.moveToFirst()) {
            do {
                ReturnSheetImage returnSheetImage = new ReturnSheetImage(
                        Integer.parseInt(cursor12.getString(0)),
                        Integer.parseInt(cursor12.getString(1)),
                        (cursor12.getString(2)),
                        Integer.parseInt(cursor12.getString(3))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheetImage.Id + "");
                param.put("ReturnSheetId", returnSheetImage.ReturnSheetId + "");
                param.put("Image", returnSheetImage.ImageBinary + "");
                param.put("IsSend", returnSheetImage.IsSend + "");
                returnSheetImageArray.add(param);
            } while (cursor12.moveToNext());
        }

        ArrayList<Map<String, String>> returnSheetPackageItemArray = new ArrayList<>();
        if (cursor13.moveToFirst()) {
            do {
                ReturnSheetPackageItem returnSheetPackageItem = new ReturnSheetPackageItem(
                        Integer.parseInt(cursor13.getString(0)),
                        Integer.parseInt(cursor13.getString(1)),
                        Integer.parseInt(cursor13.getString(2)),
                        cursor13.getString(3),
                        Integer.parseInt(cursor13.getString(4))
                );
                Map<String, String> param = new HashMap<String, String>();
                param.put("Id", returnSheetPackageItem.Id + "");
                param.put("ReturnSheetPackageId", returnSheetPackageItem.ReturnSheetPackageId + "");
                param.put("GoodsId", returnSheetPackageItem.GoodsId + "");
                param.put("Comment", returnSheetPackageItem.Commnet + "");
                param.put("IsSend", returnSheetPackageItem.IsSend + "");
                returnSheetPackageItemArray.add(param);
            } while (cursor13.moveToNext());
        }
        SettingData settingData = null;
        if (cursor14.moveToFirst()) {
            settingData  = new SettingData( Integer.parseInt(cursor14.getString(0)),cursor14.getString(1),
                    cursor14.getString(2),Integer.parseInt(cursor14.getString(3)));
        }

        AllDataString allData = new AllDataString(goodsArray, countingSheetArray, countingSheetItemArray, exitSheetArray,
                exitSheetPackageArray, exitSheetPackageGoodsArray, entranceSheetArray, entranceSheetPackageArray,
                entranceSheetPackageItemArray, returnSheetArray, returnSheetPackageArray, returnSheetImageArray, returnSheetPackageItemArray,settingData);

        db.close();

        return allData;


    }


    ///////// action for all database
    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    public void resetAllDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_Language);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_AppText);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_Goods);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CountingSheet);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CountingSheetItem);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ExitSheet);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ExitSheetPackage);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ExitSheetPackageGoods);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EntranceSheet);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EntranceSheetPackage);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EntranceSheetPackageItem);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheet);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheetImage);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheetPackage);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ReturnSheetPackageItem);


        database.execSQL(CREATE_Language_TABLE);
        database.execSQL(CREATE_AppText_TABLE);
        database.execSQL(CREATE_Goods_TABLE);
        database.execSQL(CREATE_CountingSheet_TABLE);
        database.execSQL(CREATE_CountingSheetItem_TABLE);
        database.execSQL(CREATE_ExitSheet_TABLE);
        database.execSQL(CREATE_ExitSheetPackage_TABLE);
        database.execSQL(CREATE_ExitSheetPackageGoods_TABLE);
        database.execSQL(CREATE_EntranceSheet_TABLE);
        database.execSQL(CREATE_EntranceSheetPackage_TABLE);
        database.execSQL(CREATE_EntranceSheetPackageItem_TABLE);
        database.execSQL(CREATE_ReturnSheet_TABLE);
        database.execSQL(CREATE_ReturnSheetImage_TABLE);
        database.execSQL(CREATE_ReturnSheetPackage_TABLE);
        database.execSQL(CREATE_ReturnSheetPackageItem_TABLE);
        database.close();
    }

    public boolean isTableExists() {
        SQLiteDatabase database = getWritableDatabase();

        if (true) {
            if (database == null || !database.isOpen()) {
                database = getReadableDatabase();
            }

            if (!database.isReadOnly()) {
                database.close();
                database = getReadableDatabase();
            }
        }

        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_AppText + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }


}