package com.interhandelsgmbh.storeroom.Model;



public class ExitSheetPackageGoods {

    public int Id;
    public int ExitSheetPackageId;
    public int GoodsId;
    public int IsSend;
    public String GoodsName;


    public ExitSheetPackageGoods(int id, int exitSheetPackageId, int goodsId,int isSend){
        Id = id;
        ExitSheetPackageId = exitSheetPackageId;
        GoodsId = goodsId;
        IsSend = isSend;

    }
    public ExitSheetPackageGoods(int id, int exitSheetPackageId, int goodsId,int isSend,
                                  String goodsName
    ){
        Id = id;
        ExitSheetPackageId = exitSheetPackageId;
        GoodsId = goodsId;
        GoodsName = goodsName ;
        IsSend = isSend;

    }

}
