package com.interhandelsgmbh.storeroom.Model;



public class SettingData{

    public int Id;


    public String NameScanner;
    public int IsSend;
    public String NameStore;


    public SettingData(int id, String nameScanner,String nameStore, int isSend){
        Id = id;
        NameScanner = nameScanner;
        NameStore = nameStore;
        IsSend = isSend;

    }


}
