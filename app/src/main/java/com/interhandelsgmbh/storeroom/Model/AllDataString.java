package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;
import java.util.Map;

public class AllDataString {

    public ArrayList<Map<String, String>> GoodsArray;
    public ArrayList<Map<String, String>> CountingSheetArray;
    public ArrayList<Map<String, String>> CountingSheetItemArray;
    public ArrayList<Map<String, String>> ExitSheetArray;
    public ArrayList<Map<String, String>> ExitSheetPackageArray;
    public ArrayList<Map<String, String>> ExitSheetPackageGoodsArray;
    public ArrayList<Map<String, String>> EntranceSheetArray;
    public ArrayList<Map<String, String>> EntranceSheetPackageArray;
    public ArrayList<Map<String, String>> EntranceSheetPackageItemArray;
    public ArrayList<Map<String, String>> ReturnSheetArray;
    public ArrayList<Map<String, String>> ReturnSheetPackageArray;
    public ArrayList<Map<String, String>> ReturnSheetImageArray;
    public ArrayList<Map<String, String>> ReturnSheetPackageItemArray;
    SettingData SettingData;

    public AllDataString(ArrayList<Map<String, String>> goodsArray,
                         ArrayList<Map<String, String>> countingSheetArray,
                         ArrayList<Map<String, String>> countingSheetItemArray,
                         ArrayList<Map<String, String>> exitSheetArray,
                         ArrayList<Map<String, String>> exitSheetPackageArray,
                         ArrayList<Map<String, String>> exitSheetPackageGoodsArray,
                         ArrayList<Map<String, String>> entranceSheetArray,
                         ArrayList<Map<String, String>> entranceSheetPackageArray,
                         ArrayList<Map<String, String>> entranceSheetPackageItemArray,
                         ArrayList<Map<String, String>> returnSheetArray,
                         ArrayList<Map<String, String>> returnSheetPackageArray,
                         ArrayList<Map<String, String>> returnSheetImageArray,
                         ArrayList<Map<String, String>> returnSheetPackageItemArray,
                         SettingData settingData){



        GoodsArray = goodsArray;
        CountingSheetArray = countingSheetArray;
        CountingSheetItemArray = countingSheetItemArray;
        ExitSheetArray = exitSheetArray;
        ExitSheetPackageArray = exitSheetPackageArray;
        ExitSheetPackageGoodsArray = exitSheetPackageGoodsArray;
        EntranceSheetArray = entranceSheetArray;
        EntranceSheetPackageArray = entranceSheetPackageArray;
        EntranceSheetPackageItemArray = entranceSheetPackageItemArray;
        ReturnSheetArray = returnSheetArray;
        ReturnSheetPackageArray = returnSheetPackageArray;
        ReturnSheetImageArray = returnSheetImageArray;
        ReturnSheetPackageItemArray = returnSheetPackageItemArray;
        SettingData = settingData;
    }

}
