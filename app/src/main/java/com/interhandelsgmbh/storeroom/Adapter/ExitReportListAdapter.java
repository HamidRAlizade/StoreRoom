package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.interhandelsgmbh.storeroom.Activity.SplashActivity;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.ExitSheetPackage;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExitReportListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ExitSheetPackage> exitSheetPackges = new ArrayList<>();
    ArrayList<String> postCodes = new ArrayList<>();


    ArrayList<String> goodsNumber = new ArrayList<>();
    ArrayList<String> goodsNumber0 = new ArrayList<>();
    LayoutInflater inflater;

    public ExitReportListAdapter(){}

    public ExitReportListAdapter(Context context, ArrayList<ExitSheetPackage> exitSheetPackges,ArrayList<String> postCodes,
    ArrayList<String> goodsNumber, ArrayList<String> goodsNumber0){

        this.context = context;
        this.postCodes = postCodes;
        this.exitSheetPackges = exitSheetPackges;
        this.goodsNumber = goodsNumber;
        this.goodsNumber0 = goodsNumber0;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return exitSheetPackges.size();
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
            convertView = inflater.inflate(R.layout.item_exit_report, parent, false);
            holder.tvOrderCode = convertView.findViewById(R.id.tvOrderCode);
            holder.tvPostCode = convertView.findViewById(R.id.tvPostCode);
            holder.ivRemove = convertView.findViewById(R.id.ivRemove);
            holder.tvPostGoodsNumber = convertView.findViewById(R.id.tvPostGoodsNumber);
            holder.tv_number = convertView.findViewById(R.id.tv_number);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvOrderCode.setTag(position);
        holder.tvPostCode.setTag(position);
        holder.ivRemove.setTag(position);
        holder.tvPostGoodsNumber.setTag(position);
        holder.tv_number.setTag(position);


        String order_barcode = context.getResources().getString(R.string.order_barcode);
        String delivery_number = context.getResources().getString(R.string.delivery_number);
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
            //&& delivery_number = 37
            //&& items = 32

            AppText order_barcodeText = dataBaseHandler.getAppText(38, languageId);
            AppText delivery_numberText = dataBaseHandler.getAppText(37, languageId);
            AppText itemsText = dataBaseHandler.getAppText(32, languageId);
            if (order_barcodeText != null) {
                order_barcode = (order_barcodeText.text);
            }
            if (delivery_numberText != null) {
                delivery_number = (delivery_numberText.text);
            }
            if (itemsText != null) {
                items = (itemsText.text);
            }
        }

        holder.tv_number.setText((position+1)+"");
        holder.tvOrderCode.setText(order_barcode+": "+exitSheetPackges.get(position).InvoiceBarcode);
        holder.tvPostCode.setText(delivery_number+": "+exitSheetPackges.get(position).DeliveryNumber);
        holder.tvPostGoodsNumber.setText(items+": \n"+goodsNumber.get(position));
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_remove(position);
            }
        });

        return convertView;

    }

    public class ViewHolder{

        TextView tvPostCode,tv_number;
        TextView tvOrderCode,tvPostGoodsNumber;
        ImageView ivRemove;

    }


    /**
     * get user confirm for removing this exitSheetPackage from database (TABLE_ExitSheetPackage)
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
                dataBaseHandler.deleteExitSheetPackage(exitSheetPackges.get(position));

                postCodes.remove(position);
                exitSheetPackges.remove(position);
                goodsNumber.remove(position);
                goodsNumber0.remove(position);
                notifyDataSetChanged();
                confirm.dismiss();
                confirm.cancel();
            }
        });

        confirm.setCancelable(true);
        confirm.show();
    }

}
