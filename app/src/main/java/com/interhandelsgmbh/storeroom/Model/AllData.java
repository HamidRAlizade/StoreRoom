package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;

public class AllData {

    public ArrayList<Goods> GoodsArray;
    public ArrayList<CountingSheet> CountingSheetArray;
    public ArrayList<CountingSheetItem> CountingSheetItemArray;
    public ArrayList<ExitSheet> ExitSheetArray;
    public ArrayList<ExitSheetPackage> ExitSheetPackageArray;
    public ArrayList<ExitSheetPackageGoods> ExitSheetPackageGoodsArray;
    public ArrayList<EntranceSheet> EntranceSheetArray;
    public ArrayList<EntranceSheetPackage> EntranceSheetPackageArray;
    public ArrayList<EntranceSheetPackageItem> EntranceSheetPackageItemArray;
    public ArrayList<ReturnSheet> ReturnSheetArray;
    public ArrayList<ReturnSheetPackage> ReturnSheetPackageArray;
    public ArrayList<ReturnSheetImage> ReturnSheetImageArray;
    public ArrayList<ReturnSheetPackageItem> ReturnSheetPackageItemArray;
    public SettingData SettingData;

    public AllData(ArrayList<Goods> goodsArray,
            ArrayList<CountingSheet> countingSheetArray,
            ArrayList<CountingSheetItem> countingSheetItemArray,
            ArrayList<ExitSheet> exitSheetArray,
            ArrayList<ExitSheetPackage> exitSheetPackageArray,
            ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsArray,
            ArrayList<EntranceSheet> entranceSheetArray,
            ArrayList<EntranceSheetPackage> entranceSheetPackageArray,
            ArrayList<EntranceSheetPackageItem> entranceSheetPackageItemArray,
            ArrayList<ReturnSheet> returnSheetArray,
            ArrayList<ReturnSheetPackage> returnSheetPackageArray,
            ArrayList<ReturnSheetImage> returnSheetImageArray,
            ArrayList<ReturnSheetPackageItem> returnSheetPackageItemArray,
                   SettingData settingDat ){



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
        SettingData=settingDat;
    }

}
