package com.pushpa.mycampusrecruitment.Model;

public class AddUniversityDetailsData {
    private String universityname, universitylocation, universityid, branchesavailable, yearofestablishment, imageuri, key;

    public AddUniversityDetailsData(String universityname, String universitylocation, String universityid, String branchesavailable, String yearofestablishment, String imageuri, String key) {
        this.universityname = universityname;
        this.universitylocation = universitylocation;
        this.universityid = universityid;
        this.branchesavailable = branchesavailable;
        this.yearofestablishment = yearofestablishment;
        this.imageuri = imageuri;
        this.key = key;
    }

    public AddUniversityDetailsData() {
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getUniversitylocation() {
        return universitylocation;
    }

    public void setUniversitylocation(String universitylocation) {
        this.universitylocation = universitylocation;
    }

    public String getUniversityid() {
        return universityid;
    }

    public void setUniversityid(String universityid) {
        this.universityid = universityid;
    }

    public String getBranchesavailable() {
        return branchesavailable;
    }

    public void setBranchesavailable(String branchesavailable) {
        this.branchesavailable = branchesavailable;
    }

    public String getYearofestablishment() {
        return yearofestablishment;
    }

    public void setYearofestablishment(String yearofestablishment) {
        this.yearofestablishment = yearofestablishment;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}