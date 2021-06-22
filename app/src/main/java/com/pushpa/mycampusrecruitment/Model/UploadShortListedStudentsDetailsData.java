package com.pushpa.mycampusrecruitment.Model;

public class UploadShortListedStudentsDetailsData {
    private String university, registration, imageurl, ten, twelve, graduation, currentacademic, keyskills, companiesselected, contactNumber, jobrequirement;

    public UploadShortListedStudentsDetailsData(String university, String registration, String imageurl, String ten, String twelve, String graduation, String currentacademic, String keyskills, String companiesselected, String contactNumber, String jobrequirement) {
        this.university = university;
        this.registration = registration;
        this.imageurl = imageurl;
        this.ten = ten;
        this.twelve = twelve;
        this.graduation = graduation;
        this.currentacademic = currentacademic;
        this.keyskills = keyskills;
        this.companiesselected= companiesselected;
        this.contactNumber= contactNumber;
        this.jobrequirement= jobrequirement;
    }

    public String getJobrequirement() {
        return jobrequirement;
    }

    public void setJobrequirement(String jobrequirement) {
        this.jobrequirement = jobrequirement;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public UploadShortListedStudentsDetailsData() {
    }

    public String getCompaniesselected() {
        return companiesselected;
    }

    public void setCompaniesselected(String companiesselected) {
        this.companiesselected = companiesselected;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
}
