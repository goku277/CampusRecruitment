package com.pushpa.mycampusrecruitment.Model;

public class AddCompanyDetails {
    private String cName, cYos, cTotal, cLocation, cAwardsAchievement, cImage, referenceId, key;

    public AddCompanyDetails(String cName, String cYos, String cTotal, String cLocation, String cAwardsAchievement, String cImage, String referenceId, String key) {
        this.cName = cName;
        this.cYos = cYos;
        this.cTotal = cTotal;
        this.cLocation = cLocation;
        this.cAwardsAchievement = cAwardsAchievement;
        this.cImage = cImage;
        this.referenceId= referenceId;
        this.key= key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public AddCompanyDetails() {

    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcYos() {
        return cYos;
    }

    public void setcYos(String cYos) {
        this.cYos = cYos;
    }

    public String getcTotal() {
        return cTotal;
    }

    public void setcTotal(String cTotal) {
        this.cTotal = cTotal;
    }

    public String getcLocation() {
        return cLocation;
    }

    public void setcLocation(String cLocation) {
        this.cLocation = cLocation;
    }

    public String getcAwardsAchievement() {
        return cAwardsAchievement;
    }

    public void setcAwardsAchievement(String cAwardsAchievement) {
        this.cAwardsAchievement = cAwardsAchievement;
    }

    public String getcImage() {
        return cImage;
    }

    public void setcImage(String cImage) {
        this.cImage = cImage;
    }
}