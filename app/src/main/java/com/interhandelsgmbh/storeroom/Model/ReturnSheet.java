package com.interhandelsgmbh.storeroom.Model;


import android.support.v7.widget.RecyclerView;

import com.interhandelsgmbh.storeroom.Activity.ReportActivity;
import com.interhandelsgmbh.storeroom.R;

import java.util.ArrayList;

public class ReturnSheet {

    public int Id;
    public String Date;
    public String ReceiverName;
    public String VehicleDriverName;
    public String VehicleNumber;
    public String LogisticsCompany;
    public String PostBarcode;
    public int IsDone;
    public String PdfPath;
    public int IsSend;
    public ArrayList<ReturnSheetPackage> ReturnSheetPackageArray;
    public ArrayList<ReturnSheetImage> ReturnSheetImageArray;


    public ReturnSheet(int id, String date, String receiverName,String vehicleDriverName,
                       String vehicleNumber,String postBarcode,int isDone,String pdfPath,int isSend
            ,String logisticsCompany){
        Id = id;
        Date = date;
        ReceiverName = receiverName;
        VehicleDriverName = vehicleDriverName;
        VehicleNumber = vehicleNumber;
        PostBarcode = postBarcode;
        IsDone = isDone;
        PdfPath = pdfPath;
        IsSend = isSend;
        LogisticsCompany=logisticsCompany;

    }
    public ReturnSheet(int id, String date, String receiverName,String vehicleDriverName,
                       String vehicleNumber,String postBarcode,int isDone,String pdfPath,int isSend,
                       ArrayList<ReturnSheetPackage> returnSheetPackageArray,
                       ArrayList<ReturnSheetImage> returnSheetImageArray,String logisticsCompany){
        Id = id;
        Date = date;
        ReceiverName = receiverName;
        VehicleDriverName = vehicleDriverName;
        VehicleNumber = vehicleNumber;
        LogisticsCompany=logisticsCompany;
        PostBarcode = postBarcode;
        IsDone = isDone;
        PdfPath = pdfPath;
        IsSend = isSend;
        ReturnSheetPackageArray=returnSheetPackageArray ;
        ReturnSheetImageArray=returnSheetImageArray;

    }

}
