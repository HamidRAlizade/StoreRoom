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

import com.interhandelsgmbh.storeroom.Activity.ExitSheetPackageActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.R;

import java.io.File;
import java.util.ArrayList;

public class PreExitSheetAdapter extends BaseAdapter {

    Context context;
    ArrayList<ExitSheet> PreExitSheetList = new ArrayList<>();
    ArrayList<Integer> picBack = new ArrayList<>();
    LayoutInflater inflater;

    Dialog dialog;

    public PreExitSheetAdapter() {
    }

    public PreExitSheetAdapter(Context context, ArrayList<ExitSheet> preExitSheetList, Dialog dialog) {

        this.context = context;
        this.dialog = dialog;
        this.PreExitSheetList = preExitSheetList;

        for (int i = 0; i < PreExitSheetList.size(); i++) {
            if (PreExitSheetList.get(i).IsDone == 1) {
                picBack.add(R.drawable.button_has_pdf);
            } else {
                picBack.add(R.drawable.button_no_pdf);
            }
        }


        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return PreExitSheetList.size();
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
            holder.tv_preExitSheet = convertView.findViewById(R.id.tv_preCountingSheet);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_preExitSheet.setTag(position);


        holder.tv_preExitSheet.setBackgroundResource(picBack.get(position));

        String driver_name = context.getResources().getString(R.string.driver_name);
        String vehicle_number = context.getResources().getString(R.string.vehicle_number);
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
            //&& driver_name = 77
            //&& vehicle_number = 78
            //&& date = 117

            AppText driver_nameText = dataBaseHandler.getAppText(77, languageId);
            AppText vehicle_numberText = dataBaseHandler.getAppText(78, languageId);
            AppText dateText = dataBaseHandler.getAppText(117, languageId);

            if (driver_nameText != null) {
                driver_name = (driver_nameText.text);
            }
            if (vehicle_numberText != null) {
                vehicle_number = (vehicle_numberText.text);
            }
            if (dateText != null) {
                date = (dateText.text);
            }
        }

        holder.tv_preExitSheet.setText(driver_name+": "+PreExitSheetList.get(position).VehicleDriverName + "\n" + vehicle_number+": " + PreExitSheetList.get(position).VehicleNumber+"\n"+date+": "+PreExitSheetList.get(position).Date);

        holder.tv_preExitSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreExitSheetList.get(position).IsDone == 0) {
                    goToContinueExitSheetItem(position);
                }else{
                    openPDF(PreExitSheetList.get(position).PdfPath);
                }
            }
        });

        return convertView;

    }

    public class ViewHolder {

        TextView tv_preExitSheet;

    }


    /**
     * intent this outgoing sheet info into CountingSheetActivity
     * @param position
     */
    public void goToContinueExitSheetItem(int position) {

        dialog.dismiss();
        dialog.cancel();
        Intent intent = new Intent(context, ExitSheetPackageActivity.class);
        intent.putExtra("exitSheetId", PreExitSheetList.get(position).Id + "");
        intent.putExtra("exitSheetDriverName", PreExitSheetList.get(position).VehicleDriverName + "");
        intent.putExtra("exitSheetVehicleNumber", PreExitSheetList.get(position).VehicleNumber+ "");
        intent.putExtra("exitSheetDate", PreExitSheetList.get(position).Date + "");
        intent.putExtra("exitSheetLogisticsCompany", PreExitSheetList.get(position).LogisticsCompany + "");
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
