package com.example.manigutmadayit;

public class EventDataClass {
    private String dataTitle;
    private String dataDesc;
    private String dataDate;
    private String dataImage;
    private String dataLocation;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getDataTitle() {
        return dataTitle;
    }
    public String getDataLocation() {
        return dataLocation;
    }
    public String getDataDesc() {
        return dataDesc;
    }
    public String getDataLang() {
        return dataDate;
    }
    public String getDataImage() {
        return dataImage;
    }
    public EventDataClass(String dataTitle, String dataDesc, String dataDate, String dataImage, String dataLocation) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataDate = dataDate;
        this.dataImage = dataImage;
        this.dataLocation = dataLocation;
    }
    public EventDataClass(){
    }
}
