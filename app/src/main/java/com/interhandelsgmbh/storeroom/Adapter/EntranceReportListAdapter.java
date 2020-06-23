package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.interhandelsgmbh.storeroom.Activity.ImportActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackage;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class EntranceReportListAdapter extends BaseAdapter {

    Context context;
    ArrayList<EntranceSheetPackage> entranceSheetPackages = new ArrayList<>();
    ArrayList<String> itemBarcodes = new ArrayList<>();
    LayoutInflater inflater;

    public EntranceReportListAdapter(){}

    public EntranceReportListAdapter(Context context, ArrayList<EntranceSheetPackage> entranceSheetPackages,
                                     ArrayList<String> itemBarcodes){

        this.context = context;
        this.entranceSheetPackages = entranceSheetPackages;
        this.itemBarcodes = itemBarcodes;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return entranceSheetPackages.size();
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
            convertView = inflater.inflate(R.layout.item_entrance_report, parent, false);
            holder.tv_itemsBarcode = convertView.findViewById(R.id.tv_itemsBarcode);
            holder.tv_packageCode = convertView.findViewById(R.id.tv_packageCode);
            holder.ivRemove = convertView.findViewById(R.id.ivRemove);
            holder.tv_number = convertView.findViewById(R.id.tv_number);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_packageCode.setTag(position);
        holder.tv_itemsBarcode.setTag(position);
        holder.ivRemove.setTag(position);
        holder.tv_number.setTag(position);


        holder.tv_number.setText((position+1)+"");
        String pack_barcode = context.getResources().getString(R.string.pack_barcode);
        String Items = context.getResources().getString(R.string.items);
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
            //&& pack_barcode = 111
            //&& items = 32

            AppText pack_barcodeText = dataBaseHandler.getAppText(111, languageId);
            AppText itemsText = dataBaseHandler.getAppText(32, languageId);
            if (pack_barcodeText != null) {
                pack_barcode = (pack_barcodeText.text);
            }
            if (itemsText != null) {
                Items = (itemsText.text);
            }


        }


        holder.tv_packageCode.setText(pack_barcode+": "+entranceSheetPackages.get(position).PackageNumber);
        holder.tv_itemsBarcode.setText(Items+": \n"+itemBarcodes.get(position));
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_remove(position);
            }
        });

        return convertView;

    }

    public class ViewHolder{

        TextView tv_number;
        TextView tv_packageCode,tv_itemsBarcode;
        ImageView ivRemove;

    }


    /**
     * get confirm from user for remove entrance package
     * @param position
     */
    public void confirm_remove(final int position) {

        final Dialog confirm = new Dialog(context);
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.dialog_remove_confirm);
        confirm.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        Button bt_no = confirm.findViewById(R.id.bt_no);
        Button bt_ok = confirm.findViewById(R.id.bt_ok);

        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.dismiss();
                confirm.cancel();
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                dataBaseHandler.deleteEntranceSheetPackage(entranceSheetPackages.get(position));

                itemBarcodes.remove(position);
                entranceSheetPackages.remove(position);
                notifyDataSetChanged();
                confirm.dismiss();
                confirm.cancel();
            }
        });

        confirm.setCancelable(true);
        confirm.show();
    }

}
