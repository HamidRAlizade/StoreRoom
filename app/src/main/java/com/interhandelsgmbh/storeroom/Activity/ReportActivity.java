package com.interhandelsgmbh.storeroom.Activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interhandelsgmbh.storeroom.Adapter.ExitGoodsBarcodeListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.ExitReportListAdapter;
import com.interhandelsgmbh.storeroom.Adapter.PreCountingSheetAdapter;
import com.interhandelsgmbh.storeroom.Adapter.PreEntranceSheetAdapter;
import com.interhandelsgmbh.storeroom.Adapter.PreExitSheetAdapter;
import com.interhandelsgmbh.storeroom.Adapter.PreReturnSheetAdapter;
import com.interhandelsgmbh.storeroom.Class.ToolBar;
import com.interhandelsgmbh.storeroom.Class.UpdateReceiver;
import com.interhandelsgmbh.storeroom.Class.postData;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AllData;
import com.interhandelsgmbh.storeroom.Model.AllDataLastIndex;
import com.interhandelsgmbh.storeroom.Model.AllDataString;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.Model.EntranceSheet;
import com.interhandelsgmbh.storeroom.Model.ExitSheet;
import com.interhandelsgmbh.storeroom.Model.ReturnSheet;
import com.interhandelsgmbh.storeroom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReportActivity extends AppCompatActivity {


    Boolean canRemove13 = true;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    String actions, entries_of_item, outgoing_item, item_counting, return_text;
    String counting,has_counting_dialog,no_counting_title,add_new_counting_process;
    String new_counting_info,counter_name,done,enter_name_counting_error,add_counting_success,logistics_company_co;
    String outgoing,has_outgoing_dialog,no_outgoing_dialog,add_new_outgoing_process;
    String new_outgoing_info,driver_name,vehicle_number,new_outgoing_data_error,add_outgoing_success;
    String entrance_item,no_entrace_data,has_entrance_data,add_new_entrance_process;
    String new_entrance_info,user_name,entrance_data_error,add_entrance_success;
    String no_return_dialog,has_return_dialog,add_new_return_process;
    String new_return_info,post_barcode,receiver_name,vehicle_driver_name,return_info_erron,return_add_success;

    public void setViewText() throws UnsupportedEncodingException {
        TextView tv_actions = findViewById(R.id.tv_actions);
        TextView tvEnterance = findViewById(R.id.tvEnterance);
        TextView tvExit = findViewById(R.id.tvExit);
        TextView tvControl = findViewById(R.id.tvControl);
        TextView tvReturned = findViewById(R.id.tvReturned);



        actions = getResources().getString(R.string.actions);
        entries_of_item = getResources().getString(R.string.entries_of_item);
        outgoing_item = getResources().getString(R.string.outgoing_item);
        item_counting = getResources().getString(R.string.item_counting);
        return_text = getResources().getString(R.string.return_text);
        counting = getResources().getString(R.string.counting);
        no_counting_title = getResources().getString(R.string.no_counting_title);
        has_counting_dialog = getResources().getString(R.string.has_counting_dialog);
        add_new_counting_process = getResources().getString(R.string.add_new_counting_process);
        new_counting_info = getResources().getString(R.string.new_counting_info);
        counter_name = getResources().getString(R.string.counter_name);
        logistics_company_co =  getResources().getString(R.string.logistics_company);
        done = getResources().getString(R.string.done);
        enter_name_counting_error = getResources().getString(R.string.enter_name_counting_error);
        add_counting_success = getResources().getString(R.string.add_counting_success);
        outgoing = getResources().getString(R.string.outgoing);
        has_outgoing_dialog = getResources().getString(R.string.has_outgoing_dialog);
        no_outgoing_dialog = getResources().getString(R.string.no_outgoing_dialog);
        add_new_outgoing_process= getResources().getString(R.string.add_new_outgoing_process);
        new_outgoing_info= getResources().getString(R.string.new_outgoing_info);
        driver_name= getResources().getString(R.string.driver_name);
        vehicle_number= getResources().getString(R.string.vehicle_number);
        new_outgoing_data_error= getResources().getString(R.string.new_outgoing_data_error);
        add_outgoing_success= getResources().getString(R.string.add_outgoing_success);
        entrance_item= getResources().getString(R.string.entrance_item);
        no_entrace_data= getResources().getString(R.string.no_entrace_data);
        has_entrance_data= getResources().getString(R.string.has_entrance_data);
        add_new_entrance_process= getResources().getString(R.string.add_new_entrance_process);
        new_entrance_info= getResources().getString(R.string.new_entrance_info);
        user_name= getResources().getString(R.string.user_name);
        entrance_data_error= getResources().getString(R.string.entrance_data_error);
        add_entrance_success= getResources().getString(R.string.add_entrance_success);
        no_return_dialog= getResources().getString(R.string.no_return_dialog);
        has_return_dialog= getResources().getString(R.string.has_return_dialog);
        add_new_return_process= getResources().getString(R.string.add_new_return_process);
        new_return_info= getResources().getString(R.string.new_return_info);
        post_barcode= getResources().getString(R.string.post_barcode);
        receiver_name= getResources().getString(R.string.receiver_name);
        vehicle_driver_name= getResources().getString(R.string.vehicle_driver_name);
        return_info_erron= getResources().getString(R.string.return_info_erron);
        return_add_success= getResources().getString(R.string.return_add_success);



        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& actions = 59
            //&& entries_of_item = 60
            //&& outgoing_item = 61
            //&& item_counting = 62
            //&& return_text = 63

            //&& counting = 64
            //&& no_counting_title = 65
            //&& has_counting_dialog = 66
            //&& add_new_counting_process = 67
            //&& new_counting_info = 68
            //&& counter_name = 69
            //&& done = 17
            //&& enter_name_counting_error = 70
            //&& add_counting_success = 71
            //&& outgoing = 72
            //&& has_outgoing_dialog = 73
            //&& no_outgoing_dialog = 74
            //&& add_new_outgoing_process = 75
            //&& new_outgoing_info = 76
            //&& driver_name = 77
            //&& vehicle_number = 78
            //&& new_outgoing_data_error = 79
            //&& add_outgoing_success = 80
            //&& entrance_item = 81
            //&& no_entrace_data = 82
            //&& has_entrance_data = 83
            //&& add_new_entrance_process = 84
            //&& new_entrance_info = 85
            //&& user_name = 30
            //&& entrance_data_error = 86
            //&& add_entrance_success = 87
            //&& no_return_dialog = 88
            //&& has_return_dialog = 89
            //&& add_new_return_process = 90
            //&& new_return_info = 91
            //&& post_barcode = 92
            //&& receiver_name = 93
            //&& vehicle_driver_name = 94
            //&& return_info_erron = 95
            //&& return_add_success = 96



            AppText actionsText = dataBaseHandler.getAppText(59, languageId);
            AppText entries_of_itemText = dataBaseHandler.getAppText(60, languageId);
            AppText outgoing_itemText = dataBaseHandler.getAppText(61, languageId);
            AppText item_countingText = dataBaseHandler.getAppText(62, languageId);
            AppText return_textText = dataBaseHandler.getAppText(63, languageId);
            AppText countingText = dataBaseHandler.getAppText(64, languageId);
            AppText no_counting_titleText = dataBaseHandler.getAppText(65, languageId);
            AppText has_counting_dialogText = dataBaseHandler.getAppText(66, languageId);
            AppText add_new_counting_processText = dataBaseHandler.getAppText(67, languageId);
            AppText new_counting_infoText = dataBaseHandler.getAppText(68, languageId);
            AppText counter_nameText = dataBaseHandler.getAppText(69, languageId);
            AppText et_logistics_company_coText = dataBaseHandler.getAppText(69, languageId);
            AppText doneText = dataBaseHandler.getAppText(17, languageId);
            AppText enter_name_counting_errorText = dataBaseHandler.getAppText(70, languageId);
            AppText add_counting_successText = dataBaseHandler.getAppText(71, languageId);
            AppText outgoingText = dataBaseHandler.getAppText(72, languageId);
            AppText has_outgoing_dialogText = dataBaseHandler.getAppText(73, languageId);
            AppText no_outgoing_dialogText = dataBaseHandler.getAppText(74, languageId);
            AppText add_new_outgoing_processText = dataBaseHandler.getAppText(75, languageId);
            AppText new_outgoing_infoText = dataBaseHandler.getAppText(76, languageId);
            AppText driver_nameText = dataBaseHandler.getAppText(77, languageId);
            AppText vehicle_numberText = dataBaseHandler.getAppText(78, languageId);
            AppText new_outgoing_data_errorText = dataBaseHandler.getAppText(79, languageId);
            AppText add_outgoing_successText = dataBaseHandler.getAppText(80, languageId);
            AppText entrance_itemText = dataBaseHandler.getAppText(81, languageId);
            AppText no_entrace_dataText = dataBaseHandler.getAppText(82, languageId);
            AppText has_entrance_dataText = dataBaseHandler.getAppText(83, languageId);
            AppText add_new_entrance_processText = dataBaseHandler.getAppText(84, languageId);
            AppText new_entrance_infoText = dataBaseHandler.getAppText(85, languageId);
            AppText user_nameText = dataBaseHandler.getAppText(30, languageId);
            AppText entrance_data_errorText = dataBaseHandler.getAppText(86, languageId);
            AppText add_entrance_successText = dataBaseHandler.getAppText(87, languageId);
            AppText no_return_dialogText = dataBaseHandler.getAppText(88, languageId);
            AppText has_return_dialogText = dataBaseHandler.getAppText(89, languageId);
            AppText add_new_return_processText = dataBaseHandler.getAppText(90, languageId);
            AppText new_return_infoText = dataBaseHandler.getAppText(91, languageId);
            AppText post_barcodeText = dataBaseHandler.getAppText(92, languageId);
            AppText receiver_nameText = dataBaseHandler.getAppText(93, languageId);
            AppText vehicle_driver_nameText = dataBaseHandler.getAppText(94, languageId);
            AppText return_info_erronText = dataBaseHandler.getAppText(95, languageId);
            AppText return_add_successText = dataBaseHandler.getAppText(96, languageId);



            if (actionsText != null) actions = (actionsText.text);

            if (entries_of_itemText != null) entries_of_item = (entries_of_itemText.text);
//            if (entries_of_itemText != null) entries_of_item = new String((entries_of_itemText.text).getBytes("UTF-8"));
//            if (entries_of_itemText != null) entries_of_item = (entries_of_itemText.text).getBytes("UTF-8");


            if (outgoing_itemText != null) outgoing_item = (outgoing_itemText.text);
            if (item_countingText != null) item_counting = (item_countingText.text);
            if (return_textText != null) return_text = (return_textText.text);
            if (countingText != null) counting = (countingText.text);
            if (no_counting_titleText != null) no_counting_title = (no_counting_titleText.text);
            if (has_counting_dialogText != null) has_counting_dialog = (has_counting_dialogText.text);
            if (add_new_counting_processText != null) add_new_counting_process = (add_new_counting_processText.text);
            if (new_counting_infoText != null) new_counting_info = (new_counting_infoText.text);
            if (counter_nameText != null) counter_name = (counter_nameText.text);
            if (et_logistics_company_coText != null) logistics_company_co = (et_logistics_company_coText.text);
            if (doneText != null) done = (doneText.text);
            if (enter_name_counting_errorText != null) enter_name_counting_error = (enter_name_counting_errorText.text);
            if (add_counting_successText != null) add_counting_success = (add_counting_successText.text);
            if (outgoingText != null) outgoing = (outgoingText.text);
            if (has_outgoing_dialogText != null) has_outgoing_dialog = (has_outgoing_dialogText.text);
            if (no_outgoing_dialogText != null) no_outgoing_dialog = (no_outgoing_dialogText.text);
            if (add_new_outgoing_processText != null) add_new_outgoing_process = (add_new_outgoing_processText.text);
            if (new_outgoing_infoText != null) new_outgoing_info = (new_outgoing_infoText.text);
            if (driver_nameText != null) driver_name = (driver_nameText.text);
            if (vehicle_numberText != null) vehicle_number = (vehicle_numberText.text);
            if (new_outgoing_data_errorText != null) new_outgoing_data_error = (new_outgoing_data_errorText.text);
            if (add_outgoing_successText != null) add_outgoing_success = (add_outgoing_successText.text);
            if (entrance_itemText != null) entrance_item = (entrance_itemText.text);
            if (no_entrace_dataText != null) no_entrace_data = (no_entrace_dataText.text);
            if (has_entrance_dataText != null) has_entrance_data = (has_entrance_dataText.text);
            if (add_new_entrance_processText != null) add_new_entrance_process = (add_new_entrance_processText.text);
            if (new_entrance_infoText != null) new_entrance_info = (new_entrance_infoText.text);
            if (user_nameText != null) user_name = (user_nameText.text);
            if (entrance_data_errorText != null) entrance_data_error = (entrance_data_errorText.text);
            if (add_entrance_successText != null) add_entrance_success = (add_entrance_successText.text);
            if (no_return_dialogText != null) no_return_dialog = (no_return_dialogText.text);
            if (has_return_dialogText != null) has_return_dialog = (has_return_dialogText.text);
            if (add_new_return_processText != null) add_new_return_process = (add_new_return_processText.text);
            if (new_return_infoText != null) new_return_info = (new_return_infoText.text);
            if (post_barcodeText != null) post_barcode = (post_barcodeText.text);
            if (receiver_nameText != null) receiver_name = (receiver_nameText.text);
            if (vehicle_driver_nameText != null) vehicle_driver_name = (vehicle_driver_nameText.text);
            if (return_info_erronText != null) return_info_erron = (return_info_erronText.text);
            if (return_add_successText != null) return_add_success = (return_add_successText.text);

        }

        tv_actions.setText(actions);
        tvEnterance.setText(entries_of_item);
        tvExit.setText(outgoing_item);
        tvControl.setText(item_counting);
        tvReturned.setText(return_text);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        NavigationView navigationView = findViewById(R.id.navigationView);


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ToolBar toolBar = new ToolBar(ReportActivity.this, navigationView, drawerLayout);

        Fabric.with(this, new Crashlytics());

        TextView tvExit = findViewById(R.id.tvExit);
        TextView tvReturn = findViewById(R.id.tvReturned);
        TextView tvEnterance = findViewById(R.id.tvEnterance);
        TextView tvControl = findViewById(R.id.tvControl);

        try {
            setViewText();
        } catch (UnsupportedEncodingException e) {
            Log.e("EncodingException","&&&&&&&");
            e.printStackTrace();
        }
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_exitSheet();
            }
        });

        tvEnterance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_entranceSheet();
            }
        });

        tvControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_countingSheet();
            }
        });

        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_returnSheet();
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new UpdateReceiver(), filter);

//
//        Intent alarmIntent = new Intent(ReportActivity.this, UpdateReceiver.class);
//        PendingIntent pendingIntent0 = PendingIntent.getBroadcast(ReportActivity.this, 0, alarmIntent, 0);
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        int interval = 10000;
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 1);
//        calendar.set(Calendar.MINUTE, 1);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent0);


    }


    /// Counting Process

    /**
     * show pre counting process
     */
    public void dialog_countingSheet() {

        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_counting_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_countingDialog = dialog.findViewById(R.id.tv_countingDialog);
        tv_countingDialog.setText(counting);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
        ArrayList<CountingSheet> preCountingSheetList = new ArrayList<>();
        preCountingSheetList = dataBaseHandler.getAllCountingSheet();


        TextView tv_Title = dialog.findViewById(R.id.tv_Title);
        if (preCountingSheetList.size() == 0) {
            tv_Title.setText(no_counting_title);
        } else {
            tv_Title.setText(has_counting_dialog);
        }

        ListView lv_preCountingSheet = dialog.findViewById(R.id.lv_preCountingSheet);
        final PreCountingSheetAdapter preCountingSheetAdapter = new PreCountingSheetAdapter(ReportActivity.this, preCountingSheetList, dialog);
        lv_preCountingSheet.setAdapter(preCountingSheetAdapter);

        Button bt_AddCountingSheet = dialog.findViewById(R.id.bt_AddCountingSheet);
        bt_AddCountingSheet.setText(add_new_counting_process);
        bt_AddCountingSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_AddCountingSheet();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * get new counting process information
     */
    public void dialog_AddCountingSheet() {

        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_counting_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_newCounting = dialog.findViewById(R.id.tv_newCounting);
        tv_newCounting.setText(new_counting_info);

        final EditText et_counterName = dialog.findViewById(R.id.et_counterName);
        final EditText et_logistics_company_co = dialog.findViewById(R.id.et_logistics_company_co);
        final EditText et_countingDate = dialog.findViewById(R.id.et_CountingDate);

        et_counterName.setHint(counter_name);
        et_logistics_company_co.setHint(logistics_company_co);




        final String date = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
        final String strDate = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8) +
                " " + date.substring(9, 11) + ":" + date.substring(11, 13);
        et_countingDate.setText(strDate);

        Button bt_add = dialog.findViewById(R.id.bt_add);
        bt_add.setText(done);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String counterName = et_counterName.getText().toString();
                String logisticscompany = et_logistics_company_co.getText().toString();
                String countingDate = et_countingDate.getText().toString();
                if (counterName.length() == 0 || countingDate.length() != 16||logisticscompany.length()==0) {
                    Toast.makeText(ReportActivity.this, enter_name_counting_error, Toast.LENGTH_LONG).show();
                } else {
                    CountingSheet countingSheet = new CountingSheet(1, counterName, countingDate, 0, "",0,logisticscompany);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
                    long countingSheetId = dataBaseHandler.addCountingSheet(countingSheet);

                    Toast.makeText(ReportActivity.this, add_counting_success, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    dialog.cancel();
                    Intent intent = new Intent(ReportActivity.this, CountingSheetItemActivity.class);
                    intent.putExtra("countingSheetId", countingSheetId + "");
                    intent.putExtra("countingSheetName", counterName + "");
                    intent.putExtra("countingSheetLogisticsCompany", logisticscompany + "");
                    intent.putExtra("countingSheetDate", countingDate + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }


    ///Outgoing Process

    /**
     * show pre Outgoing process
     */
    public void dialog_exitSheet() {

        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_outgoingDialog = dialog.findViewById(R.id.tv_outgoingDialog);
        tv_outgoingDialog.setText(outgoing);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
        ArrayList<ExitSheet> preExitSheetList = new ArrayList<>();
        preExitSheetList = dataBaseHandler.getAllExitSheet();

        TextView tv_Title = dialog.findViewById(R.id.tv_Title);
        if (preExitSheetList.size() == 0) {
            tv_Title.setText(no_outgoing_dialog);
        } else {
            tv_Title.setText(has_outgoing_dialog);
        }

        ListView lv_preExitSheet = dialog.findViewById(R.id.lv_preExitSheet);
        PreExitSheetAdapter preExitSheetAdapter = new PreExitSheetAdapter(ReportActivity.this, preExitSheetList, dialog);
        lv_preExitSheet.setAdapter(preExitSheetAdapter);

        Button bt_AddExitSheet = dialog.findViewById(R.id.bt_AddExitSheet);
        bt_AddExitSheet.setText(add_new_outgoing_process);
        bt_AddExitSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_AddExitSheet();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * get new Outgoing process information
     */
    public void dialog_AddExitSheet() {

        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_exit_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_newOutgoing = dialog.findViewById(R.id.tv_newOutgoing);
        tv_newOutgoing.setText(new_outgoing_info);

        final EditText et_exitDate = dialog.findViewById(R.id.et_exitDate);
        final EditText et_DriverName = dialog.findViewById(R.id.et_DriverName);
        final EditText et_logistics_company = dialog.findViewById(R.id.et_logistics_company);
        final EditText et_VehicleNumber = dialog.findViewById(R.id.et_VehicleNumber);

        et_DriverName.setHint(driver_name);
        et_VehicleNumber.setHint(vehicle_number);



        final String date = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
        final String strDate = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8) +
                " " + date.substring(9, 11) + ":" + date.substring(11, 13);
        et_exitDate.setText(strDate);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String DriverName = et_DriverName.getText().toString();
                String VehicleNumber = et_VehicleNumber.getText().toString();
                String exitDate = et_exitDate.getText().toString();
                String logisticscompany = et_logistics_company.getText().toString();
                if (DriverName.length() == 0 || VehicleNumber.length() == 0 || exitDate.length() != 16&&logisticscompany.length()==0) {
                    Toast.makeText(ReportActivity.this, new_outgoing_data_error, Toast.LENGTH_LONG).show();
                } else {
                    ExitSheet exitSheet = new ExitSheet(0, exitDate, VehicleNumber, DriverName, 0, "",0,logisticscompany);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
                    long exitSheetId = dataBaseHandler.addExitSheet(exitSheet);

                    Toast.makeText(ReportActivity.this,add_outgoing_success, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    dialog.cancel();
                    Intent intent = new Intent(ReportActivity.this, ExitSheetPackageActivity.class);
                    intent.putExtra("exitSheetId", exitSheetId + "");
                    intent.putExtra("exitSheetDriverName", exitSheet.VehicleDriverName + "");
                    intent.putExtra("exitSheetVehicleNumber", exitSheet.VehicleNumber + "");
                    intent.putExtra("exitSheetDate", exitSheet.Date + "");
                    intent.putExtra("exitSheetLogisticsCompany", logisticscompany + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }


    ///Entrance Process

    /**
     * show pre Entrance process
     */
    public void dialog_entranceSheet() {
        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_entrance_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_enterDialog = dialog.findViewById(R.id.tv_enterDialog);
        tv_enterDialog.setText(entrance_item);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
        ArrayList<EntranceSheet> preEntranceSheetList = new ArrayList<>();
        preEntranceSheetList = dataBaseHandler.getAllEntranceSheet();

        TextView tv_Title = dialog.findViewById(R.id.tv_Title);
        if (preEntranceSheetList.size() == 0) {
            tv_Title.setText(no_entrace_data);
        } else {
            tv_Title.setText(has_entrance_data);
        }

        ListView lv_preEntranceSheet = dialog.findViewById(R.id.lv_preEntranceSheet);
        PreEntranceSheetAdapter preEntranceSheetAdapter = new PreEntranceSheetAdapter(ReportActivity.this, preEntranceSheetList, dialog);
        lv_preEntranceSheet.setAdapter(preEntranceSheetAdapter);

        Button bt_AddEntranceSheet = dialog.findViewById(R.id.bt_AddEntranceSheet);
        bt_AddEntranceSheet.setText(add_new_entrance_process);
        bt_AddEntranceSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_AddEntranceSheet();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * get new Entrance process information
     */
    public void dialog_AddEntranceSheet() {

        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_entrance_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_newEntrance = dialog.findViewById(R.id.tv_newEntrance);
        final EditText et_UserName = dialog.findViewById(R.id.et_UserName);
        final EditText et_logistics_company = dialog.findViewById(R.id.et_logistics_company);
        final EditText et_entranceDate = dialog.findViewById(R.id.et_entranceDate);

        tv_newEntrance.setText(new_entrance_info);
        et_UserName.setHint(user_name);



        final String date = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
        final String strDate = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8) +
                " " + date.substring(9, 11) + ":" + date.substring(11, 13);
        et_entranceDate.setText(strDate);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        bt_add.setText(done);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName = et_UserName.getText().toString();
                String logisticsCompany = et_logistics_company.getText().toString();
//                String Description = et_Description.getText().toString();
                String entranceDate = et_entranceDate.getText().toString();
                if (UserName.length() == 0 || entranceDate.length() != 16||logisticsCompany.length()==0) {
                    Toast.makeText(ReportActivity.this, entrance_data_error, Toast.LENGTH_LONG).show();
                } else {
                    EntranceSheet entranceSheet = new EntranceSheet(0, entranceDate, UserName, "", 0, "",0,logisticsCompany);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
                    long entranceSheetId = dataBaseHandler.addEntranceSheet(entranceSheet);

                    Toast.makeText(ReportActivity.this, add_entrance_success, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    dialog.cancel();
                    Intent intent = new Intent(ReportActivity.this, EntranceSheetPackageActivity.class);
                    intent.putExtra("entranceSheetId", entranceSheetId + "");
                    intent.putExtra("entranceSheetUserName", entranceSheet.UserName + "");
                    intent.putExtra("entranceSheetDescription", entranceSheet.Description + "");
                    intent.putExtra("entranceSheetDate", entranceSheet.Date + "");
                    intent.putExtra("entranceSheetLogisticsCompany", entranceSheet.LogisticsCompany + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }


    ///Return Process

    /**
     * show pre Return process
     */
    public void dialog_returnSheet() {
        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_return_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tv_returnDialog = dialog.findViewById(R.id.tv_returnDialog);
        tv_returnDialog.setText(return_text);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
        ArrayList<ReturnSheet> preReturnSheetList = new ArrayList<>();
        preReturnSheetList = dataBaseHandler.getAllReturnSheet();

        TextView tv_Title = dialog.findViewById(R.id.tv_Title);
        if (preReturnSheetList.size() == 0) {
            tv_Title.setText(no_return_dialog);
        } else {
            tv_Title.setText(has_return_dialog);
        }


        ListView lv_preReturnSheet = dialog.findViewById(R.id.lv_preReturnSheet);
        PreReturnSheetAdapter preEntranceSheetAdapter = new PreReturnSheetAdapter(ReportActivity.this, preReturnSheetList, dialog);
        lv_preReturnSheet.setAdapter(preEntranceSheetAdapter);

        Button bt_AddReturnSheet = dialog.findViewById(R.id.bt_AddReturnSheet);
        bt_AddReturnSheet.setText(add_new_return_process);
        bt_AddReturnSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_AddReturnSheet();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * get new Return process information
     */
    public void dialog_AddReturnSheet() {

        final Dialog dialog = new Dialog(ReportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_return_sheet);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        canRemove13 = true;
        TextView tv_newReturn = dialog.findViewById(R.id.tv_newReturn);
        tv_newReturn.setText(new_return_info);
        final EditText et_PostBarcode = dialog.findViewById(R.id.et_PostBarcode);
        et_PostBarcode.setHint(post_barcode);
        final EditText et_ReceiverName = dialog.findViewById(R.id.et_ReceiverName);
        et_ReceiverName.setHint(receiver_name);
        final EditText et_VehicleDriverName = dialog.findViewById(R.id.et_VehicleDriverName);
        final EditText et_logistics_company = dialog.findViewById(R.id.et_logistics_company);
        et_VehicleDriverName.setHint(vehicle_driver_name);
        final EditText et_VehicleNumber = dialog.findViewById(R.id.et_VehicleNumber);
        et_VehicleNumber.setHint(vehicle_number);
        final EditText et_returnDate = dialog.findViewById(R.id.et_returnDate);
        final String date = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
        final String strDate = date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8) +
                " " + date.substring(9, 11) + ":" + date.substring(11, 13);
        et_returnDate.setText(strDate);

        final String[] postBarcode = {""};
        final int TYPING_TIMEOUT = 500;
        final Handler timeoutHandlerPost = new Handler();
        final Runnable typingTimeoutPost = new Runnable() {
            public void run() {
                serviceCall(et_PostBarcode, et_ReceiverName, postBarcode[0]);
            }
        };

        et_PostBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String number = et_PostBarcode.getText().toString();
                timeoutHandlerPost.removeCallbacks(typingTimeoutPost);

                if (number.length() > 8) {
                    Log.e("packageCode last2", number.substring(number.length() - 2, number.length()) + "");
                } else {
                    canRemove13 = true;
                }

                if (number.length() > 9 && number.substring(number.length() - 2, number.length()).equals("13")) {
                    if (canRemove13) {
                        Log.e("packageCode finish", number.substring(number.length() - 2, number.length() - 1) + "");
                        postBarcode[0] = number.substring(0, number.length() - 2);
                        Log.e("packageCode", postBarcode[0]);
                        timeoutHandlerPost.postDelayed(typingTimeoutPost, TYPING_TIMEOUT);
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        Button bt_add = dialog.findViewById(R.id.bt_add);
        bt_add.setText(done);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PostBarcode = et_PostBarcode.getText().toString();
                String ReceiverName = et_ReceiverName.getText().toString();
                String VehicleDriverName = et_VehicleDriverName.getText().toString();
                String VehicleNumber = et_VehicleNumber.getText().toString();
                String returnDate = et_returnDate.getText().toString();
                String logisticsCompany = et_logistics_company.getText().toString();


                if (PostBarcode.length() < 10 || ReceiverName.length() == 0 ||
                        VehicleDriverName.length() == 0 || VehicleNumber.length() == 0 || returnDate.length() != 16&&logisticsCompany.length()==0) {
                    Toast.makeText(ReportActivity.this, return_info_erron, Toast.LENGTH_LONG).show();
                } else {
                    ReturnSheet returnSheet = new ReturnSheet(0, date, ReceiverName, VehicleDriverName, VehicleNumber, PostBarcode, 0, "",0,logisticsCompany);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(ReportActivity.this);
                    long returnSheetId = dataBaseHandler.addReturnSheet(returnSheet);

                    Toast.makeText(ReportActivity.this, return_add_success, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    dialog.cancel();
                    Intent intent = new Intent(ReportActivity.this, ReturnSheetPackageActivity.class);
                    intent.putExtra("returnSheetId", returnSheetId + "");
                    intent.putExtra("returnSheetReceiverName", ReceiverName + "");
                    intent.putExtra("returnSheetVehicleDriverName", VehicleDriverName + "");
                    intent.putExtra("returnSheetVehicleNumber", VehicleNumber + "");
                    intent.putExtra("returnSheetPostBarcode", PostBarcode + "");
                    intent.putExtra("returnSheetDate", date + "");
                    intent.putExtra("returnSheetLogisticsCompany", logisticsCompany + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    public void serviceCall(EditText et_PackageCode, EditText et_ReceiverName, String postBarcode) {
        canRemove13 = false;
        et_PackageCode.setText(postBarcode);
        et_PackageCode.clearFocus();
        et_ReceiverName.requestFocus();
    }







}
