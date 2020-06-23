package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;

public class ExitSheet {

    public int Id;
    public String Date;
    public String VehicleNumber;
    public String VehicleDriverName;
    public int IsDone;
    public String LogisticsCompany;
    public String PdfPath;
    public int IsSend;
    public ArrayList<ExitSheetPackage> ExitSheetPackageArray;


    public ExitSheet(int id,String date,String vehicleNumber,String vehicleDriverName,int isDone,String pdfPath,
                     int isSend,String logisticsCompany){
        Id = id;
        Date = date;
        VehicleNumber = vehicleNumber;
        LogisticsCompany=logisticsCompany;
        VehicleDriverName = vehicleDriverName;
        IsDone = isDone;
        PdfPath = pdfPath;
        IsSend = isSend;

    }

    public ExitSheet(int id, String date, String vehicleNumber, String vehicleDriverName, int isDone, String pdfPath, int isSend,
                      ArrayList<ExitSheetPackage> exitSheetPackageArray,String logisticsCompany
    ){
        Id = id;
        Date = date;
        VehicleNumber = vehicleNumber;
        VehicleDriverName = vehicleDriverName;
        IsDone = isDone;
        LogisticsCompany=logisticsCompany;
        PdfPath = pdfPath;
        IsSend = isSend;
        ExitSheetPackageArray=exitSheetPackageArray;

    }

}
