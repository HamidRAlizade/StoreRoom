package com.interhandelsgmbh.storeroom.Model;



public class ReturnSheetPackageItem {

    public int Id;
    public int ReturnSheetPackageId;
    public int GoodsId;
    public String Commnet;
    public int IsSend;
    public String GoodsName;


    public ReturnSheetPackageItem(int id, int returnSheetPackageId, int goodsId,String commnet,int isSend){
        Id = id;
        ReturnSheetPackageId = returnSheetPackageId;
        GoodsId = goodsId;
        Commnet = commnet;
        IsSend = isSend;

    }
  public ReturnSheetPackageItem(int id, int returnSheetPackageId, int goodsId,String commnet,int isSend, String goodsName
  ){
        Id = id;
        ReturnSheetPackageId = returnSheetPackageId;
        GoodsId = goodsId;
        Commnet = commnet;
        IsSend = isSend;
        GoodsName =goodsName;

    }

}
