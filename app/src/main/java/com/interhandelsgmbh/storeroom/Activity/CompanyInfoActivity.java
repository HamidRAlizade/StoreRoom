package com.interhandelsgmbh.storeroom.Activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.interhandelsgmbh.storeroom.Class.Content;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.R;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static com.interhandelsgmbh.storeroom.BuildConfig.DEBUG;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.icu.util.ULocale.getName;

public class CompanyInfoActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath = "";
    public static final String DOCUMENTS_DIR = "documents";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void setViewText() {
        TextView tv_coInfo = findViewById(R.id.tv_coInfo);
        TextView tv_coName = findViewById(R.id.tv_coName);
        TextView tv_coAddress = findViewById(R.id.tv_coAddress);
        TextView tvCompanySave = findViewById(R.id.tvCompanySave);
        CheckBox cbCompanyLogo = findViewById(R.id.cbCompanyLogo);
        String coInfo = getResources().getString(R.string.company_information);
        String coName = getResources().getString(R.string.company_name);
        String coAddress = getResources().getString(R.string.company_address);
        String coLogo = getResources().getString(R.string.add_company_logo);
        String save = getResources().getString(R.string.save);
        errorCoNAme = getResources().getString(R.string.enter_co_name);
        errorCoAddress = getResources().getString(R.string.error_co_address);
        errorCoLogo = getResources().getString(R.string.error_co_logo);


        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(CompanyInfoActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& company_information = 2
            //&& company_name = 3
            //&& company_address = 4
            //&& add_company_logo = 5
            //&& save = 6
            //&& error co name = 7
            //&& error co address = 8
            //&& error c logo = 9

            AppText company_informationText = dataBaseHandler.getAppText(2, languageId);
            AppText company_nameText = dataBaseHandler.getAppText(3, languageId);
            AppText company_addressText = dataBaseHandler.getAppText(4, languageId);
            AppText add_company_logoText = dataBaseHandler.getAppText(5, languageId);
            AppText saveText = dataBaseHandler.getAppText(6, languageId);
            AppText errorCoNameText = dataBaseHandler.getAppText(7, languageId);
            AppText errorCoAddressText = dataBaseHandler.getAppText(8, languageId);
            AppText errorCoLogoText = dataBaseHandler.getAppText(9, languageId);

            if (company_informationText != null) {
                tv_coInfo.setText(company_informationText.text);
            } else {
                tv_coInfo.setText(coInfo);
            }
            if (company_nameText != null) {
                tv_coName.setText(company_nameText.text);
            } else {
                tv_coName.setText(coName);
            }
            if (company_addressText != null) {
                tv_coAddress.setText(company_addressText.text);
            } else {
                tv_coAddress.setText(coAddress);
            }
            if (add_company_logoText != null) {
                cbCompanyLogo.setText(add_company_logoText.text);
            } else {
                cbCompanyLogo.setText(coLogo);
            }
            if (saveText != null) {
                tvCompanySave.setText(saveText.text);
            } else {
                tvCompanySave.setText(save);
            }
            if (errorCoNameText != null) {
                errorCoNAme = (errorCoNameText.text);
            }
            if (errorCoAddressText != null) {
                errorCoAddress = (errorCoAddressText.text);
            }
            if (errorCoLogoText != null) {
                errorCoLogo = (errorCoLogoText.text);
            }
        } else {
            tv_coInfo.setText(coInfo);
            tv_coName.setText(coName);
            tv_coAddress.setText(coAddress);
            cbCompanyLogo.setText(coLogo);
            tvCompanySave.setText(save);

        }

    }


    private String errorCoNAme, errorCoAddress, errorCoLogo, sucessSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        Fabric.with(this, new Crashlytics());

        /**
         * Find views by Id
         */
        final EditText etCompanyName = findViewById(R.id.etCompanyName);
        final EditText etCompanyAddress = findViewById(R.id.etCompanyAddress);
        final CheckBox cbCompanyLogo = findViewById(R.id.cbCompanyLogo);
        TextView tvCompanySave = findViewById(R.id.tvCompanySave);
        ImageView ivAddCompanyLogo = findViewById(R.id.ivAddCompanyLogo);

        final EditText et_postUrl = findViewById(R.id.et_postUrl);

        setViewText();


        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        if (settings.contains("CompanyName")) {
            String coName = settings.getString("CompanyName", "");
            String coAddress = settings.getString("CompanyAddress", "");
            String CompanyLogoPath = settings.getString("CompanyLogoPath", "");
            etCompanyName.setText(coName + "");
            etCompanyAddress.setText(coAddress + "");
            selectedImagePath = CompanyLogoPath;
        }
        if (settings.contains("postUrl")) {
            if (settings.getString("postUrl", "").length() > 0) {

                String url = settings.getString("postUrl", "");
                et_postUrl.setText(url);
            }
        }


        /**
         * get image file from storage
         */
        ivAddCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });


        cbCompanyLogo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedImagePath = "";
                    cbCompanyLogo.setClickable(false);
                }
            }
        });


        /**
         * save company info on sharedPreference
         */
        tvCompanySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = etCompanyName.getText().toString();
                String companyAddress = etCompanyAddress.getText().toString();
                if (companyName.isEmpty() || companyName.equals("")) {
                    Toast.makeText(CompanyInfoActivity.this, errorCoNAme, Toast.LENGTH_LONG).show();
                } else if (companyAddress.isEmpty() || companyAddress.equals("")) {
                    Toast.makeText(CompanyInfoActivity.this, errorCoAddress, Toast.LENGTH_LONG).show();
                } else if (selectedImagePath.equals("")) {
                    Toast.makeText(CompanyInfoActivity.this, errorCoLogo, Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences setting = getSharedPreferences("UserInfo", 0);
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putString("CompanyName", companyName);
                    editor.putString("CompanyAddress", companyAddress);
                    editor.putString("CompanyLogoPath", selectedImagePath);
                    if(et_postUrl.getText().toString().length()>0){
                        String url = et_postUrl.getText().toString();
                        editor.putString("postUrl",url);
                    }
                    editor.commit();
                    editor.apply();
                    Content.grtUrl(getApplicationContext());

                    Intent intent = new Intent(CompanyInfoActivity.this, ReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(CompanyInfoActivity.this, selectedImageUri);
                CheckBox cbCompanyLogo = findViewById(R.id.cbCompanyLogo);
                cbCompanyLogo.setChecked(true);
                cbCompanyLogo.setClickable(true);
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
