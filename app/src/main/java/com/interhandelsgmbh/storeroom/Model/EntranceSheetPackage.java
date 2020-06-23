package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;

public class EntranceSheetPackage {

    public int Id;
    public int EntranceSheetId;
    public String PackageNumber;
    public int IsSend;
    public ArrayList<EntranceSheetPackageItem> EntranceSheetPackageItemArray;


    public EntranceSheetPackage(int id, int entranceSheetId, String packageNumber,int isSend){
        Id = id;
        EntranceSheetId = entranceSheetId;
        PackageNumber = packageNumber;
        IsSend = isSend;

    }
    public EntranceSheetPackage(int id, int entranceSheetId, String packageNumber,int isSend
    ,                   ArrayList<EntranceSheetPackageItem> entranceSheetPackageItemArray
                                ){
        Id = id;
        EntranceSheetId = entranceSheetId;
        PackageNumber = packageNumber;
        IsSend = isSend;
        EntranceSheetPackageItemArray=entranceSheetPackageItemArray;

    }

}
