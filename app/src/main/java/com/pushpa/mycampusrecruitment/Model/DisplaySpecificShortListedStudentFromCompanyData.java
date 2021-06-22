package com.pushpa.mycampusrecruitment.Model;

public class DisplaySpecificShortListedStudentFromCompanyData {
    private String ten, twelve, graduation, currentacademic, keyskills, registrationnumber, universityname, imageuri;

    public DisplaySpecificShortListedStudentFromCompanyData(String ten, String twelve, String graduation, String currentacademic, String registrationnumber, String universityname, String imageuri, String keyskills) {
        this.ten = ten;
        this.twelve = twelve;
        this.graduation = graduation;
        this.currentacademic = currentacademic;
        this.keyskills = keyskills;
        this.registrationnumber = registrationnumber;
        this.universityname = universityname;
        this.imageuri = imageuri;
    }



    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTwelve() {
        return twelve;
    }

    public void setTwelve(String twelve) {
        this.twelve = twelve;
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

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        this.registrationnumber = registrationnumber;
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
