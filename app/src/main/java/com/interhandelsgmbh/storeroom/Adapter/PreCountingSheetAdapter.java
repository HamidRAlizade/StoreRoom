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

import com.interhandelsgmbh.storeroom.Activity.CountingSheetItemActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.R;

import java.io.File;
import java.util.ArrayList;

public class PreCountingSheetAdapter extends BaseAdapter {

    Context context;
    ArrayList<CountingSheet> PreCountingSheetList = new ArrayList<>();
    ArrayList<Integer> picBack = new ArrayList<>();
    LayoutInflater inflater;

    Dialog dialog;

    public PreCountingSheetAdapter() {
    }

    public PreCountingSheetAdapter(Context context, ArrayList<CountingSheet> preCountingSheetList,Dialog dialog) {

        this.context = context;
        this.dialog = dialog;
        this.PreCountingSheetList = preCountingSheetList;

        for (int i = 0; i < PreCountingSheetList.size(); i++) {
            if (PreCountingSheetList.get(i).IsDone == 1) {
                picBack.add(R.drawable.button_has_pdf);
            } else {
                picBack.add(R.drawable.button_no_pdf);
            }
        }


        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return PreCountingSheetList.size();
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
            holder.tv_preCountingSheet = convertView.findViewById(R.id.tv_preCountingSheet);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_preCountingSheet.setTag(position);


        holder.tv_preCountingSheet.setBackgroundResource(picBack.get(position));

        String counting_date = context.getResources().getString(R.string.counting_date);

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
            //&& counting_date = 113


            AppText counting_dateText = dataBaseHandler.getAppText(113, languageId);

            if (counting_dateText != null) {
                counting_date = (counting_dateText.text);
            }
        }

        holder.tv_preCountingSheet.setText(PreCountingSheetList.get(position).CounterName + "\n" + counting_date+": " + PreCountingSheetList.get(position).CountingDate);

        holder.tv_preCountingSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreCountingSheetList.get(position).IsDone == 0) {
                    goToContinueCountingSheetItem(position);
                }else{
                    openPDF(PreCountingSheetList.get(position).PdfPath);
                }
            }
        });

        return convertView;

    }

    public class ViewHolder {

        TextView tv_preCountingSheet;

    }


    /**
     * intent this counting sheet info into CountingSheetActivity
     * @param position
     */
    public void goToContinueCountingSheetItem(int position) {

        dialog.dismiss();
        dialog.cancel();
        Intent intent = new Intent(context, CountingSheetItemActivity.class);
        intent.putExtra("countingSheetId", PreCountingSheetList.get(position).Id + "");
        intent.putExtra("countingSheetName", PreCountingSheetList.get(position).CounterName + "");
        intent.putExtra("countingSheetDate", PreCountingSheetList.get(position).CountingDate + "");
        intent.putExtra("countingSheetLogisticsCompany", PreCountingSheetList.get(position).LogisticsCompany + "");
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

        File file = new File(filePath);
        Uri attachmentUri = FileProvider.getUriForFile(context, "com.freshdesk.helpdesk.interstoreroom", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "Error! Can't open file.", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, "application/pdf");
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "Error! Can't open file.", Toast.LENGTH_LONG).show();
            }
        }
    }


}
