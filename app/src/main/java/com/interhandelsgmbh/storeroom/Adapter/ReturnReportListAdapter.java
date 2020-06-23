package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
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

import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.EntranceSheetPackage;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetPackage;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class ReturnReportListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ReturnSheetPackage> returnSheetPackages = new ArrayList<>();
    ArrayList<String> itemBarcodes = new ArrayList<>();
    LayoutInflater inflater;

    public ReturnReportListAdapter(){}

    public ReturnReportListAdapter(Context context, ArrayList<ReturnSheetPackage> returnSheetPackages,
                                   ArrayList<String> itemBarcodes){

        this.context = context;
        this.returnSheetPackages = returnSheetPackages;
        this.itemBarcodes = itemBarcodes;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return returnSheetPackages.size();
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

        String order_barcode = context.getResources().getString(R.string.order_barcode);
        String items = context.getResources().getString(R.string.items);

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
            //&& order_barcode = 38
            //&& items = 32

            AppText order_barcodeText = dataBaseHandler.getAppText(38, languageId);
            AppText itemsText = dataBaseHandler.getAppText(32, languageId);

            if (order_barcodeText != null) {
                order_barcode = (order_barcodeText.text);
            }
            if (itemsText != null) {
                items = (itemsText.text);
            }
        }


        holder.tv_number.setText((position+1)+"");
        holder.tv_packageCode.setText(order_barcode+": "+returnSheetPackages.get(position).OrderBarcode);
        holder.tv_itemsBarcode.setText(items+": \n"+itemBarcodes.get(position));
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
     * get user confirm for picture from list
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
                dataBaseHandler.deleteReturnSheetPackage(returnSheetPackages.get(position));

                itemBarcodes.remove(position);
                returnSheetPackages.remove(position);
                notifyDataSetChanged();
                confirm.dismiss();
                confirm.cancel();
            }
        });

        confirm.setCancelable(true);
        confirm.show();
    }

}
