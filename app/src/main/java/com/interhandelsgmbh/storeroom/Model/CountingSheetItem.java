package com.interhandelsgmbh.storeroom.Model;

import java.util.ArrayList;
import java.util.Date;

public class CountingSheetItem {

    public int Id;
    public int CountingSheetId;
    public int GoodsId;
    public int Number;
    public int IsDone;
    public int IsSend;
    public String GoodsName;


    public CountingSheetItem(int id,int countingSheetId,int goodsId,int number,int isDone,int isSend){
        Id = id;
        CountingSheetId = countingSheetId;
        GoodsId = goodsId;
        Number = number;
        IsDone = isDone;
        IsSend = isSend;

    }    public CountingSheetItem(int id,int countingSheetId,int goodsId,int number,int isDone,int isSend,
    String goodsName){
        Id = id;
        CountingSheetId = countingSheetId;
        GoodsId = goodsId;
        Number = number;
        IsDone = isDone;
        IsSend = isSend;
        GoodsName =goodsName ;

    }

}
