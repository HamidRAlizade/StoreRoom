package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;

public class ReturnSheetPackage {

    public int Id;
    public int ReturnSheetId;
    public String OrderBarcode;
    public int IsSend;
    public ArrayList<ReturnSheetPackageItem> ReturnSheetPackageItemArray;


    public ReturnSheetPackage(int id, int returnSheetId, String orderBarcode,int isSend){
        Id = id;
        ReturnSheetId = returnSheetId;
        OrderBarcode = orderBarcode;
        IsSend = isSend;

    }
    public ReturnSheetPackage(int id, int returnSheetId, String orderBarcode,int isSend,
                              ArrayList<ReturnSheetPackageItem> returnSheetPackageItemArray){
        Id = id;
        ReturnSheetId = returnSheetId;
        OrderBarcode = orderBarcode;
        IsSend = isSend;
        ReturnSheetPackageItemArray=returnSheetPackageItemArray;
    }

}
