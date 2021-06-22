package com.pushpa.mycampusrecruitment.Model;

public class DisplayCorrespondingJobData {
    private String requirements, currentacademic, keyskills, qualification, tenth, twelvth, expected_ctc, grad;

    public DisplayCorrespondingJobData(String requirements, String currentacademic, String grad, String keyskills, String qualification, String tenth, String twelvth, String expected_ctc) {
        this.requirements = requirements;
        this.currentacademic = currentacademic;
        this.keyskills = keyskills;
        this.qualification = qualification;
        this.tenth = tenth;
        this.twelvth = twelvth;
        this.expected_ctc = expected_ctc;
        this.grad= grad;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTenth() {
        return tenth;
    }

    public void setTenth(String tenth) {
        this.tenth = tenth;
    }

    public String getTwelvth() {
        return twelvth;
    }

    public void setTwelvth(String twelvth) {
        this.twelvth = twelvth;
    }

    public String getExpected_ctc() {
        return expected_ctc;
    }

    public void setExpected_ctc(String expected_ctc) {
        this.expected_ctc = expected_ctc;
    }
}