package com.interhandelsgmbh.storeroom.Model;



public class EntranceSheetPackageItem {

    public int Id;
    public int EntranceSheetPackageId;
    public int GoodsId;
    public String GoodsName ;
    public int IsSend;
    public EntranceSheetPackageItem(int id, int entranceSheetPackageId, int goodsId,int isSend,String goodsName){
        Id = id;
        EntranceSheetPackageId = entranceSheetPackageId;
        GoodsId = goodsId;
        IsSend = isSend;
        GoodsName=goodsName ;

    }
    public EntranceSheetPackageItem(int id, int entranceSheetPackageId, int goodsId,int isSend){
        Id = id;
        EntranceSheetPackageId = entranceSheetPackageId;
        GoodsId = goodsId;
        IsSend = isSend;

    }

}
