package com.interhandelsgmbh.storeroom.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.AppText;
import com.interhandelsgmbh.storeroom.R;

public class DescriptionActivity extends AppCompatActivity {


    private EditText et_entranceDescription,et_outgoingDescription,et_countingDescription,et_returnDescription;




    String description, entrance_process, outgoing_process, counting_process, return_process, save,save_description_success;

    public void setViewText() {
        TextView tv_description = findViewById(R.id.tv_description);
        TextView tv_entranceProcess = findViewById(R.id.tv_entranceProcess);
        TextView tv_outgoingProcess = findViewById(R.id.tv_outgoingProcess);
        TextView tv_countingProcess = findViewById(R.id.tv_countingProcess);
        TextView tv_ReturnProcess = findViewById(R.id.tv_ReturnProcess);
        TextView tv_save = findViewById(R.id.tv_save);


        description = getResources().getString(R.string.description);
        entrance_process = getResources().getString(R.string.entrance_process);
        outgoing_process = getResources().getString(R.string.outgoing_process);
        counting_process = getResources().getString(R.string.counting_process);
        return_process = getResources().getString(R.string.return_process);
        save = getResources().getString(R.string.save);
        save_description_success = getResources().getString(R.string.save_description_success);



        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if (setting.contains("LanguageId")) {
            String LanguageIdStr = setting.getString("LanguageId", "1");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(DescriptionActivity.this);
            int languageId = 1;
            try {
                languageId = Integer.parseInt(LanguageIdStr);
            } catch (Exception e) {
                languageId = 1;
            }
            //&& description = 23
            //&& entrance_process = 24
            //&& outgoing_process = 25
            //&& counting_process = 26
            //&& return_process = 27
            //&& save = 6
            //&& save_description_success = 28



            AppText descriptionText = dataBaseHandler.getAppText(23, languageId);
            AppText entrance_processText = dataBaseHandler.getAppText(24, languageId);
            AppText outgoing_processText = dataBaseHandler.getAppText(25, languageId);
            AppText counting_processText = dataBaseHandler.getAppText(26, languageId);
            AppText return_processText = dataBaseHandler.getAppText(27, languageId);
            AppText saveText = dataBaseHandler.getAppText(6, languageId);
            AppText save_description_successText = dataBaseHandler.getAppText(28, languageId);


            if (descriptionText != null) {
                description = (descriptionText.text);
            }
            if (entrance_processText != null) {
                entrance_process = (entrance_processText.text);
            }
            if (outgoing_processText != null) {
                outgoing_process = (outgoing_processText.text);
            }
            if (counting_processText != null) {
                counting_process = (counting_processText.text);
            }
            if (return_processText != null) {
                return_process = (return_processText.text);
            }
            if (saveText != null) {
                save = (saveText.text);
            }
            if (save_description_successText != null) {
                save_description_success = (save_description_successText.text);
            }


        }

        tv_description.setText(description);
        tv_entranceProcess.setText(entrance_process);
        tv_outgoingProcess.setText(outgoing_process);
        tv_countingProcess.setText(counting_process);
        tv_ReturnProcess.setText(return_process);
        tv_save.setText(save);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        et_entranceDescription = findViewById(R.id.et_entranceDescription);
        et_outgoingDescription = findViewById(R.id.et_outgoingDescription);
        et_countingDescription = findViewById(R.id.et_countingDescription);
        et_returnDescription = findViewById(R.id.et_returnDescription);

        setViewText();

        /**
         * fill editText with saved description
         */
        SharedPreferences setting = getSharedPreferences("UserInfo", 0);
        if(setting.contains("entranceDescription")){
            String entranceDescription = setting.getString("entranceDescription","");
            et_entranceDescription.setText(entranceDescription);
            et_entranceDescription.setSelection(et_entranceDescription.getText().toString().length());
        }
        if(setting.contains("outgoingDescription")){
            String outgoingDescription = setting.getString("outgoingDescription","");
            et_outgoingDescription.setText(outgoingDescription);
            et_outgoingDescription.setSelection(et_outgoingDescription.getText().toString().length());
        }
        if(setting.contains("countingDescription")){
            String countingDescription = setting.getString("countingDescription","");
            et_countingDescription.setText(countingDescription);
            et_countingDescription.setSelection(et_countingDescription.getText().toString().length());
        }
        if(setting.contains("returnDescription")){
            String returnDescription = setting.getString("returnDescription","");
            et_returnDescription.setText(returnDescription);
            et_returnDescription.setSelection(et_returnDescription.getText().toString().length());
        }

        TextView tv_save = findViewById(R.id.tv_save);

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entranceDescription = et_entranceDescription.getText().toString();
                String outgoingDescription = et_outgoingDescription.getText().toString();
                String countingDescription = et_countingDescription.getText().toString();
                String returnDescription = et_returnDescription.getText().toString();
                SharedPreferences setting = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("entranceDescription", entranceDescription);
                editor.putString("outgoingDescription", outgoingDescription);
                editor.putString("countingDescription", countingDescription);
                editor.putString("returnDescription", returnDescription);
                editor.commit();
                editor.apply();
                Toast.makeText(DescriptionActivity.this, save_description_success,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DescriptionActivity.this, ReportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });






    }
}
