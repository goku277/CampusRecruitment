package com.pushpa.mycampusrecruitment.Model;

public class ViewUniversitiesModelData {
    private String universityName, universityId, branches, location, year, imageUrl, key;

    public ViewUniversitiesModelData(String universityName, String universityId, String branches, String location, String year, String imageUrl, String key) {
        this.universityName = universityName;
        this.universityId = universityId;
        this.branches = branches;
        this.location = location;
        this.year = year;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}