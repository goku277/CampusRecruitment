package com.pushpa.mycampusrecruitment.Model;

public class PostJobAdvertisementData {
    private String cName, cQualification, cRequirement, cJobLocation, cKeySkills, cYearsOfGap, referenceId, key, cTen, cTwelve, cGrad, cCurrentAcademic, logo, ctc;

    public PostJobAdvertisementData(String cName, String logo, String cQualification, String cRequirement, String cJobLocation, String cKeySkills, String cYearsOfGap, String cTen, String cTwelve, String cGrad, String cCurrentAcademic, String referenceId, String key, String ctc) {
        this.cName = cName;
        this.logo= logo;
        this.cQualification = cQualification;
        this.cRequirement = cRequirement;
        this.cJobLocation = cJobLocation;
        this.cKeySkills = cKeySkills;
        this.cYearsOfGap = cYearsOfGap;
        this.cTen= cTen;
        this.cTwelve= cTwelve;
        this.cGrad= cGrad;
        this.cCurrentAcademic= cCurrentAcademic;
        this.referenceId= referenceId;
        this.key= key;
        this.ctc= ctc;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public PostJobAdvertisementData() {
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcQualification() {
        return cQualification;
    }

    public void setcQualification(String cQualification) {
        this.cQualification = cQualification;
    }

    public String getcRequirement() {
        return cRequirement;
    }

    public void setcRequirement(String cRequirement) {
        this.cRequirement = cRequirement;
    }

    public String getcJobLocation() {
        return cJobLocation;
    }

    public void setcJobLocation(String cJobLocation) {
        this.cJobLocation = cJobLocation;
    }

    public String getcKeySkills() {
        return cKeySkills;
    }

    public void setcKeySkills(String cKeySkills) {
        this.cKeySkills = cKeySkills;
    }

    public String getcYearsOfGap() {
        return cYearsOfGap;
    }

    public void setcYearsOfGap(String cYearsOfGap) {
        this.cYearsOfGap = cYearsOfGap;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getcTen() {
        return cTen;
    }

    public void setcTen(String cTen) {
        this.cTen = cTen;
    }

    public String getcTwelve() {
        return cTwelve;
    }

    public void setcTwelve(String cTwelve) {
        this.cTwelve = cTwelve;
    }

    public String getcGrad() {
        return cGrad;
    }

    public void setcGrad(String cGrad) {
        this.cGrad = cGrad;
    }

    public String getcCurrentAcademic() {
        return cCurrentAcademic;
    }

    public void setcCurrentAcademic(String cCurrentAcademic) {
        this.cCurrentAcademic = cCurrentAcademic;
    }
}