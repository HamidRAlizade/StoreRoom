package com.interhandelsgmbh.storeroom.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.interhandelsgmbh.storeroom.Adapter.PreCountingSheetAdapter;
import com.interhandelsgmbh.storeroom.Database.DataBaseHandler;
import com.interhandelsgmbh.storeroom.Model.CountingSheet;
import com.interhandelsgmbh.storeroom.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView tv_title_helper = findViewById(R.id.tv_title_helper);
        TextView tv_helperText = findViewById(R.id.tv_helperText);
        Button bt_ok = findViewById(R.id.bt_ok);

        String helpId = getIntent().getStringExtra("helpId");
        String helpTitle = getIntent().getStringExtra("helpTitle");
        tv_title_helper.setText(helpTitle);
        printHelpText(tv_helperText,helpId);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, ReportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }


    public void printHelpText(TextView textView,String helpId){
        String text = "";
        if(helpId.equals("1")){
            text = "In this page, the user enters company information such as name, address, and company logo.\n" +
                    "-click “attach” for adding logo.\n" +
                    "-finally press ”save” button.\n";
        }else if(helpId.equals("2")){
            text = "Click “attach” button and choose csv format file.\n" +
                    "- the csv file must consist of two columns:\n" +
                    "Barcode,name,\n" +
                    "or\n" +
                    "barcode;name;\n" +
                    "\n" +
                    "- click “show data detail” after choosing files.\n" +
                    "\n" +
                    "According as the csv format which parted by comma or semicolon, click on intended divider.\n" +
                    "Finally choose Import Data:\n" +
                    "- Press “Reset” if you want to delete previous data.\n" +
                    "- Press “Update” if you want to add new data to the previous.\n";
        }else if(helpId.equals("3")){
            text = "Four selections of presenting process are observable in the home page.\n" +
                    "Entries: The process of invoicing from entries to the stock.\n" +
                    "\n" +
                    "Outgoing: The process of invoicing from the outgoing.\n" +
                    "\n" +
                    "Counting: The process of invoicing from stocktaking and counting inventory.\n" +
                    "\n" +
                    "Return: The process of invoicing from returned to the stock.\n" +
                    "You can observe a list of processes which you’ve made before by choosing each item.\n" +
                    "——> If the process doesn’t end up in making pdf file, continuing of the process is possible for you.\n" +
                    "\n" +
                    "——> If the process ends up in making pdf file, observing of that file is possible for you.\n" +
                    "\n" +
                    "——> If you want to begin a new process, click “add new process”.\n";
        }else if(helpId.equals("4")){
            text = "Begin the new import process by choosing “Add New Entrance Process”.\n" +
                    "Type the name of person who takes control of import to the stock in the username field.\n" +
                    "——> If date would not be according to your time, its editing is possible.\n" +
                    "\n" +
                    "——> Begin import process by clicking on “Done”.\n" +
                    "The import process is completed on this page. Use the button of reading a barcode on your barcode reader, and then read the package barcode.\n" +
                    "Now, a dialogue is opened for you that you can read barcode of the goods which are related to this package.\n" +
                    "At the beginning, read barcode of the first merchandise.\n" +
                    "\n" +
                    "——> If the barcode already have been saved by an import process in the App’s database, the name of merchandise has been shown automatically under the intended merchandise barcode.\n" +
                    "1.\tOtherwise, adding this merchandise to your database is possible. Type the name of merchandise in “Add New Item” and then click “Add” in order to add this merchandise to your database.\n" +
                    "2.\tfinally, you will observe the list of goods which related to this package and you can authorize to delete some goods from the list by clicking on remove icon and then delete it.\n" +
                    "3.\tRegister and confirm the final list of goods in this package by clicking “Done”.\n" +
                    "——> Continue the process of reading package number and barcode of goods related to this package to the last import package and then click “finish”.\n" +
                    "1.\tType explanation about these packages which are imported to the stock and click “Create PDF “. The invoice pdf is made and observable for you.\n" +
                    "2.\tSign on page 14 and then click “Done”.\n" +
                    "3.\tA sample of invoice pdf.\n";
        }else if(helpId.equals("5")){
            text = "Observe the outgoing process or make a new process by clicking on “Outgoing Item”.\n" +
                    "——> Gray boxes are incomplete process that their editing and completing are possible.\n" +
                    "\n" +
                    "——> Boxes with green line are the processes which are completed.\n" +
                    "\n" +
                    "1.\tBegin the new outgoing process by clicking on “Add New Outgoing Process”. In this part driver’s name, car number, and exit date of the car is getting from users.\n" +
                    "2.\tUse the button of reading a barcode on your barcode reader. Read the barcode of the postal package and then read the barcode of the invoice. Now a dialogue is opened that you can read the barcode of its invoice optional, and you can add barcode of the related goods too.\n" +
                    "——> If the barcode already have been saved by an import process in the App’s database, the name of merchandise has been shown automatically under the intended merchandise barcode.\n" +
                    "1.\tOtherwise, adding this merchandise to your database is possible. Type the name of merchandise in “Add New Item” and then click “Add” in order to add this merchandise to your database.\n" +
                    "2.\tfinally, you will observe the list of goods which related to this package and you can authorize to delete some goods from the list by clicking on remove icon and then delete it.\n" +
                    "3.\tRegister and confirm the final list of goods in this package by clicking “Done”.\n" +
                    "\n" +
                    "——>You will have a list of postal packages finally that you can delete each one if you want.\n" +
                    "——>Click on “finish” and insert the driver’s signature on the next page and click on “Done”.\n";
        }else if(helpId.equals("6")){
            text = "By clicking on “Item Counting”, you can see the process of stocktaking and counting merchandise inventory and also you are able to make new stocktaking process.\n" +
                    "——> Gray boxes are incomplete process that their editing and completing are possible.\n" +
                    "——> Boxes with green line are the processes which are completed that you can observe their invoice pdf.\n" +
                    "1.\tBy clicking on “Add New Counting Process” the new stocktaking process is begun and information which is related to stocktaking and its date is getting from users.\n" +
                    "2.\tOn this page the stocktaking process is begun. Use barcode reader and read barcode of the goods. If a barcode exists in the database already, its name is shown automatically and you can enter the number of goods in the “Amount” field and press “Add” in order to add it to your stocktaking list. If that barcode doesn’t exist in the database, a dialogue will be shown that you can add it to your database by click on “Add” and type its name and then enter the number of goods and add to list.\n" +
                    "3.\tFinally, you will have a list of goods with their number that the possibility of deleting each by clicking on remove button is prepared.\n" +
                    "4.\tBy clicking on “finish”, the person’s signature who does stocktaking is getting from users in this page and by clicking on “Done”, last pdf which is related to this stocktaking process will be made.\n" +
                    "5.\tThe pdf file of stocktaking is observable.\n";
        }else if(helpId.equals("7")){
            text = "By clicking on “Return”, a list of return processes which are registered before is observable.\n" +
                    "——> Gray boxes are incomplete process that their editing and completing are possible.\n" +
                    "\n" +
                    "——> Boxes with green line are the processes which are completed that you can observe their invoice pdf.\n" +
                    "\n" +
                    "By clicking on “Add New Return Process” the new return process is begun.\n" +
                    "\n" +
                    "1.\tAll the information about the package which is returned, the name of the person who takes that package, car driver’s name, car number, and the date of returning is getting from users in this page.\n" +
                    "2.\tThe process of returning is begun on this page. Use barcode reader and read the returned package barcode.\n" +
                    "3.\tAnd then a dialogue is opened automatically. In this part you can read returned goods barcode. If it doesn’t exist in database, add it to the database and enter the explanation about reasons of returning that merchandise.\n" +
                    "4.\tAt the end, You can see a list of goods that you can delete them from returning merchandise list by using remove button. By clicking on “Done”, goods are confirmed and this returned package will add to your base list. Continue this process till all the returned packages will be ended. by clicking on “Attach Photos”, you can add maximum 4 photos of returned package to your invoice.\n" +
                    "5.\tClick on attach button and choose photos from the gallery or click on attache button and take a photo. Then click on “Add to List” in order to add the chosen photo or the taken photo to the returned photos list of this invoice. Click on remove button in order to delete the photo.\n" +
                    "6.\tClick on “Save Photo” in order to save photos for returned invoice and then the signature of the person who takes returned package is getting.\n" +
                    "7.\tBy clicking on “Save and Get driver signature”, add the driver’s signature.\n" +
                    "8.\tBy clicking on “Done”, the pdf file of returning process is made. The sample of this file is observable.\n";
        }else if(helpId.equals("8")){
            text = "Application menu consists of these parts: Company Information: In this part you can edit company information. This information is printed on all invoices.\n" +
                    "1.\tDescription: On this page you can type a comment for each item to print this comment in all item’s invoice. ( click on “Save” to save the comment.)\n" +
                    "2.\tDelete Data: If you agree with the dialogue which is related to delete all the database in the application, press “Yes”, then all the data, such as merchandise information and all the registered information in the application will be deleted.\n" +
                    "3.\tExit: You can exit from your company account without deleting the data related to the goods and registered information, if you accept “Exit” from the account.\n";
        }else{
            text = "This item does not have any help";
        }

        textView.setText(text);

    }



}
