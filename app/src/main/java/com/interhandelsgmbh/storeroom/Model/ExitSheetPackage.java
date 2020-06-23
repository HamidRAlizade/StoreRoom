package com.interhandelsgmbh.storeroom.Model;


import java.util.ArrayList;

public class ExitSheetPackage {

    public int Id;
    public int ExitSheetId;
    public String DeliveryNumber;
    public String InvoiceBarcode;
    public int IsSend;
    public ArrayList<ExitSheetPackageGoods> ExitSheetPackageGoodsArray;

    public ExitSheetPackage(int id, int exitSheetId, String deliveryNumber, String invoiceBarcode,int isSend){
        Id = id;
        ExitSheetId = exitSheetId;
        DeliveryNumber = deliveryNumber;
        InvoiceBarcode = invoiceBarcode;
        IsSend = isSend;

    }
    public ExitSheetPackage(int id, int exitSheetId, String deliveryNumber, String invoiceBarcode,int isSend,
                            ArrayList<ExitSheetPackageGoods> exitSheetPackageGoodsArray){
        Id = id;
        ExitSheetId = exitSheetId;
        DeliveryNumber = deliveryNumber;
        InvoiceBarcode = invoiceBarcode;
        IsSend = isSend;
        ExitSheetPackageGoodsArray=exitSheetPackageGoodsArray ;

    }

}
