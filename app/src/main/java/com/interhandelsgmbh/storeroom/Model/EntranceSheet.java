package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;

public class EntranceSheet {

    public int Id;
    public String Date;
    public String UserName;
    public String Description;
    public int IsDone;
    public  String LogisticsCompany;
    public String PdfPath;
    public int IsSend;
    public ArrayList<EntranceSheetPackage> EntranceSheetPackageArray;



    public EntranceSheet(int id, String date, String userName, String description, int isDone
            , String pdfPath,int isSend,String logisticsCompany){
        Id = id;
        Date = date;
        UserName = userName;
        LogisticsCompany=logisticsCompany;
        Description = description;
        IsDone = isDone;
        PdfPath = pdfPath;
        IsSend = isSend;

    }

    public EntranceSheet(int id, String date, String userName, String description, int isDone, String pdfPath,int isSend,
                      ArrayList<EntranceSheetPackage> entranceSheetPackageArray,String logisticsCompany
    ){
        Id = id;
        Date = date;
        UserName = userName;
        LogisticsCompany=logisticsCompany;
        Description = description;
        IsDone = isDone;
        PdfPath = pdfPath;
        IsSend = isSend;
        EntranceSheetPackageArray=entranceSheetPackageArray;

    }

}
