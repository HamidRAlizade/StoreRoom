package com.interhandelsgmbh.storeroom.Model;



public class ReturnSheetImage {

    public int Id;
    public int ReturnSheetId;
    public String ImageBinary;
    public int IsSend;


    public ReturnSheetImage(int id, int returnSheetId, String imageBinary,int isSend){
        Id = id;
        ReturnSheetId = returnSheetId;
        ImageBinary = imageBinary;
        IsSend = isSend;

    }

}
