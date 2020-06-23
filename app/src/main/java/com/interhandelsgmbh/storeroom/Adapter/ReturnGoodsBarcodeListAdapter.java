package com.interhandelsgmbh.storeroom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.Goods;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class ReturnGoodsBarcodeListAdapter extends BaseAdapter {

    Context context;

    String barCode = "";
    ArrayList<Goods> goodsBarCodeList = new ArrayList<>();
    ArrayList<String> commentList = new ArrayList<>();
    ArrayList<Integer> isEnableList = new ArrayList<>();
    ArrayList<Integer> getPic = new ArrayList<>();
    LayoutInflater inflater;
    ListView listView;

    public ReturnGoodsBarcodeListAdapter(Context context, ListView listView, ArrayList<Goods> goodsBarCodeList,
                                         ArrayList<String> commentList, ArrayList<Integer> isEnableList) {
        this.context = context;
        this.listView = listView;
        this.commentList = commentList;
        this.goodsBarCodeList = goodsBarCodeList;
        this.isEnableList = isEnableList;

        for (int i = 0; i < isEnableList.size(); i++) {
            if (isEnableList.get(i) == 0) {
                getPic.add(R.drawable.remove);
            } else {
                getPic.add(R.drawable.remove);
            }
        }

        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return goodsBarCodeList.size();
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
        convertView = inflater.inflate(R.layout.item_return_goods, parent, false);
        viewHolder.et_itemBarcode = convertView.findViewById(R.id.et_itemBarcode);
        viewHolder.tv_itemName = convertView.findViewById(R.id.tv_itemName);
        viewHolder.et_itemComment = convertView.findViewById(R.id.et_itemComment);
        viewHolder.imv_Remove = convertView.findViewById(R.id.imv_Remove);
        viewHolder.ll_add = convertView.findViewById(R.id.ll_add);
        viewHolder.et_itemBarcode.setText(goodsBarCodeList.get(position).Barocde);
        viewHolder.tv_itemName.setText(goodsBarCodeList.get(position).Name);
        viewHolder.et_itemComment.setText(commentList.get(position));


        viewHolder.tv_barcodeText = convertView.findViewById(R.id.tv_barcodeText);
        viewHolder.tv_nameText = convertView.findViewById(R.id.tv_nameText);
        viewHolder.tv_commentText = convertView.findViewById(R.id.tv_commentText);
        viewHolder.tv_addNewItem = convertView.findViewById(R.id.tv_addNewItem);

        String barcode, name, comment, add_this_item;
        barcode = context.getResources().getString(R.string.barcode);
        name = context.getResources().getString(R.string.name);
        comment = context.getResources().getString(R.string.comment);
        add_this_item = context.getResources().getString(R.string.add_this_item);

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
            //&& barcode = 11
            //&& name = 12
            //&& comment = 121
            //&& add_this_item = 122

            AppText barcodeText = dataBaseHandler.getAppText(11, languageId);
            AppText nameText = dataBaseHandler.getAppText(12, languageId);
            AppText commentText = dataBaseHandler.getAppText(121, languageId);
            AppText add_this_itemText = dataBaseHandler.getAppText(122, languageId);

            if (barcodeText != null) barcode = (barcodeText.text);
            if (nameText != null) name = (nameText.text);
            if (commentText != null) comment = (commentText.text);
            if (add_this_itemText != null) add_this_item = (add_this_itemText.text);

        }


        viewHolder.tv_barcodeText.setText(barcode);
        viewHolder.tv_nameText.setText(name);
        viewHolder.tv_commentText.setText(comment);
        viewHolder.tv_addNewItem.setText(add_this_item);


        if (isEnableList.get(position) == 1) {
            viewHolder.et_itemBarcode.setEnabled(true);
            viewHolder.et_itemBarcode.requestFocus();
            viewHolder.et_itemComment.clearFocus();
            viewHolder.et_itemComment.setEnabled(false);
            viewHolder.ll_add.setVisibility(View.GONE);

        } else if (isEnableList.get(position) == 2) {
            viewHolder.et_itemBarcode.setEnabled(false);
            viewHolder.et_itemBarcode.clearFocus();
            viewHolder.et_itemComment.setEnabled(true);
            viewHolder.et_itemComment.requestFocus();
            viewHolder.ll_add.setVisibility(View.VISIBLE);
        } else {
            viewHolder.et_itemBarcode.setEnabled(false);
            viewHolder.et_itemComment.setEnabled(false);
            viewHolder.ll_add.setVisibility(View.GONE);
        }


        viewHolder.imv_Remove.setImageResource((getPic.get(position)));

        viewHolder.ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnableList.get(position) == 2) {
                    String comment = viewHolder.et_itemComment.getText().toString();
                    if (comment.length() > 0) {
                        commentList.set(position, comment);
                        isEnableList.set(position, 0);
                        getPic.set(position, R.drawable.remove);
                        Goods goods = new Goods(0, "", "", 0);
                        commentList.add("");
                        isEnableList.add(1);
                        goodsBarCodeList.add(goods);
                        getPic.add(R.drawable.remove);
                        notifyDataSetChanged();

                    } else {
                        String write_comment_for_this_item = context.getResources().getString(R.string.write_comment_for_this_item);
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
                            //&& write_comment_for_this_item = 120
                            AppText write_comment_for_this_itemText = dataBaseHandler.getAppText(120, languageId);

                            if (write_comment_for_this_itemText != null) {
                                write_comment_for_this_item = (write_comment_for_this_itemText.text);
                            }
                        }

                        Toast.makeText(context, write_comment_for_this_item, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        viewHolder.imv_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnableList.get(position) == 0) {
                    confirm_remove(position);
                } else {
                    String read_barcode_in_return = context.getResources().getString(R.string.read_barcode_in_return);
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
                        //&& read_barcode_in_return = 119
                        AppText read_barcode_in_returnText = dataBaseHandler.getAppText(119, languageId);

                        if (read_barcode_in_returnText != null) {
                            read_barcode_in_return = (read_barcode_in_returnText.text);
                        }
                    }

                    Toast.makeText(context, read_barcode_in_return, Toast.LENGTH_LONG).show();
                }
            }
        });


        if (isEnableList.get(position) == 1) {
            listView.setSelection(position);

            viewHolder.et_itemBarcode.requestFocus();
            viewHolder.et_itemBarcode.setId(position);
            viewHolder.et_itemBarcode.requestFocus();


            final int TYPING_TIMEOUT = 500; // 5 seconds timeout
            final Handler timeoutHandler = new Handler();
            final Runnable typingTimeout = new Runnable() {
                public void run() {
                    serviceCall(position, goodsBarCodeList, isEnableList);
                }
            };


            viewHolder.et_itemBarcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    final int pos = viewHolder.et_itemBarcode.getId();
                    final String number = viewHolder.et_itemBarcode.getText().toString();
                    timeoutHandler.removeCallbacks(typingTimeout);

                    if (isEnableList.get(position) == 1) {
                        if (number.length() > 3) {
                            Log.e("last 2 number : ", number.substring(number.length() - 2, number.length()) + "");
                        }

                        if (number.length() > 12 && number.substring(number.length() - 2, number.length()).equals("13")) {

                            if (isEnableList.get(position) == 1) {
                                Log.e("finish : ", number.substring(number.length() - 2, number.length() - 1) + "");
                                barCode = number.substring(0, number.length() - 2);

                                timeoutHandler.postDelayed(typingTimeout, TYPING_TIMEOUT);

                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

        }


        return convertView;
    }


    public class ViewHolder {
        LinearLayout ll_add;
        EditText et_itemBarcode, et_itemComment;
        ImageView imv_Remove;
        TextView tv_itemName, tv_barcodeText, tv_nameText, tv_commentText, tv_addNewItem;
    }

    /**
     * @return comment of goods from adapter
     */
    public ArrayList<String> getCommentValue() {
        return commentList;
    }

    /**
     * @return goods barcode value from adapter
     */
    public ArrayList<Goods> getGoodsBarCodesValue() {
        return goodsBarCodeList;
    }


    /**
     * action after barcode typing finished
     *
     * @param pos
     * @param goodsBarCodeList
     * @param isEnableList
     */
    public void serviceCall(int pos, ArrayList<Goods> goodsBarCodeList, ArrayList<Integer> isEnableList) {

        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        Goods goods = dataBaseHandler.getGoodsWithBarcode(barCode);
        Log.e("goods barcode", barCode + "");
        Log.e("goods detected", goods + "");

        if (goods.Id > 0) {
            isEnableList.set(pos, 2);
            goodsBarCodeList.set(pos, goods);
            notifyDataSetChanged();

        } else {
            isEnableList.set(pos, 0);
            Goods goods1 = new Goods(0, "", barCode, 0);
            goodsBarCodeList.set(pos, goods1);
            addNewGoodsIntoDatabase(pos, barCode);
        }


    }


    /**
     * add new items info into database
     *
     * @param pos     : position
     * @param barcode
     */
    public void addNewGoodsIntoDatabase(final int pos, final String barcode) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_new_goods);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        final TextView tv_addNewItem = dialog.findViewById(R.id.tv_addNewItem);
        final TextView tv_barcode = dialog.findViewById(R.id.tv_barcode);
        final TextView tv_name = dialog.findViewById(R.id.tv_name);
        final TextView tv_goodsBarCode = dialog.findViewById(R.id.tv_goodsBarCode);
        final EditText et_GoodsName = dialog.findViewById(R.id.et_GoodsName);
        final Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final Button bt_add = dialog.findViewById(R.id.bt_add);

        String barcode_text, name, add, addNewItem, cancel;

        barcode_text = context.getResources().getString(R.string.barcode);
        name = context.getResources().getString(R.string.name);
        add = context.getResources().getString(R.string.add);
        addNewItem = context.getResources().getString(R.string.add_new_item);
        cancel = context.getResources().getString(R.string.cancel);


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
            //&& barcode = 11
            //&& name = 12
            //&& add = 14
            //&& addNewItem = 18
            //&& cancel = 19

            AppText barcodeText = dataBaseHandler.getAppText(11, languageId);
            AppText nameText = dataBaseHandler.getAppText(12, languageId);
            AppText addText = dataBaseHandler.getAppText(14, languageId);
            AppText addNewItemText = dataBaseHandler.getAppText(18, languageId);
            AppText cancelText = dataBaseHandler.getAppText(19, languageId);

            if (barcodeText != null) {
                barcode_text = (barcodeText.text);
            }
            if (nameText != null) {
                name = (nameText.text);
            }
            if (addText != null) {
                add = (addText.text);
            }
            if (addNewItemText != null) {
                addNewItem = (addNewItemText.text);
            }
            if (cancelText != null) {
                cancel = (cancelText.text);
            }

        }
        tv_addNewItem.setText(addNewItem);
        tv_barcode.setText(barcode_text);
        tv_name.setText(name);
        bt_cancel.setText(cancel);
        bt_add.setText(add);


        tv_goodsBarCode.setText(barcode);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isEnableList.set(pos, 1);
                Goods goods1 = new Goods(0, "", "", 0);
                goodsBarCodeList.set(pos, goods1);
                notifyDataSetChanged();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_GoodsName.getText().toString();
                if (name.length() > 0) {
                    Goods goods = new Goods(1, name, barcode, 0);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                    int goodsId = (int) dataBaseHandler.addGoods(goods);
                    Goods goods1 = new Goods(goodsId, name, barcode, 0);
                    isEnableList.set(pos, 2);
                    goodsBarCodeList.set(pos, goods1);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    dialog.cancel();
                } else {
                    String enter_name_of_item = context.getResources().getString(R.string.enter_name_of_item);
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
                        //&& enter_name_of_item = 112
                        AppText enter_name_of_itemText = dataBaseHandler.getAppText(112, languageId);

                        if (enter_name_of_itemText != null) {
                            enter_name_of_item = (enter_name_of_itemText.text);
                        }
                    }

                    Toast.makeText(context, enter_name_of_item, Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }


    /**
     * get user confirm for removing item from list
     *
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
                goodsBarCodeList.remove(position);
                isEnableList.remove(position);
                commentList.remove(position);
                getPic.remove(position);
                notifyDataSetChanged();
                confirm.dismiss();
                confirm.cancel();
            }
        });

        confirm.setCancelable(true);
        confirm.show();
    }


}
