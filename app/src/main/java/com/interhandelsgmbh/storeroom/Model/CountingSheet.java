package com.interhandelsgmbh.storeroom.Model;

import java.util.ArrayList;
import java.util.Date;


public class CountingSheet {

    public int Id;
    public String CounterName;
    public String CountingDate;
    public int IsDone;
    public String LogisticsCompany;

    public String PdfPath;
    public int IsSend;
    public ArrayList<CountingSheetItem> CountingSheetItemArray;
    public CountingSheet(int id, String counterName, String countingDate,int isDone,String pdfPath,int isSend,String logisticsCompany){
        Id = id;
        CounterName = counterName;
        LogisticsCompany=logisticsCompany;
        CountingDate = countingDate;
        IsDone = isDone;
        PdfPath = pdfPath;
        IsSend = isSend;
    }
    public CountingSheet(int id, String counterName, String countingDate, int isDone, String pdfPath, int isSend,
                         ArrayList<CountingSheetItem> countingSheetItemArray,String logisticsCompany){
        Id = id;
        CounterName = counterName;
        CountingDate = countingDate;
        IsDone = isDone;
        LogisticsCompany=logisticsCompany;
        PdfPath = pdfPath;
        IsSend = isSend;
        CountingSheetItemArray = countingSheetItemArray;
    }

}
