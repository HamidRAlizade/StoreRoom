package com.interhandelsgmbh.storeroom.Model;


public class AppText {

    public int Id;
    public int parentId;
    public int languageId;
    public String text;

    public AppText(int id, int _parentId, int _languageId, String _text){
        Id = id;
        parentId = _parentId;
        languageId = _languageId;
        text = _text;
    }

}
