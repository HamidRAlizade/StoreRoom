package com.interhandelsgmbh.storeroom.Activity;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.Language;
import com.interhandelsgmbh.storeroom.R;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.icu.util.ULocale.getName;
import static com.interhandelsgmbh.storeroom.BuildConfig.DEBUG;

public class LanguageImportActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath = "";
    public static final String DOCUMENTS_DIR = "documents";


    ArrayList<AppText> appTextArrayList = new ArrayList<>();
    String languageText = "English";
    int languageId = 2;
    private int nextId = 2;

    private int newNumber = 0;

    LinearLayout ll_detail;
    TextView tv_newData;

    Boolean flag = true;


    String imports, csv_file_added, show_data_detail, import_new_data,
            select_csv_file, add_csv_file, import_item_number, csv_file_error,
            number_new_data, number_duplicate_data, select_csv_error;

    String splitter, which_splitter_has_been_used_in_your_csv_file, comma, semicolon,
            if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_update,
            reset, update;

    public void setViewText() {
        TextView tv_import = findViewById(R.id.tv_import);
        CheckBox cb_csvFile = findViewById(R.id.cb_csvFile);
        Button bt_getCsvFile = findViewById(R.id.bt_getCsvFile);
        Button bt_import = findViewById(R.id.bt_import);


        imports = getResources().getString(R.string.imports);
        csv_file_added = getResources().getString(R.string.csv_file_added);
        show_data_detail = getResources().getString(R.string.show_data_detail);
        import_new_data = getResources().getString(R.string.import_new_data);
        select_csv_file = getResources().getString(R.string.select_csv_file);
        add_csv_file = getResources().getString(R.string.add_csv_file);
        import_item_number = getResources().getString(R.string.import_item_number);
        csv_file_error = getResources().getString(R.string.csv_file_error3);
        number_new_data = getResources().getString(R.string.number_new_data);
        number_duplicate_data = getResources().getString(R.string.number_duplicate_data);
        select_csv_error = getResources().getString(R.string.select_csv_error);
        splitter = getResources().getString(R.string.splitter);
        which_splitter_has_been_used_in_your_csv_file = getResources().getString(R.string.which_splitter_has_been_used_in_your_csv_file);
        comma = getResources().getString(R.string.comma);
        semicolon = getResources().getString(R.string.semicolon);
        if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_update = getResources().getString(R.string.if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_update);
        reset = getResources().getString(R.string.reset);
        update = getResources().getString(R.string.update);


//        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
//        if (setting.contains("LanguageId")) {
//            String LanguageIdStr = setting.getString("LanguageId", "1");
//            DataBaseHandler dataBaseHandler = new DataBaseHandler(LanguageImportActivity.this);
//            int languageId = 1;
//            try {
//                languageId = Integer.parseInt(LanguageIdStr);
//            } catch (Exception e) {
//                languageId = 1;
//            }
//            //&& imports = 41
//            //&& csv_file_added = 42
//            //&& show_data_detail = 43
//            //&& import_new_data = 44
//            //&& select_csv_file = 45
//            //&& add_csv_file = 46
//            //&& import_item_number = 47
//            //&& csv_file_error = 48
//            //&& number_new_data = 49
//            //&& number_duplicate_data = 50
//            //&& select_csv_error = 51
//            //&& splitter = 52
//            //&& which_splitter_has_been_used_in_your_csv_file = 53
//            //&& comma = 54
//            //&& semicolon = 55
//            //&& if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_update = 56
//            //&& reset = 57
//            //&& update = 58
//
//
//            AppText importsText = dataBaseHandler.getAppText(41, languageId);
//            AppText csv_file_addedText = dataBaseHandler.getAppText(42, languageId);
//            AppText show_data_detailText = dataBaseHandler.getAppText(43, languageId);
//            AppText import_new_dataText = dataBaseHandler.getAppText(44, languageId);
//            AppText select_csv_fileText = dataBaseHandler.getAppText(45, languageId);
//            AppText add_csv_fileText = dataBaseHandler.getAppText(46, languageId);
//            AppText import_item_numberText = dataBaseHandler.getAppText(47, languageId);
//            AppText csv_file_errorText = dataBaseHandler.getAppText(48, languageId);
//            AppText number_new_dataText = dataBaseHandler.getAppText(49, languageId);
//            AppText number_duplicate_dataText = dataBaseHandler.getAppText(50, languageId);
//            AppText select_csv_errorText = dataBaseHandler.getAppText(51, languageId);
//            AppText splitterText = dataBaseHandler.getAppText(52, languageId);
//            AppText which_splitter_has_been_used_in_your_csv_fileText = dataBaseHandler.getAppText(53, languageId);
//            AppText commaText = dataBaseHandler.getAppText(54, languageId);
//            AppText semicolonText = dataBaseHandler.getAppText(55, languageId);
//            AppText if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_updateText = dataBaseHandler.getAppText(56, languageId);
//            AppText resetText = dataBaseHandler.getAppText(57, languageId);
//            AppText updateText = dataBaseHandler.getAppText(58, languageId);
//
//
//            if (importsText != null) {
//                imports = (importsText.text);
//            }
//            if (csv_file_addedText != null) {
//                csv_file_added = (csv_file_addedText.text);
//            }
//            if (show_data_detailText != null) {
//                show_data_detail = (show_data_detailText.text);
//            }
//            if (import_new_dataText != null) {
//                import_new_data = (import_new_dataText.text);
//            }
//            if (select_csv_fileText != null) {
//                select_csv_file = (select_csv_fileText.text);
//            }
//            if (add_csv_fileText != null) {
//                add_csv_file = (add_csv_fileText.text);
//            }
//            if (import_item_numberText != null) {
//                import_item_number = (import_item_numberText.text);
//            }
//            if (csv_file_errorText != null) {
//                csv_file_error = (csv_file_errorText.text);
//            }
//            if (number_new_dataText != null) {
//                number_new_data = (number_new_dataText.text);
//            }
//            if (number_duplicate_dataText != null) {
//                number_duplicate_data = (number_duplicate_dataText.text);
//            }
//            if (select_csv_errorText != null) {
//                select_csv_error = (select_csv_errorText.text);
//            }
//            if (splitterText != null) {
//                splitter = (splitterText.text);
//            }
//            if (which_splitter_has_been_used_in_your_csv_fileText != null) {
//                which_splitter_has_been_used_in_your_csv_file = (which_splitter_has_been_used_in_your_csv_fileText.text);
//            }
//            if (commaText != null) {
//                comma = (commaText.text);
//            }
//            if (semicolonText != null) {
//                semicolon = (semicolonText.text);
//            }
//            if (if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_updateText != null) {
//                if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_update = (if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_updateText.text);
//            }
//            if (resetText != null) {
//                reset = (resetText.text);
//            }
//            if (updateText != null) {
//                update = (updateText.text);
//            }
//
//
//        }


        tv_import.setText(imports);
        cb_csvFile.setText(csv_file_added);
        bt_getCsvFile.setText(show_data_detail);
        bt_import.setText(import_new_data);


    }


    EditText et_LanguageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_import);


        final DataBaseHandler dataBaseHandler = new DataBaseHandler(LanguageImportActivity.this);

        Button bt_import = findViewById(R.id.bt_import);

        ll_detail = findViewById(R.id.ll_detail);
        tv_newData = findViewById(R.id.tv_newDataNumber);
        ll_detail.setVisibility(View.INVISIBLE);
        et_LanguageName = findViewById(R.id.et_LanguageName);

        setViewText();

        ArrayList<Language> preLanguageList = new ArrayList<>();
        ArrayList<Integer> preLanguageIdList = new ArrayList<>();

        /**
         * get all language's data that saved in TABLE_Language
         */
        preLanguageList = dataBaseHandler.getAllLanguage();
        for (int i = 0; i < preLanguageList.size(); i++) {
            preLanguageIdList.add(preLanguageList.get(i).Id);
        }
        if (preLanguageIdList.size() != 0) {
            nextId = preLanguageIdList.get(preLanguageIdList.size() - 1) + 1;
        }

        languageId = nextId;
        Log.e("nextId", nextId + "");


        ImageView imv_addCsvFile = findViewById(R.id.imv_addCsvFile);
        final CheckBox cb_csvFile = findViewById(R.id.cb_csvFile);

        /**
         * attache csv file from storage
         */
        imv_addCsvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String languageName = et_LanguageName.getText().toString();
                if (languageName.length() > 0) {
                    languageText = languageName;
                    flag = true;
                    cb_csvFile.setChecked(false);
                    newNumber = 0;
                    appTextArrayList.clear();

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(Intent.createChooser(intent, select_csv_file), SELECT_PICTURE);
                } else {
                    Toast.makeText(LanguageImportActivity.this, "Write Name of Language", Toast.LENGTH_LONG).show();
                }
            }
        });


        Button bt_exportCsvFile = findViewById(R.id.bt_exportCsvFile);
        bt_exportCsvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String languageName = et_LanguageName.getText().toString();
                if (languageName.length() > 0) {
                    Toast.makeText(LanguageImportActivity.this, "please wait ...", Toast.LENGTH_LONG).show();

                    languageText = languageName;

                    ArrayList<String[]> appTextList = new ArrayList<String[]>();
                    appTextList = dataBaseHandler.getAllEnText(languageName);
                    Log.e("appTextList", appTextList + "--");
                    File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator + "storeRoomCsv");
                    if (!storageDir.exists()) {
                        storageDir.mkdirs();
                        if (!storageDir.mkdirs()) {
                            Log.e("Error", "failed to create directory");
                        }
                    }
                    int num = new Random().nextInt(100000);
                    String fileName = "EnText_id" + num + ".csv";
                    String filePath = storageDir + File.separator + fileName;
                    File f = new File(filePath);
                    CSVWriter writer = null;

                    if (f.exists() && !f.isDirectory()) {
                        FileWriter mFileWriter = null;
                        try {
                            mFileWriter = new FileWriter(filePath, true);
                            writer = new CSVWriter(mFileWriter);
                            writer.writeAll(appTextList);
                            writer.close();
                            openFolder(filePath + "");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(LanguageImportActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        try {
                            writer = new CSVWriter(new FileWriter(filePath));
                            writer.writeAll(appTextList);
                            writer.close();
                            openFolder(filePath + "");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(LanguageImportActivity.this, "Error!", Toast.LENGTH_LONG).show();

                        }
                    }

                } else {
                    Toast.makeText(LanguageImportActivity.this, "Write Name of Language", Toast.LENGTH_LONG).show();
                }


            }
        });

        Button bt_getCsvFile = findViewById(R.id.bt_getCsvFile);
        bt_getCsvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = selectedImagePath + "";

                dialog_splitter(path);


            }
        });

        bt_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appTextArrayList.size() > 0) {
                    dialog_importData(appTextArrayList);
                } else {
                    Toast.makeText(LanguageImportActivity.this, add_csv_file, Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void openFolder(String filePath) {

        final Dialog dialog = new Dialog(LanguageImportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exportedfilepath);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_filePath = dialog.findViewById(R.id.tv_filePath);
        Button bt_ok = dialog.findViewById(R.id.bt_ok);

        tv_filePath.setText("see source file in: \n"+filePath);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * choose user wants reset or update database
     *
     * @param appTextArrayList
     */
    public void dialog_importData(final ArrayList<AppText> appTextArrayList) {

        final Dialog logOut = new Dialog(LanguageImportActivity.this);
        logOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logOut.setContentView(R.layout.dialog_import_data);
        logOut.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_imports = logOut.findViewById(R.id.tv_imports);
        TextView tv_importText = logOut.findViewById(R.id.tv_importText);
        Button bt_resetData = logOut.findViewById(R.id.bt_resetData);
        Button bt_updateData = logOut.findViewById(R.id.bt_updateData);

        tv_imports.setText(imports);
        tv_importText.setText(if_you_want_to_clear_old_data_and_import_new_one_click_on_reset_otherwise_click_on_update);
        bt_resetData.setText(reset);
        bt_updateData.setText(update);

        bt_resetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.dismiss();
                logOut.cancel();
                ResetAndAddNewDataIntoDatabase(appTextArrayList);
            }
        });

        bt_updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.dismiss();
                logOut.cancel();
                AddNewDataIntoDatabase(appTextArrayList);
            }
        });

        logOut.setCancelable(true);
        logOut.show();
    }


    /**
     * reset TABLE_Goods and import new data from csv file
     *
     * @param maindata
     */
    public void ResetAndAddNewDataIntoDatabase(ArrayList<AppText> maindata) {

        DataBaseHandler dataBaseHandler = new DataBaseHandler(LanguageImportActivity.this);

        dataBaseHandler.deleteAllImportedText();
        for (int i = 0; i < maindata.size(); i++) {
            AppText appText = maindata.get(i);
            dataBaseHandler.addAppText(appText);
        }

        Language language = new Language(languageId, languageText);
        dataBaseHandler.addLanguage(language);

        Toast.makeText(LanguageImportActivity.this, languageText + " imported successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(LanguageImportActivity.this, ReportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    /**
     * add items data from csv file into TABLE_Goods
     *
     * @param newdata
     */
    public void AddNewDataIntoDatabase(ArrayList<AppText> newdata) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(LanguageImportActivity.this);

        for (int i = 0; i < newdata.size(); i++) {
            AppText appText = newdata.get(i);
            dataBaseHandler.addAppText(appText);

        }

        Language language = new Language(languageId, languageText);
        dataBaseHandler.addLanguage(language);

        Toast.makeText(LanguageImportActivity.this, languageText + " imported successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(LanguageImportActivity.this, ReportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    /**
     * choose wich splitter is used in csv file
     *
     * @param path
     */
    public void dialog_splitter(final String path) {

        final Dialog logOut = new Dialog(LanguageImportActivity.this);
        logOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logOut.setContentView(R.layout.dialog_csvseprator);
        logOut.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_splitter = logOut.findViewById(R.id.tv_splitter);
        TextView tv_splitText = logOut.findViewById(R.id.tv_splitText);
        Button bt_comma = logOut.findViewById(R.id.bt_comma);
        Button bt_semicolon = logOut.findViewById(R.id.bt_semicolon);
        tv_splitter.setText(splitter);
        tv_splitText.setText(which_splitter_has_been_used_in_your_csv_file);
        bt_comma.setText(comma);
        bt_semicolon.setText(semicolon);

        bt_comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.dismiss();
                logOut.cancel();
                readEnglishCsvFile(path);
            }
        });

        bt_semicolon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.dismiss();
                logOut.cancel();
                readGermanCsvFile(path);
            }
        });

        logOut.setCancelable(true);
        logOut.show();
    }


    /**
     * read data from csv file that split with semicolon
     *
     * @param path
     */
    public void readGermanCsvFile(String path) {
        try {
            File csvfile = new File(path);
            Log.e("pathhh:: ", csvfile.getPath() + "");

            appTextArrayList.clear();
            newNumber = 0;

            FileInputStream fileInputStream = new FileInputStream(csvfile);
            InputStreamReader fileReaderState = new InputStreamReader(fileInputStream, "ISO-8859-1");
//            UnicodeReader ur = new UnicodeReader(fileInputStream, "UTF-8");
            BufferedReader buffer = new BufferedReader(fileReaderState);
            String line = "";
            languageId = nextId;
            int counter = 1;

            boolean flag = true;

            while ((line = buffer.readLine()) != null) {

                String[] nextLine = line.split(";");
                if (counter > 1) {
                    int c = nextLine.length;
                    Log.e("csv column:", c + "");
                    StringBuilder s = new StringBuilder();
                    if (c < 3) {
                        Toast.makeText(LanguageImportActivity.this, csv_file_error, Toast.LENGTH_LONG).show();
                        flag = false;
                        break;
                    } else {
                        for (int k = 2; k < c; k++) {
                            s.append(nextLine[k]);
                        }


                        String text = s.toString();
                        int parentId = Integer.parseInt(nextLine[0]);

                        AppText appText = new AppText(parentId, parentId, languageId, text);

                        appTextArrayList.add(appText);


                        newNumber = newNumber + 1;

                    }
                }
                counter = counter + 1;
            }

//
//
//
//
//
//
//
//
//            CSVReader reader = new CSVReader(new FileReader(path));
//            String[] nextLine;
//
//            while ((nextLine = reader.readNext()) != null) {
//                // nextLine[] is an array of values from the line
//                if(counter>1){
//                    System.out.println(id+"-"+nextLine[0] +"-"+ nextLine[1]);
//
//                    Goods goods = new Goods(id,nextLine[0],nextLine[1]);
//                    if(preGoodsBarcodeList.contains(nextLine[1]) || newBarCode.contains(nextLine[1])){
//                        preNumber = preNumber + 1;
//                    }else{
//                        newNumber = newNumber + 1;
//                        newBarCode.add(nextLine[1]);
//                        newdata.add(goods);
//                        id = id + 1;
//                    }
//
//                }
//                counter = counter+1;
//            }

            if (flag) {
                ll_detail.setVisibility(View.VISIBLE);
                tv_newData.setText(number_new_data + ": " + newNumber);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, select_csv_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * read data from csv file that split with comma
     *
     * @param path
     */
    public void readEnglishCsvFile(String path) {
        try {
            File csvfile = new File(path);
            Log.e("pathhh:: ", csvfile.getPath() + "");

            appTextArrayList.clear();
            newNumber = 0;

            FileInputStream fileInputStream = new FileInputStream(csvfile);
            InputStreamReader fileReaderState = new InputStreamReader(fileInputStream, "ISO-8859-1");
//            UnicodeReader ur = new UnicodeReader(fileInputStream, "UTF-8");
            BufferedReader buffer = new BufferedReader(fileReaderState);
            String line = "";
            int languageId = nextId;
            int counter = 1;

            boolean flag = true;

            while ((line = buffer.readLine()) != null) {

                String[] nextLine = line.split(",");
                if (counter > 1) {
                    int c = nextLine.length;
                    Log.e("csv column:", c + "");
                    StringBuilder s = new StringBuilder();
                    if (c < 3) {
                        Toast.makeText(LanguageImportActivity.this, csv_file_error, Toast.LENGTH_LONG).show();
                        flag = false;
                        break;
                    } else {
                        for (int k = 2; k < c; k++) {
                            s.append(nextLine[k]);
                        }


                        String text = s.toString();
                        int parentId = Integer.parseInt(nextLine[0]);

                        AppText appText = new AppText(parentId, parentId, languageId, text);

                        appTextArrayList.add(appText);


                        newNumber = newNumber + 1;

                    }
                }
                counter = counter + 1;
            }

//
//
//
//
//
//
//
//
//            CSVReader reader = new CSVReader(new FileReader(path));
//            String[] nextLine;
//
//            while ((nextLine = reader.readNext()) != null) {
//                // nextLine[] is an array of values from the line
//                if(counter>1){
//                    System.out.println(id+"-"+nextLine[0] +"-"+ nextLine[1]);
//
//                    Goods goods = new Goods(id,nextLine[0],nextLine[1]);
//                    if(preGoodsBarcodeList.contains(nextLine[1]) || newBarCode.contains(nextLine[1])){
//                        preNumber = preNumber + 1;
//                    }else{
//                        newNumber = newNumber + 1;
//                        newBarCode.add(nextLine[1]);
//                        newdata.add(goods);
//                        id = id + 1;
//                    }
//
//                }
//                counter = counter+1;
//            }

            if (flag) {
                ll_detail.setVisibility(View.VISIBLE);
                tv_newData.setText(number_new_data + ": " + newNumber);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, select_csv_error, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * get csv file path
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            selectedImagePath = getPath(LanguageImportActivity.this, selectedImageUri);

            try {
                CSVReader reader = new CSVReader(new FileReader(selectedImagePath));
                CheckBox cb_csvFile = findViewById(R.id.cb_csvFile);
                cb_csvFile.setChecked(true);
                cb_csvFile.setClickable(false);
                Button bt_getCsvFile = findViewById(R.id.bt_getCsvFile);
                bt_getCsvFile.setClickable(true);
                bt_getCsvFile.setAlpha(1f);

            } catch (Exception e) {
                CheckBox cb_csvFile = findViewById(R.id.cb_csvFile);
                cb_csvFile.setChecked(false);
                cb_csvFile.setClickable(false);
                Button bt_getCsvFile = findViewById(R.id.bt_getCsvFile);
                bt_getCsvFile.setClickable(false);
                bt_getCsvFile.setAlpha(0.6f);
                e.printStackTrace();
                Toast.makeText(this, select_csv_error, Toast.LENGTH_SHORT).show();
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

//                if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }

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
        File dir = new File(context.getCacheDir(), DOCUMENTS_DIR);
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


}
