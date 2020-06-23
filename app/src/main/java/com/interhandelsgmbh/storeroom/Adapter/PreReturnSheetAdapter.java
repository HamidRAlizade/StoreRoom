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

import com.interhandelsgmbh.storeroom.Activity.ReturnSheetPackageActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.ReturnSheet;
import com.interhandelsgmbh.storeroom.R;

import java.io.File;
import java.util.ArrayList;

public class PreReturnSheetAdapter extends BaseAdapter {

    Context context;
    ArrayList<ReturnSheet> returnSheetList = new ArrayList<>();
    ArrayList<Integer> picBack = new ArrayList<>();
    LayoutInflater inflater;

    Dialog dialog;

    public PreReturnSheetAdapter() {
    }

    public PreReturnSheetAdapter(Context context, ArrayList<ReturnSheet> returnSheetList, Dialog dialog) {

        this.context = context;
        this.dialog = dialog;
        this.returnSheetList = returnSheetList;

        for (int i = 0; i < returnSheetList.size(); i++) {
            if (returnSheetList.get(i).IsDone == 1) {
                picBack.add(R.drawable.button_has_pdf);
            } else {
                picBack.add(R.drawable.button_no_pdf);
            }
        }


        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return returnSheetList.size();
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

        String receiver = context.getResources().getString(R.string.receiver);
        String post_barcode = context.getResources().getString(R.string.post_barcode);
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
            //&& date = 117
            //&& receiver = 118
            //&& post_barcode = 92


            AppText receiverText = dataBaseHandler.getAppText(118, languageId);
            AppText post_barcodeText = dataBaseHandler.getAppText(92, languageId);
            AppText dateText = dataBaseHandler.getAppText(117, languageId);

            if (receiverText != null) {
                receiver = (receiverText.text);
            }
            if (post_barcodeText != null) {
                post_barcode = (post_barcodeText.text);
            }
            if (dateText != null) {
                date = (dateText.text);
            }
        }

        holder.tv_preExitSheet.setText(receiver+": "+returnSheetList.get(position).ReceiverName + "\n" + post_barcode+": " + returnSheetList.get(position).PostBarcode+"\n"+date+": "+returnSheetList.get(position).Date);

        holder.tv_preExitSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (returnSheetList.get(position).IsDone == 0) {
                    goToContinueReturnSheetItem(position);
                }else{
                    openPDF(returnSheetList.get(position).PdfPath);
                }
            }
        });

        return convertView;

    }

    public class ViewHolder {

        TextView tv_preExitSheet;

    }


    /**
     * intent this Return sheet info into CountingSheetActivity
     * @param position
     */
    public void goToContinueReturnSheetItem(int position) {

        dialog.dismiss();
        dialog.cancel();
        Intent intent = new Intent(context, ReturnSheetPackageActivity.class);
        intent.putExtra("returnSheetId", returnSheetList.get(position).Id + "");
        intent.putExtra("returnSheetReceiverName", returnSheetList.get(position).ReceiverName + "");
        intent.putExtra("returnSheetVehicleDriverName", returnSheetList.get(position).VehicleDriverName+ "");
        intent.putExtra("returnSheetVehicleNumber", returnSheetList.get(position).VehicleNumber+ "");
        intent.putExtra("returnSheetPostBarcode", returnSheetList.get(position).PostBarcode+ "");
        intent.putExtra("returnSheetDate", returnSheetList.get(position).Date + "");
        intent.putExtra("returnSheetLogisticsCompany", returnSheetList.get(position).LogisticsCompany + "");
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
