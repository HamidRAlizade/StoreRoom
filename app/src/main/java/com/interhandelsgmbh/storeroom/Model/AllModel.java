package com.interhandelsgmbh.storeroom.Model;

import java.util.ArrayList;

public class AllModel {

    public ArrayList<CountingSheet> CountingSheetArray;
    public ArrayList<ExitSheet> ExitSheetArray;
//    public ArrayList<ExitSheetPackage> ExitSheetPackageArray;
    public ArrayList<EntranceSheet> EntranceSheetArray;
//    public ArrayList<EntranceSheetPackage> EntranceSheetPackageArray;
//    public ArrayList<EntranceSheetPackageItem> EntranceSheetPackageItemArray;
    public ArrayList<ReturnSheet> ReturnSheetArray;
    public SettingData SettingData;
//    public ArrayList<ReturnSheetPackage> ReturnSheetPackageArray;
//    public ArrayList<ReturnSheetImage> ReturnSheetImageArray;
//    public ArrayList<ReturnSheetPackageItem> ReturnSheetPackageItemArray;

    public AllModel(
                   ArrayList<CountingSheet> countingSheetArray,
                   ArrayList<ExitSheet> exitSheetArray,
                   ArrayList<EntranceSheet> entranceSheetArray,
                   ArrayList<ReturnSheet> returnSheetArray,
                   SettingData settingDat
                  ){




        CountingSheetArray = countingSheetArray;
        SettingData =settingDat;
        ExitSheetArray = exitSheetArray;
        EntranceSheetArray = entranceSheetArray;

        ReturnSheetArray = returnSheetArray;

    }

}
