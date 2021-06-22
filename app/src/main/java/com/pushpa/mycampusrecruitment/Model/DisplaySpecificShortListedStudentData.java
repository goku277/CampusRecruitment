package com.pushpa.mycampusrecruitment.Model;

public class DisplaySpecificShortListedStudentData {
    private String registrationnumber, ten, graduation, currentacademic, keyskills, selectionincompanies, twelve;

    public DisplaySpecificShortListedStudentData(String registrationnumber, String ten, String graduation, String currentacademic, String keyskills, String selectionincompanies, String twelve) {
        this.registrationnumber = registrationnumber;
        this.ten = ten;
        this.graduation = graduation;
        this.currentacademic = currentacademic;
        this.keyskills = keyskills;
        this.selectionincompanies= selectionincompanies;
        this.twelve= twelve;
    }

    public String getTwelve() {
        return twelve;
    }

    public void setTwelve(String twelve) {
        this.twelve = twelve;
    }

    public String getSelectionincompanies() {
        return selectionincompanies;
    }

    public void setSelectionincompanies(String selectionincompanies) {
        this.selectionincompanies = selectionincompanies;
    }

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        this.registrationnumber = registrationnumber;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getCurrentacademic() {
        return currentacademic;
    }

    public void setCurrentacademic(String currentacademic) {
        this.currentacademic = currentacademic;
    }

    public String getKeyskills() {
        return keyskills;
    }

    public void setKeyskills(String keyskills) {
        this.keyskills = keyskills;
    }
}