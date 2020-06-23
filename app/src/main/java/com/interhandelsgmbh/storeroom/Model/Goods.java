package com.interhandelsgmbh.storeroom.Model;

public class Goods {

    public int Id;
    public String Name;

    public String Barocde;
    public int IsSend;

    public Goods(int id, String name,String barcode,int isSend){
        Id = id;
        Name = name;
        Barocde = barcode;
        IsSend = isSend;

    }

}
