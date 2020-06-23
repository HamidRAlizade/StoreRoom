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
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.Model.CountingSheetItem;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class PreCountingSheetItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<CountingSheetItem> countingSheetItemList = new ArrayList<>();
    ArrayList<Integer> countingSheetItemGoodsId = new ArrayList<>();

    LayoutInflater inflater;

    public PreCountingSheetItemAdapter(){}

    public PreCountingSheetItemAdapter(Context context, ArrayList<CountingSheetItem> countingSheetItemList,ArrayList<Integer> countingSheetItemGoodsId ){

        this.context = context;
        this.countingSheetItemList = countingSheetItemList;
        this.countingSheetItemGoodsId = countingSheetItemGoodsId;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return countingSheetItemList.size();
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
            convertView = inflater.inflate(R.layout.item_counting_sheet_item, parent, false);
            holder.tv_index = convertView.findViewById(R.id.tv_index);
            holder.tv_goodsName = convertView.findViewById(R.id.tv_goodsName);
            holder.tv_goodsBarcode = convertView.findViewById(R.id.tv_goodsBarcode);
            holder.tv_goodsNumber = convertView.findViewById(R.id.tv_goodsNumber);
            holder.imv_goodsRemove = convertView.findViewById(R.id.imv_goodsRemove);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_index.setTag(position);
        holder.tv_goodsName.setTag(position);
        holder.tv_goodsBarcode.setTag(position);
        holder.tv_goodsNumber.setTag(position);
        holder.imv_goodsRemove.setTag(position);
        holder.tv_index.setText((position+1)+"");

        String item_name = context.getResources().getString(R.string.item_name);
        String item_barcode = context.getResources().getString(R.string.item_barcode);
        String item_amount = context.getResources().getString(R.string.item_amount);

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
            //&& item_name = 114
            //&& item_barcode = 115
            //&& item_amount = 116


            AppText item_nameText = dataBaseHandler.getAppText(114, languageId);
            AppText item_barcodeText = dataBaseHandler.getAppText(115, languageId);
            AppText item_amountText = dataBaseHandler.getAppText(116, languageId);

            if (item_nameText != null) {
                item_name = (item_nameText.text);
            }
            if (item_barcodeText != null) {
                item_barcode = (item_barcodeText.text);
            }
            if (item_amountText != null) {
                item_amount = (item_amountText.text);
            }
        }


        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        Goods goods = dataBaseHandler.getGoods(countingSheetItemList.get(position).GoodsId);
        holder.tv_goodsName.setText(item_name+": "+goods.Name);
        holder.tv_goodsBarcode.setText(item_barcode+": "+goods.Barocde);
        holder.tv_goodsNumber.setText(item_amount+": "+countingSheetItemList.get(position).Number);

        holder.imv_goodsRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_remove(position);
            }
        });


        return convertView;

    }

    public class ViewHolder{

        TextView tv_index,tv_goodsName,tv_goodsBarcode,tv_goodsNumber;
        ImageView imv_goodsRemove;
    }


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
                dataBaseHandler.deleteCountingSheetItem(countingSheetItemList.get(position));
                countingSheetItemList.remove(position);
                countingSheetItemGoodsId.remove(position);
                notifyDataSetChanged();
                confirm.dismiss();
                confirm.cancel();
            }
        });

        confirm.setCancelable(true);
        confirm.show();
    }


}
