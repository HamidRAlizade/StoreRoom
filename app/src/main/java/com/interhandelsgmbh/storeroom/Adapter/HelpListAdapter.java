package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.interhandelsgmbh.storeroom.Activity.ExitSheetPackageActivity;
import com.interhandelsgmbh.storeroom.Activity.HelpActivity;
import com.interhandelsgmbh.storeroom.Class.ToolBar;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.CountingSheetItem;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class HelpListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ToolBar.HelpItem> helpItems = new ArrayList<>();


    LayoutInflater inflater;

    public HelpListAdapter(){}

    public HelpListAdapter(Context context, ArrayList<ToolBar.HelpItem> helpItems ){

        this.context = context;
        this.helpItems = helpItems;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return helpItems.size();
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
            holder.tv_help = convertView.findViewById(R.id.tv_preCountingSheet);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_help.setTag(position);


        holder.tv_help.setBackgroundResource(R.drawable.button_has_pdf);


        holder.tv_help.setText(helpItems.get(position).helpText+"");

        holder.tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HelpActivity.class);
                intent.putExtra("helpId", helpItems.get(position).Id + "");
                intent.putExtra("helpTitle", helpItems.get(position).helpText + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        return convertView;

    }

    public class ViewHolder {

        TextView tv_help;

    }




}
