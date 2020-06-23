package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Activity.EntranceSheetPackageActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.EntranceSheet;
import com.interhandelsgmbh.storeroom.R;

import java.io.File;
import java.util.ArrayList;

public class PreEntranceSheetAdapter extends BaseAdapter {

    Context context;
    ArrayList<EntranceSheet> preEntranceSheetList = new ArrayList<>();
    ArrayList<Integer> picBack = new ArrayList<>();
    LayoutInflater inflater;

    Dialog dialog;

    public PreEntranceSheetAdapter() {
    }

    public PreEntranceSheetAdapter(Context context, ArrayList<EntranceSheet> preEntranceSheetList, Dialog dialog) {

        this.context = context;
        this.dialog = dialog;
        this.preEntranceSheetList = preEntranceSheetList;

        for (int i = 0; i < preEntranceSheetList.size(); i++) {
            if (preEntranceSheetList.get(i).IsDone == 1) {
                picBack.add(R.drawable.button_has_pdf);
            } else {
                picBack.add(R.drawable.button_no_pdf);
            }
        }


        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return preEntranceSheetList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_pre_counting_sheet, parent, false);
            holder.tv_preEntranceSheet = convertView.findViewById(R.id.tv_preCountingSheet);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_preEntranceSheet.setTag(position);


        holder.tv_preEntranceSheet.setBackgroundResource(picBack.get(position));

        String name = context.getResources().getString(R.string.name);
        String date = context.getResources().getString(R.string.date);

        SharedPreferences setting = context.getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& name = 12
            //&& date = 117


            AppText nameText = dataBaseHandler.getAppText(12, languageId);
            AppText dateText = dataBaseHandler.getAppText(117, languageId);

            if (nameText != null) {
                name = (nameText.text);
            }
            if (dateText != null) {
                date = (dateText.text);
            }
        }


        holder.tv_preEntranceSheet.setText(name+": "+preEntranceSheetList.get(position).UserName +"\n"+date+": "+preEntranceSheetList.get(position).Date);

        holder.tv_preEntranceSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preEntranceSheetList.get(position).IsDone == 0) {
                    goToContinueEntranceSheetItem(position);
                }else{
                    openPDF(preEntranceSheetList.get(position).PdfPath);
                }
            }
        });

        return convertView;

    }

    public class ViewHolder {

        TextView tv_preEntranceSheet;

    }


    /**
     * intent this entrance sheet info into CountingSheetActivity
     * @param position
     */
    public void goToContinueEntranceSheetItem(int position) {

        dialog.dismiss();
        dialog.cancel();
        Intent intent = new Intent(context, EntranceSheetPackageActivity.class);
        intent.putExtra("entranceSheetId", preEntranceSheetList.get(position).Id + "");
        intent.putExtra("entranceSheetUserName", preEntranceSheetList.get(position).UserName + "");
        intent.putExtra("entranceSheetDescription", preEntranceSheetList.get(position).Description+ "");
        intent.putExtra("entranceSheetDate", preEntranceSheetList.get(position).Date + "");
        intent.putExtra("entranceSheetLogisticsCompany", preEntranceSheetList.get(position).LogisticsCompany + "");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    /**
     * open pdf file of completed sheet
     * @param filePath
     */
    private void openPDF(String filePath) {

        String error_open_file = context.getResources().getString(R.string.error_open_file);
        SharedPreferences setting = context.getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& error_open_file = 22
            AppText error_open_fileText = dataBaseHandler.getAppText(22, languageId);

            if (error_open_fileText != null) {
                error_open_file = (error_open_fileText.text);
            }
        }


        File file = new File(filePath);
        Uri attachmentUri = FileProvider.getUriForFile(context, "com.freshdesk.helpdesk.interstoreroom", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, error_open_file, Toast.LENGTH_LONG).show();
            }
        } else {
            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, "application/pdf");
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, error_open_file, Toast.LENGTH_LONG).show();
            }
        }
    }


}
