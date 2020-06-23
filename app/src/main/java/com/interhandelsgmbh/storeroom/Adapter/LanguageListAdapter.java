package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.interhandelsgmbh.storeroom.Activity.HelpActivity;
import com.interhandelsgmbh.storeroom.Activity.ReportActivity;
import com.interhandelsgmbh.storeroom.Class.ToolBar;
import com.interhandelsgmbh.storeroom.Model.Language;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class LanguageListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Language> languages = new ArrayList<>();

    Dialog dialog;

    LayoutInflater inflater;

    public LanguageListAdapter(){}

    public LanguageListAdapter(Context context, ArrayList<Language> languages , Dialog dialog){

        this.context = context;
        this.languages = languages;
        this.dialog = dialog;

        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return languages.size();
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

        SharedPreferences setting = context.getSharedPreferences("UserInfo", 0);
        if(setting.contains("LanguageId")){
            int id = Integer.parseInt(setting.getString("LanguageId","1"));
            if(languages.get(position).Id==id){
                holder.tv_help.setBackgroundResource(R.drawable.button_has_pdf);
            }

        }else{
            if(languages.get(position).Id==1){
                holder.tv_help.setBackgroundResource(R.drawable.button_has_pdf);
            }
        }



        holder.tv_help.setText(languages.get(position).language+"");

        holder.tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences setting = context.getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("LanguageId",languages.get(position).Id+"");
                editor.commit();
                editor.apply();
                dialog.dismiss();
                dialog.cancel();
                Intent intent = new Intent(context, ReportActivity.class);
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
