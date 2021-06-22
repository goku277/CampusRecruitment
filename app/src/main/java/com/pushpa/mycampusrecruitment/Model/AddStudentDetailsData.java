package com.pushpa.mycampusrecruitment.Model;

public class AddStudentDetailsData {
    private String UniversityName, StudentRegistrationNumber, Ten, Twelve, Graduation, CurrentAcademic, KeySkills, ImageUrl, key, selectedBranch, studentName, contactNumber, EmailId, dob, semester;

    public AddStudentDetailsData(String universityName, String studentRegistrationNumber, String ten, String twelve, String graduation, String currentAcademic, String keySkills, String imageUrl, String key, String selectedBranch,
                                 String contactNumber, String studentName, String emailId, String dob, String semester) {
        UniversityName = universityName;
        StudentRegistrationNumber = studentRegistrationNumber;
        Ten = ten;
        Twelve = twelve;
        Graduation = graduation;
        CurrentAcademic = currentAcademic;
        KeySkills = keySkills;
        ImageUrl = imageUrl;
        this.key = key;
        this.selectedBranch= selectedBranch;
        this.contactNumber= contactNumber;
        this.studentName= studentName;
        this.EmailId= emailId;
        this.dob= dob;
        this.semester= semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(String selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public AddStudentDetailsData() {
    }

    public String getUniversityName() {
        return UniversityName;
    }

    public void setUniversityName(String universityName) {
        UniversityName = universityName;
    }

    public String getStudentRegistrationNumber() {
        return StudentRegistrationNumber;
    }

    public void setStudentRegistrationNumber(String studentRegistrationNumber) {
        StudentRegistrationNumber = studentRegistrationNumber;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getTwelve() {
        return Twelve;
    }

    public void setTwelve(String twelve) {
        Twelve = twelve;
    }

    public String getGraduation() {
        return Graduation;
    }

    public void setGraduation(String graduation) {
        Graduation = graduation;
    }

    public String getCurrentAcademic() {
        return CurrentAcademic;
    }

    public void setCurrentAcademic(String currentAcademic) {
        CurrentAcademic = currentAcademic;
    }

    public String getKeySkills() {
        return KeySkills;
    }

    public void setKeySkills(String keySkills) {
        KeySkills = keySkills;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}