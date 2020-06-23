package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.Model.ReturnSheetImage;
import com.interhandelsgmbh.storeroom.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ReturnPhotoListAdapter extends BaseAdapter {

    Context context;

    String barCode="";
    ArrayList<ReturnSheetImage> photoPathList = new ArrayList<>();
    LayoutInflater inflater;

    public ReturnPhotoListAdapter(Context context, ArrayList<ReturnSheetImage> photoPathList) {
        this.context = context;
        this.photoPathList = photoPathList;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return photoPathList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder viewHolder;


//        if(convertView == null){
        viewHolder = new ViewHolder();
        convertView = inflater.inflate(R.layout.item_return_photo, parent, false);
        viewHolder.imv_photo = convertView.findViewById(R.id.imv_photo);
        viewHolder.imv_Remove = convertView.findViewById(R.id.imv_Remove);



        if (photoPathList.get(position) != null && photoPathList.get(position).ImageBinary.length() > 0) {
            File logo = new File(photoPathList.get(position).ImageBinary);
            if (logo.exists()) {
                Picasso.with(context).load(logo)

                        .into(viewHolder.imv_photo, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
//                                        imv_coLogo.setImageResource(R.mipmap.ic_launcher);
                            }
                        });

            }
        }

        viewHolder.imv_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    confirm_remove(position);

            }
        });






        return convertView;
    }


    public class ViewHolder {
        ImageView imv_photo,imv_Remove;
    }


    /**
     * @return photoPaths from adapter
     */
    public ArrayList<ReturnSheetImage> getPhotosPathValue() {
        return photoPathList;
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
                dataBaseHandler.deleteReturnSheetImage(photoPathList.get(position).Id);
                photoPathList.remove(position);
                notifyDataSetChanged();
                confirm.dismiss();
                confirm.cancel();
            }
        });

        confirm.setCancelable(true);
        confirm.show();
    }



}
